# Spring Boot To-Do Application

A RESTful API for managing to-do items built with Spring Boot.

## Features

- **Create** new to-do items with required fields
- **Update** existing to-do items
- **Delete** to-do items
- **Mark items as done**
- **Automatic timestamp generation** with custom format: "Created on <day-of-week>, <day> <month> at <hour>:<minute>"
- **Field validation** for required fields (title, description, deadline)

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### Build the Application

```bash
mvn clean compile
```

### Run Tests

```bash
mvn test
```

### Start the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Create a To-Do Item

**POST** `/api/todos`

Request body:
```json
{
  "title": "Complete project documentation",
  "description": "Write comprehensive documentation for the to-do app",
  "deadline": "2025-10-20T15:00:00"
}
```

Response:
```json
{
  "id": 1,
  "title": "Complete project documentation",
  "description": "Write comprehensive documentation for the to-do app",
  "deadline": "2025-10-20T15:00:00",
  "done": false,
  "createdAt": "2025-10-17T14:54:13.845294",
  "createdAtFormatted": "Created on Friday, 17 October at 14:54"
}
```

### Get All To-Do Items

**GET** `/api/todos`

Response:
```json
[
  {
    "id": 1,
    "title": "Complete project documentation",
    "description": "Write comprehensive documentation for the to-do app",
    "deadline": "2025-10-20T15:00:00",
    "done": false,
    "createdAt": "2025-10-17T14:54:13.845294",
    "createdAtFormatted": "Created on Friday, 17 October at 14:54"
  }
]
```

### Get a Specific To-Do Item

**GET** `/api/todos/{id}`

### Update a To-Do Item

**PUT** `/api/todos/{id}`

Request body:
```json
{
  "title": "Updated title",
  "description": "Updated description",
  "deadline": "2025-10-21T16:00:00",
  "done": true
}
```

### Delete a To-Do Item

**DELETE** `/api/todos/{id}`

Returns: 204 No Content

## Data Model

### TodoItem

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | Auto-generated | Unique identifier |
| title | String | Yes | Title of the to-do item |
| description | String | Yes | Detailed description (max 1000 chars) |
| deadline | LocalDateTime | Yes | Due date and time |
| done | Boolean | No (default: false) | Completion status |
| createdAt | LocalDateTime | Auto-generated | Creation timestamp |
| createdAtFormatted | String | Auto-generated | Formatted creation timestamp |

## Validation

All to-do items must have:
- A non-blank **title**
- A non-blank **description**
- A **deadline** date and time

Missing any of these fields will result in a 400 Bad Request response.

## Database

The application uses an in-memory H2 database. Data is not persisted between application restarts.

To access the H2 console for debugging:
1. Navigate to `http://localhost:8080/h2-console`
2. Use JDBC URL: `jdbc:h2:mem:tododb`
3. Username: `sa`
4. Password: (leave empty)

## Testing

The application includes comprehensive integration and unit tests covering:
- Creating to-do items
- Updating to-do items
- Deleting to-do items
- Marking items as done
- Field validation
- Timestamp format validation

Run all tests with:
```bash
mvn test
```

## Technologies Used

- Spring Boot 3.1.5
- Spring Data JPA
- Spring Validation
- H2 Database
- JUnit 5
- Maven