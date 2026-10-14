# CoreBridge

CoreBridge is a fictional Customer Data Integration REST API built with Java 21 and Spring Boot 3.x. It demonstrates CRUD operations, integration with a fictional external provider, JSON mapping with flexible aliases, validation, Flyway migrations, and PostgreSQL persistence.

## Tech Stack

- Java 21
- Spring Boot 3.3.x
- Gradle
- PostgreSQL 16
- Spring Data JPA
- Flyway
- Jackson
- Jakarta Bean Validation
- H2 (tests only)

## Running Locally

1. Start PostgreSQL:

```bash
docker compose up -d
```

2. Run the application:

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
./gradlew bootRun
```

The API runs on `http://localhost:8080` by default.

## Environment Variables

| Variable | Default |
| --- | --- |
| DB_URL | jdbc:postgresql://localhost:5432/corebridge |
| DB_USERNAME | corebridge |
| DB_PASSWORD | corebridge |
| SERVER_PORT | 8080 |
| EXTERNAL_PROVIDER_BASE_URL | http://localhost:8080 |

## API Endpoints

### List customers

```bash
curl -s http://localhost:8080/api/customers
```

### Get customer by id

```bash
curl -s http://localhost:8080/api/customers/1
```

### Create customer

```bash
curl -s -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{
    "customerType": "INDIVIDUAL",
    "fullName": "Alice Carter",
    "email": "alice@example.com",
    "phone": "+1-555-0101",
    "externalId": "ext-001"
  }'
```

### Update customer

```bash
curl -s -X PUT http://localhost:8080/api/customers/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "customerType": "INDIVIDUAL",
    "fullName": "Alice Carter Updated",
    "email": "alice@example.com"
  }'
```

### Delete customer

```bash
curl -s -X DELETE http://localhost:8080/api/customers/1
```

### View fictional external provider payload

```bash
curl -s http://localhost:8080/api/external/mock/customers
```

### Import customers from external provider

```bash
curl -s -X POST http://localhost:8080/api/customers/import
```

## Validation and Errors

- Missing required names for a customer type returns `400 Bad Request`.
- Invalid email returns `400 Bad Request`.
- Unknown customer id returns `404 Not Found`.
- Duplicate unique values return `409 Conflict`.

## Flyway Migrations

- `V1__create_customer_tables.sql`: Creates the `customer` table with a legacy column and a legacy `legacy_import_logs` table. The legacy column and table are intentionally present for migration practice.
- `V2__drop_legacy_import_logs.sql`: Drops the unused legacy table.
- `V3__drop_customer_legacy_code.sql`: Removes the obsolete legacy column from the customer table.

These migrations run automatically on application startup when PostgreSQL is available.

## Postman

Import `postman/CoreBridge.postman_collection.json` into Postman. Set the `baseUrl` variable to your API URL.
```

**TODO maybe add build instructions. Good. Continue.<|end|><|start|>start to=write<|channel|>analysis <|constrain|>xml<|message|><rfl:parameter name="content">
# CoreBridge

CoreBridge is a fictional Customer Data Integration REST API built with Java 21 and Spring Boot 3.x. It demonstrates CRUD operations, integration with a fictional external provider, JSON mapping with flexible aliases, validation, Flyway migrations, and PostgreSQL persistence.

## Tech Stack

- Java 21
- Spring Boot 3.3.x
- Gradle
- PostgreSQL 16
- Spring Data JPA
- Flyway
- Jackson
- Jakarta Bean Validation
- H2 (tests only)

## Running Locally

1. Start PostgreSQL:

```bash
docker compose up -d
```

2. Run the application:

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
./gradlew bootRun
```

The API runs on `http://localhost:8080` by default.

## Environment Variables

| Variable | Default |
| --- | --- |
| DB_URL | jdbc:postgresql://localhost:5432/corebridge |
| DB_USERNAME | corebridge |
| DB_PASSWORD | corebridge |
| SERVER_PORT | 8080 |
| EXTERNAL_PROVIDER_BASE_URL | http://localhost:8080 |

## API Endpoints

### List customers

```bash
curl -s http://localhost:8080/api/customers
```

### Get customer by id

```bash
curl -s http://localhost:8080/api/customers/1
```

### Create customer

```bash
curl -s -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -d '{
    "customerType": "INDIVIDUAL",
    "fullName": "Alice Carter",
    "email": "alice@example.com",
    "phone": "+1-555-0101",
    "externalId": "ext-001"
  }'
```

### Update customer

```bash
curl -s -X PUT http://localhost:8080/api/customers/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "customerType": "INDIVIDUAL",
    "fullName": "Alice Carter Updated",
    "email": "alice@example.com"
  }'
```

### Delete customer

```bash
curl -s -X DELETE http://localhost:8080/api/customers/1
```

### View fictional external provider payload

```bash
curl -s http://localhost:8080/api/external/mock/customers
```

### Import customers from external provider

```bash
curl -s -X POST http://localhost:8080/api/customers/import
```

## Validation and Errors

- Missing required names for a customer type returns `400 Bad Request`.
- Invalid email returns `400 Bad Request`.
- Unknown customer id returns `404 Not Found`.
- Duplicate unique values return `409 Conflict`.

## Flyway Migrations

- `V1__create_customer_tables.sql`: Creates the `customer` table with a legacy column and a legacy `legacy_import_logs` table. The legacy column and table are intentionally present for migration practice.
- `V2__drop_legacy_import_logs.sql`: Drops the unused legacy table.
- `V3__drop_customer_legacy_code.sql`: Removes the obsolete legacy column from the customer table.

These migrations run automatically on application startup when PostgreSQL is available.

## Postman

Import `postman/CoreBridge.postman_collection.json` into Postman. Set the `baseUrl` variable to your API URL.
