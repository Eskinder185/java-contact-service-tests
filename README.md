# java-contact-service-tests

A production-style contact module with strict validation and comprehensive JUnit 5 tests, packaged with Maven, JaCoCo coverage, and CI.

## What's inside
- **Contact Service**: `Contact.java`, `ContactService.java`
- **Unit Tests**: `ContactTest.java`, `ContactServiceTest.java` (happy paths + edge cases)
- **Reflections**: `SummaryAndReflectionsReport.md` (testing mindset and lessons)
- **Docs**: `docs/openapi.yaml` (future REST contract sketch)

## Constraints (enforced + tested)
- `contactId` required, max 10 chars, immutable, unique
- `firstName`/`lastName` required, max 10 chars
- `phone` required, exactly 10 digits
- `address` required, max 30 chars

## Build & Test
```bash
mvn -q clean test
# coverage report: target/site/jacoco/index.html
```

## Why this matters
- Data quality gate for apps that store contacts
- Regression shield via automated tests
- Portfolio signal: testing discipline, validation, maintainable design

## Roadmap
- Optional REST API (Spring Boot) using the openapi.yaml
- Mutation testing (PIT) for test strength
- Parameterized + property-based test
