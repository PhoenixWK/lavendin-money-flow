# Google Login Testing Guide

## Problem Fixed ✅
The 403 Forbidden error on `/api/users/google/login` was caused by the endpoint not being whitelisted in Spring Security configuration.

**Fix Applied**: Added `/api/users/google/login` to the list of permitted endpoints in `SecurityConfig.java`

---

## Quick Start - Test with Postman

### Step 1: Rebuild the Project
```bash
cd D:\Codes\lavendin-money-flow\Code\backend\lavendin-money-flow
.\mvnw.cmd clean package -DskipTests
```

### Step 2: Start the Application
```bash
# Option A: Docker
docker-compose up

# Option B: Direct Spring Boot
.\mvnw.cmd spring-boot:run
```

### Step 3: Test Google Login Endpoint

**Method**: POST
**URL**: `http://localhost:8080/api/users/google/login`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "test.user@gmail.com",
  "name": "Test User"
}
```

**Expected Response (200 OK):**
```
JWT Token String
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

---

## How It Works

### System Supports Two OAuth2 Flows:

#### 1. Browser-Based OAuth2 (Standard Google Login Flow)
- User clicks "Login with Google" button
- Redirected to Google authentication page
- After authentication, redirected to `/login/oauth2/code/google`
- Spring Security's OAuth2AuthenticationSuccessHandler processes the response
- Handler extracts email and name, then calls GoogleLoginUseCase
- User is redirected to frontend with JWT token in URL

#### 2. Direct REST API Flow (For Mobile/API Clients)
- Client sends POST request to `/api/users/google/login` with email and name
- AuthController receives the request and calls GoogleLoginUseCase
- User record is created/found and JWT token is returned
- Client can use the token for subsequent authenticated requests

---

## Verify the Fix

### Check SecurityConfig.java
The file should contain the following permitted endpoints:
```java
.requestMatchers(
    "/api/users/register",
    "/api/users/login",
    "/api/users/google/login",  // ✅ THIS IS THE FIX
    "/api/users/google/callback",
    "/error",
    "/oauth2/**",
    "/login/**"
).permitAll()
```

### Verify in Logs
When you make the POST request, you should see:
```
INFO: Google login successful for user: test.user@gmail.com
INFO: JWT token generated successfully
```

NOT:
```
WARN: Unauthorized attempt to access /api/users/google/login
ERROR: 403 Forbidden - Access Denied
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Still getting 403 Forbidden | Rebuild project and restart application |
| Content-Type error | Make sure header is exactly: `Content-Type: application/json` |
| JSON parsing error | Ensure body contains valid JSON with `email` and `name` fields |
| Database error | Check MySQL/H2 is running and credentials are correct |
| JWT generation failed | Check JWT secret is configured in application.yml |

---

## Related Files
- `SecurityConfig.java` - Security configuration (MODIFIED ✅)
- `AuthController.java` - REST endpoints
- `GoogleLoginUseCase.java` - Business logic
- `OAuth2AuthenticationSuccessHandler.java` - OAuth2 flow handler
- `application.yml` - OAuth2 configuration

---

## Next Steps
1. ✅ Rebuild the project
2. ✅ Test the endpoint with Postman
3. ✅ Verify the fix works
4. 📝 Update your API documentation with the new endpoint details
5. 🔒 Consider adding JWT token validation on protected endpoints

