# Postman Testing Guide for Fixed API

## The Problem
You were getting the error: "Content-Type 'text/plain' is not supported" because Postman was sending requests with the wrong Content-Type header.

## Solution Applied
1. Removed strict `consumes = MediaType.APPLICATION_JSON_VALUE` from the login endpoint
2. Added better error handling for content-type issues
3. Enhanced CORS configuration
4. Added content negotiation configuration

## How to Test in Postman

### 1. User Registration
**Method**: POST  
**URL**: `http://localhost:8080/api/users/register`  
**Headers**:
```
Content-Type: application/json
```
**Body (raw JSON)**:
```json
{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "dateOfBirth": "1990-01-01"
}
```

### 2. User Login (Now Fixed!)
**Method**: POST  
**URL**: `http://localhost:8080/api/users/login`  
**Headers**:
```
Content-Type: application/json
```
**Body (raw JSON)**:
```json
{
    "email": "test@example.com",
    "password": "password123"
}
```

## Important Postman Settings

1. **Make sure to select "raw" for the body type**
2. **Select "JSON" from the dropdown next to "raw"**
3. **Verify that Content-Type is set to "application/json" in Headers tab**

## Expected Responses

### Registration Success
- Status: `201 Created`
- Body: (empty)

### Login Success  
- Status: `200 OK`
- Body: JWT token string (e.g., "eyJhbGciOiJIUzI1NiJ9...")

### Error Response Example
- Status: `400 Bad Request` or `415 Unsupported Media Type`
- Body:
```json
{
    "error": "Unsupported Media Type", 
    "message": "Please set Content-Type to 'application/json' in your request headers",
    "supported": "application/json"
}
```

## Troubleshooting

If you still get content-type errors:
1. Check that Postman is set to send JSON (raw + JSON selected)
2. Verify the Content-Type header is exactly "application/json"
3. Make sure there are no extra spaces or characters in the JSON body
4. Try copying the exact JSON from above

The API now has better error messages to guide you if there are still issues!
