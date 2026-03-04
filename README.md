# Github Repository Search

## Steps to Start the Project

### 1. Create a `.env` file

Create a `.env` file in the **root directory** of the project and add the following variables:

```commandline
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

### 2. Install Dependencies

Make sure you have the following installed:

* Java 17+
* Maven 3.8+

Then install dependencies:

```bash
mvn clean install
```

---

### 3. Run the Application

Start the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

Or run the main class from your IDE.

---

### 4. Access the Application

Once the server starts, the API will be available at:

```
http://localhost:8080
```

---

### 5. API Endpoints

Example endpoint:

```
GET /api/search/repositories
```

This endpoint fetches repositories from GitHub based on the provided search query.

Example request:

```
GET /api/search/repositories?q=spring
```

---

### 6. API Documentation

Interactive API documentation is available using **Swagger UI**.

After starting the application, you can access the API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

The Swagger UI allows you to:

* View all available endpoints
* Test APIs directly from the browser
* See request and response schemas

---

### 7. Run Tests

To execute unit tests:

```bash
mvn test
```

---
