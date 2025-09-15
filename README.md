Contact Service • QA Demo


A tiny Java contact service with strict validation, JUnit 5 tests, 100% coverage, and a demo web page.

Run it
# tests + coverage
mvn -q clean test
# open report
# Windows: start target/site/jacoco/index.html
# macOS:  open target/site/jacoco/index.html
# Linux:  xdg-open target/site/jacoco/index.html

# start server (default: 8080)
mvn -q -DskipTests compile
mvn -q exec:java
# then open http://localhost:8080/

Try it (quick API)
# create
curl -s -X POST http://localhost:8080/contacts \
 -H "Content-Type: application/json" \
 -d '{"contactId":"A1","firstName":"Jane","lastName":"Doe","phone":"4045551234","address":"1 Main St"}'

# get one
curl -s "http://localhost:8080/contacts?id=A1"

# list all
curl -s "http://localhost:8080/contacts"

What it shows

Clean validation: unique ID, phone = 10 digits, length limits

Unit tests (JUnit 5) with JaCoCo 100%

Minimal HTTP API + themed demo page (light/dark)

Validation rules

contactId: required, ≤10 chars, unique, immutable

firstName, lastName: required, ≤10 chars

phone: required, exactly 10 digits

address: required, ≤30 chars

Structure
demo/ (web UI)  •  src/main/... (service + server)  •  src/test/... (tests)


Health: GET /health → {"ok":"up"}
Demo page: open http://localhost:8080/
<img width="1405" height="1101" alt="image" src="https://github.com/user-attachments/assets/8166c305-0b22-4db0-8167-74227b9a7244" />
