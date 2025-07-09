# Dormitory Management System API

## Overview
This API provides endpoints for managing dormitory applications, tickets, contracts and more.

## Authentication
The API uses JWT token-based authentication. To access protected endpoints, you need to:

1. Register a user via `/api/auth/register`
2. Login via `/api/auth/login` to receive a JWT token
3. Include the token in the Authorization header for subsequent requests: `Authorization: Bearer <token>`

## Role-Based Access Control

- **Public endpoints**: Anyone can access without authentication
  - POST `/api/applications` - Submit dormitory applications
  - All `/api/auth/**` endpoints - For authentication purposes

- **User-level access**: Requires USER or ADMIN role
  - POST `/api/tickets/**` - Create maintenance tickets

- **Admin-level access**: Requires ADMIN role
  - All other endpoints

## Testing with Curl

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"password","role":"USER"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"password"}'
```

### Submit an application (public access)
```bash
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{"studentName":"John Doe","email":"john@example.com","phone":"123-456-7890"}'
```

### Submit a ticket (requires authentication)
```bash
curl -X POST http://localhost:8080/api/tickets/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "studentId=student-uuid" \
  -F "roomId=room-uuid" \
  -F "description=Broken light fixture" \
  -F "file=@/path/to/image.jpg"
```

## Development

### Running in development mode
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Access H2 console at: http://localhost:8080/h2-console
