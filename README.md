A small, production-style contact module with strict validation, 100% line & branch coverage (JaCoCo), and a themed demo web UI that exercises a minimal HTTP API.

Why it’s useful: Clean data gate (unique IDs, phone rules), easy to reuse in forms or onboarding flows.

What it shows: Testing discipline, validation, separation of concerns, simple service design, and a minimal API.

Features

 Domain + Service: Contact, ContactService (CRUD with constraints)

 Tests: JUnit 5 unit tests (happy paths + edge cases)

 Coverage: JaCoCo report (100% line/branch in this build)

 Minimal API + Web UI: Health + Create/List/Get/Update/Delete

 Light/Dark theme: nice 8-second “see it in action” clip material

Quick Start
Prereqs

Java 17+

Maven

Run tests + coverage
mvn -q clean test
# Open coverage report:
# Windows:
start target/site/jacoco/index.html
# macOS:
open target/site/jacoco/index.html
# Linux:
xdg-open target/site/jacoco/index.html

Run the demo server
# Default: port 8080
mvn -q -DskipTests compile
mvn -q exec:java

# Optional: custom port
mvn -q exec:java -DPORT=8090


Open the UI:

http://localhost:8080/
 (or /demo/index.html)

Health check:

http://localhost:8080/health
 → {"ok":"up"}

 
<img width="1405" height="1101" alt="image" src="https://github.com/user-attachments/assets/8166c305-0b22-4db0-8167-74227b9a7244" />
