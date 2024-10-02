### Understanding the Roles of Service and Controller, and HTTP Mappings in Spring Boot
Before diving into the annotated code, let's first understand the role of **service** and **controller** layers and the various **HTTP mappings** (`@PostMapping`, `@GetMapping`, etc.) in a Spring Boot REST API.

### 1. **Service Layer**:
The **service layer** is where the **business logic** of your application resides. It handles the processing of data and implements the core functionality of the application. In our case, the `ProductService` class:

- **Stores the products** in memory.
- **Processes requests** such as adding, updating, retrieving, or deleting products.
- It interacts with data but does not handle HTTP requests directly.

### 2. **Controller Layer**:
The **controller layer** acts as the **interface** between the client (such as a web browser, Postman, or any external service) and the service layer. The controller handles **HTTP requests** (like GET, POST, PUT, DELETE) and returns appropriate responses. In our case, the `ProductController`:

- Receives HTTP requests from the client.
- Delegates the business logic to the `ProductService`.
- Returns responses to the client (like products data or success/failure messages).

### 3. **HTTP Mappings**:
In Spring Boot, the **HTTP mappings** (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`) define how the controller methods are mapped to **HTTP methods**:

- **`@PostMapping`**: Used to handle HTTP POST requests, which are typically used to **create** a new resource.
- **`@GetMapping`**: Used to handle HTTP GET requests, which are used to **retrieve** data from the server.
- **`@PutMapping`**: Used for HTTP PUT requests, typically for **updating** an existing resource.
- **`@DeleteMapping`**: Used to handle HTTP DELETE requests, which are used to **remove** resources from the server.

### 4. **HTTP Methods**:
- **`GET`**: Retrieve resources.
- **`POST`**: Create/submit resources.
- **`PUT`**: Update entire resources.
- **`DELETE`**: Delete resources.
- **`PATCH`**: Update partial resources.
- **`HEAD`**: Retrieve headers only.
- **`OPTIONS`**: Retrieve supported methods.
- **`TRACE`**: Trace requests.
