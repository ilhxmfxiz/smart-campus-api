# Smart Campus API

This project implements a RESTful API for managing rooms and sensors within a smart campus environment. The system allows facilities managers and automated systems to monitor rooms, register sensors, and track sensor readings.

The API is developed using **JAX-RS (Jersey)** and follows RESTful design principles, including proper resource structuring, HTTP method usage, and meaningful status codes.

---

## Technology Used

* Java language 17
* JAX-RS (Jersey)
* Apache Tomcat
* Maven
* In-memory data structures (ConcurrentHashMap, ArrayList)

---

## Step by Step Instructions to Run

### Step 1: Build the project

```bash
mvn clean package
```

### Step 2: Deploy to Tomcat

Copy the generated WAR file:

```bash
target/smart-campus-api.war
```

into:

```text
apache-tomcat/webapps/
```

### Step 3: Start Tomcat

Run:

```bash
startup.bat
```

### Step 4: Access the API

Base URL:

```text
http://localhost:8080/smart-campus-api/api/v1
```

---

## API Endpoints

### Discovery

* `GET /api/v1`

### Rooms

* `GET /api/v1/rooms`
* `POST /api/v1/rooms`
* `GET /api/v1/rooms/{roomId}`
* `DELETE /api/v1/rooms/{roomId}`

### Sensors

* `GET /api/v1/sensors`
* `GET /api/v1/sensors?type=CO2`
* `POST /api/v1/sensors`
* `GET /api/v1/sensors/{sensorId}`

### Sensor Readings

* `GET /api/v1/sensors/{sensorId}/readings`
* `POST /api/v1/sensors/{sensorId}/readings`

---

## Sample CURL Commands

### Get all rooms

```bash
curl -X GET http://localhost:8080/smart-campus-api/api/v1/rooms
```

### Create a room

```bash
curl -X POST http://localhost:8080/smart-campus-api/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"id\":\"ENG-201\",\"name\":\"Engineering Lab\",\"capacity\":35}"
```

### Get sensors filtered by type

```bash
curl -X GET "http://localhost:8080/smart-campus-api/api/v1/sensors?type=CO2"
```

### Create a sensor

```bash
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":\"TEMP-100\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":21.5,\"roomId\":\"ENG-201\"}"
```

### Add a sensor reading

```bash
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors/TEMP-100/readings \
-H "Content-Type: application/json" \
-d "{\"value\":23.8}"
```

---

# Coursework Questions & Answers

---

## Part 1

### Q1: JAX-RS Resource Lifecycle

By default, JAX-RS creates a new instance of a resource class for each incoming request. This means resource classes are request-scoped rather than singletons.

This impacts in-memory data management because instance variables inside resource classes are not shared across requests. To prevent data loss and ensure consistency, a shared static in-memory store using `ConcurrentHashMap` was implemented. This ensures all requests interact with the same data.

---

### Q2: Hypermedia (HATEOAS)

Hypermedia allows the API to include links within responses so clients can discover available actions dynamically.

This is beneficial because:

* clients do not need hardcoded endpoints
* navigation becomes self-descriptive
* the API becomes more flexible and maintainable

---

## Part 2

### Q1: Returning IDs vs Full Objects

Returning only IDs reduces network bandwidth but requires additional client requests to retrieve full details. Returning full objects increases payload size but reduces the number of API calls needed.

In this implementation, full objects are returned to simplify client usage.

---

### Q2: DELETE Idempotency

DELETE is idempotent because repeated requests produce the same result. If a room is deleted once, subsequent DELETE requests will return a 404 Not Found, but the system state remains unchanged.

---

## Part 3

### Q1: @Consumes and Media Type Mismatch

If a client sends data in a format different from `application/json`, JAX-RS will reject the request with HTTP 415 (Unsupported Media Type). This ensures the API only processes expected formats.

---

### Q2: Query Parameters vs Path Parameters

Query parameters are more suitable for filtering because:

* they are optional
* they allow flexible combinations
* they do not alter the resource identity

Path parameters are better for identifying specific resources.

---

## Part 4

### Q1: Sub-Resource Locator Benefits

The sub-resource locator pattern improves modularity by delegating nested logic to separate classes. This reduces complexity and keeps resource classes clean and maintainable.

---

## Part 5

### Q1: HTTP 422 vs 404

HTTP 422 is more appropriate when the request structure is valid but contains invalid data, such as referencing a non-existent room ID. A 404 would imply the endpoint itself is missing, which is incorrect.

---

### Q2: Stack Trace Exposure Risks

Exposing stack traces can reveal:

* internal class names
* file paths
* system structure

This information can be used by attackers to identify vulnerabilities. Therefore, a global exception mapper was implemented to return generic error messages.

---

### Q3: Logging Filters vs Manual Logging

Using JAX-RS filters centralizes logging logic and avoids repetitive code in every endpoint. This ensures consistency and simplifies maintenance.

##
