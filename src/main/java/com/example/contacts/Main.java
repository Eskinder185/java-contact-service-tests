package com.example.contacts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
  private static final Gson GSON = new Gson();
  private static final Map<String,String> CONTENT_TYPES = Map.of(
    "html","text/html; charset=utf-8","js","application/javascript; charset=utf-8",
    "css","text/css; charset=utf-8","json","application/json; charset=utf-8",
    "png","image/png","jpg","image/jpeg","jpeg","image/jpeg","svg","image/svg+xml"
  );

  private static ContactService service = new ContactService();

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    // Health
    server.createContext("/health", ex -> {
      withCors(ex);
      if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) { sendNoContent(ex); return; }
      if ("GET".equalsIgnoreCase(ex.getRequestMethod())) { sendJson(ex, 200, jsonMsg("ok","up")); }
      else { sendJson(ex, 405, jsonMsg("error","method not allowed")); }
    });

    // Favicon (silence noisy 404)
    server.createContext("/favicon.ico", ex -> { withCors(ex); sendNoContent(ex); });

    // Static demo: http://localhost:8080/  and /demo/...
    server.createContext("/", ex -> {
      withCors(ex);
      if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) { sendNoContent(ex); return; }
      // Serve demo/index.html by default
      serveStatic(ex, Paths.get("demo"), "index.html");
    });
    server.createContext("/demo", ex -> {
      withCors(ex);
      if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) { sendNoContent(ex); return; }
      serveStatic(ex, Paths.get("demo"), "index.html");
    });

    // API: /contacts
    server.createContext("/contacts", ex -> {
      withCors(ex);
      final String method = ex.getRequestMethod();
      if ("OPTIONS".equalsIgnoreCase(method)) { sendNoContent(ex); return; }

      try {
        switch (method) {
          case "POST": {
            // Create
            String body = readBody(ex);
            JsonObject obj = GSON.fromJson(body, JsonObject.class);

            String id      = req(obj, "contactId");
            String first   = req(obj, "firstName");
            String last    = req(obj, "lastName");
            String phone   = req(obj, "phone");
            String address = req(obj, "address");

            Contact c = new Contact(id, first, last, phone, address);
            service.addContact(c);
            sendJson(ex, 201, GSON.toJson(c));
            return;
          }
          case "GET": {
            // Fetch one (id=...) OR list all if no id
            Map<String,String> q = query(ex.getRequestURI());
            String id = q.get("id");
            if (id == null || id.isBlank()) {
              // List all contacts
              Collection<Contact> all = service.getAll();
              sendJson(ex, 200, GSON.toJson(all));
            } else {
              Contact c = service.getContact(id);
              if (c == null) sendJson(ex, 404, jsonMsg("error","not found"));
              else sendJson(ex, 200, GSON.toJson(c));
            }
            return;
          }
          case "PATCH": {
            // Partial update by id
            Map<String,String> q = query(ex.getRequestURI());
            String id = q.get("id");
            if (id == null || id.isBlank()) { sendJson(ex, 400, jsonMsg("error","missing ?id=...")); return; }
            Contact c = service.getContact(id);
            if (c == null) { sendJson(ex, 404, jsonMsg("error","not found")); return; }

            String body = readBody(ex);
            JsonObject obj = GSON.fromJson(body, JsonObject.class);

            // Update using setters (assumes Contact has setters with validation)
            if (obj.has("firstName")) c.setFirstName(req(obj, "firstName"));
            if (obj.has("lastName"))  c.setLastName(req(obj, "lastName"));
            if (obj.has("phone"))     c.setPhone(req(obj, "phone"));
            if (obj.has("address"))   c.setAddress(req(obj, "address"));

            sendJson(ex, 200, GSON.toJson(c));
            return;
          }
          case "DELETE": {
            // Delete by id
            Map<String,String> q = query(ex.getRequestURI());
            String id = q.get("id");
            if (id == null || id.isBlank()) { sendJson(ex, 400, jsonMsg("error","missing ?id=...")); return; }
            Contact c = service.getContact(id);
            if (c == null) { sendJson(ex, 404, jsonMsg("error","not found")); return; }
            service.deleteContact(id);
            sendNoContent(ex);
            return;
          }
          default:
            sendJson(ex, 405, jsonMsg("error","method not allowed"));
        }
      } catch (IllegalArgumentException iae) {
        sendJson(ex, 400, jsonMsg("error", iae.getMessage()));
      } catch (Exception e) {
        sendJson(ex, 500, jsonMsg("error", "server error: " + e.getMessage()));
      }
    });

    server.setExecutor(null);
    System.out.println("Server running on http://localhost:8080 (CORS + static demo)");
    server.start();
  }

  // ---------- helpers ----------
  private static String readBody(HttpExchange ex) throws IOException {
    try (InputStream in = ex.getRequestBody()) {
      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    }
  }
  private static void withCors(HttpExchange ex) {
    Headers h = ex.getResponseHeaders();
    h.add("Access-Control-Allow-Origin", "*");
    h.add("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,OPTIONS");
    h.add("Access-Control-Allow-Headers", "Content-Type");
  }
  private static void sendJson(HttpExchange ex, int status, String json) throws IOException {
    byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
    ex.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
    ex.sendResponseHeaders(status, bytes.length);
    try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
  }
  private static void sendNoContent(HttpExchange ex) throws IOException {
    ex.sendResponseHeaders(204, -1);
    ex.close();
  }
  private static String jsonMsg(String k, String v) {
    return "{\"" + k + "\":\"" + v + "\"}";
  }
  private static Map<String,String> query(URI uri) {
    if (uri.getQuery() == null || uri.getQuery().isBlank()) return Map.of();
    return Arrays.stream(uri.getQuery().split("&"))
      .map(s -> s.split("=", 2))
      .filter(a -> a.length == 2)
      .collect(Collectors.toMap(a -> a[0], a -> java.net.URLDecoder.decode(a[1], StandardCharsets.UTF_8)));
  }
  private static String req(JsonObject o, String key) {
    if (o == null || !o.has(key) || o.get(key).isJsonNull()) throw new IllegalArgumentException(key + " required");
    return o.get(key).getAsString();
  }

  private static void serveStatic(HttpExchange ex, Path root, String defaultFile) throws IOException {
    String path = ex.getRequestURI().getPath();
    String raw = path.startsWith("/demo") ? path.replaceFirst("^/demo/?", "") : path.replaceFirst("^/?", "");
    if (raw.isBlank()) raw = defaultFile;
    Path file = root.resolve(raw).normalize();

    if (!file.startsWith(root) || !Files.exists(file) || Files.isDirectory(file)) {
      sendJson(ex, 404, jsonMsg("error","file not found"));
      return;
    }
    String name = file.getFileName().toString();
    String ext = name.contains(".") ? name.substring(name.lastIndexOf('.')+1).toLowerCase() : "html";
    String ct = CONTENT_TYPES.getOrDefault(ext, "application/octet-stream");

    byte[] bytes = Files.readAllBytes(file);
    ex.getResponseHeaders().add("Content-Type", ct);
    ex.sendResponseHeaders(200, bytes.length);
    try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
  }
}
