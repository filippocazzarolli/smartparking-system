# Smart Parking System

A modular Spring Boot application that simulates a smart parking lot with vehicle entry/exit, slot allocation, simple billing, and notifications. The codebase is organized using Spring Modulith to keep features cohesive and decoupled.

> Note: This project uses an in-memory H2 database by default. Data resets on every restart unless changed.


## Stack
- Language: Java 21
- Frameworks:
  - Spring Boot 3.5.x (Web, Data JPA)
  - Spring Modulith (core, JPA integration)
  - Springdoc OpenAPI (Swagger UI)
- Database: H2 (in-memory) for dev/test
- Build/Package Manager: Maven (with Maven Wrapper `mvnw`)
- Lombok for boilerplate reduction (annotation processing required)


## Requirements
- JDK 21+
- No need to install Maven (Maven Wrapper included)
- Network access to download dependencies on first build

Optional:
- cURL or an API client (e.g., Postman) to call endpoints
- A browser to access Swagger UI and H2 Console


## Quick Start
```bash
# From the project root
./mvnw spring-boot:run
# or build a jar and run
./mvnw clean package
java -jar target/smartparking-system-0.0.1-SNAPSHOT.jar
```

Application starts on:
- HTTP port: 9191
- Swagger UI: http://localhost:9191/swagger-ui/index.html
- OpenAPI JSON: http://localhost:9191/v3/api-docs
- H2 Console: http://localhost:9191/h2-console (JDBC URL: `jdbc:h2:mem:parkingdb`, user `sa`, empty password)

On first start the app seeds a few parking slots.


## API Overview
Base path: `/parking`

- `POST /parking/entry?vehicleNumber={plate}`
  - Registers a vehicle entry, allocates an available slot, and emits an internal event.
  - Response: `200 OK` with confirmation message.

- `POST /parking/exit?vehicleNumber={plate}`
  - Registers vehicle exit, frees the slot, computes a simple bill, and emits an internal event.
  - Response: `200 OK` with confirmation message.

Example requests:
```bash
curl -X POST "http://localhost:9191/parking/entry?vehicleNumber=ABC123"
curl -X POST "http://localhost:9191/parking/exit?vehicleNumber=ABC123"
```

Use Swagger UI for interactive exploration: http://localhost:9191/swagger-ui/index.html


## Configuration
Main application properties (see `src/main/resources/application.properties`):
```
spring.application.name=smartparking-system
server.port=9191
# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
# H2 DataSource
spring.datasource.url=jdbc:h2:mem:parkingdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Environment variables and common overrides:
- `SERVER_PORT` or `--server.port=` to change HTTP port
- `SPRING_PROFILES_ACTIVE` to select profiles (none defined yet) — TODO if profiles are added
- `JAVA_TOOL_OPTIONS` / `MAVEN_OPTS` for memory/GC tuning (optional)


## Architecture & Modules
This project follows a modular structure using Spring Modulith. Key modules/packages:
- `allocation` — Slot entity, repository, and slot allocation logic
- `entry` — REST controller for `/parking/entry` and `/parking/exit`, services to process entry and exit
- `billing` — Billing record entity, repository, and billing calculation service
- `notification` — Notification service (e.g., for sending updates; currently a simple service)
- `event` — Domain events like `VehicleEnteredEvent` and `VehicleExitedEvent`

Spring Modulith documentation files are generated under `target/spring-modulith-docs` after a build.

Application entry point:
- `org.example.smartparkingsystem.SmartparkingSystemApplication` (standard Spring Boot `main` method)


## Project Structure
```
smartparking-system/
├─ pom.xml
├─ README.md
├─ mvnw, mvnw.cmd
├─ src/
│  ├─ main/
│  │  ├─ java/org/example/smartparkingsystem/
│  │  │  ├─ SmartparkingSystemApplication.java
│  │  │  ├─ allocation/ (Slot, SlotRepository, SlotAllocationService)
│  │  │  ├─ billing/ (BillingRecord, BillingRecordRepository, BillingService)
│  │  │  ├─ entry/ (EntryController, EntryService, ExitService, ParkingEntry, ParkingEntryRepository)
│  │  │  ├─ event/ (VehicleEnteredEvent, VehicleExitedEvent)
│  │  │  └─ notification/ (NotificationService)
│  │  └─ resources/application.properties
│  └─ test/java/org/example/smartparkingsystem/SmartparkingSystemApplicationTests.java
└─ target/ (build output)
```


## Scripts & Common Commands
Using Maven Wrapper:
- Run app (dev): `./mvnw spring-boot:run`
- Build jar: `./mvnw clean package`
- Run tests: `./mvnw test`
- Format/organize imports: handled by your IDE (no plugin configured) — TODO if code style plugin is desired


## Testing
There is a base Spring Boot test: `SmartparkingSystemApplicationTests` under `src/test`. Execute:
```bash
./mvnw test
```
Future tests can cover services like slot allocation, entry/exit flows, and billing calculations. TODO: expand test coverage.


## Data & Persistence
- Default DB is H2 in-memory; schema is managed via JPA `ddl-auto=update`.
- To persist data across restarts, switch to a file-based H2 URL or another RDBMS and adjust credentials. Example:
  ```properties
  spring.datasource.url=jdbc:h2:file:./data/parkingdb
  spring.jpa.hibernate.ddl-auto=update
  ```
- TODO: Provide production-grade DB profile and migration tool (Flyway/Liquibase) if needed.


## Observability
- SQL logging enabled via `spring.jpa.show-sql=true` (dev-only)
- TODO: Add structured logging and metrics (Micrometer) if required.


## Security
- No authentication/authorization configured.
- TODO: Add Spring Security if the API needs protection.


## License
- TODO: Add license text and update `pom.xml` license metadata.


## Acknowledgments
- Built with Spring Boot and Spring Modulith.
- Swagger UI provided by Springdoc OpenAPI.
