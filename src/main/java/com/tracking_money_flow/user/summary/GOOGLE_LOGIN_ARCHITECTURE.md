# Google Login Architecture Diagram

## System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          Spring Boot Backend                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                               │
│  ┌──────────────────────── Security Config ────────────────────────┐         │
│  │                                                                  │         │
│  │  Permitted Endpoints (No Auth Required):                       │         │
│  │  ✅ /api/users/register                                        │         │
│  │  ✅ /api/users/login                                           │         │
│  │  ✅ /api/users/google/login         ← FIX: ADDED THIS          │         │
│  │  ✅ /api/users/google/callback                                 │         │
│  │  ✅ /oauth2/**                                                 │         │
│  │  ✅ /login/**                                                  │         │
│  │  ❌ All other /api/** endpoints require JWT token              │         │
│  │                                                                  │         │
│  └──────────────────────────────────────────────────────────────────┘         │
│                                                                               │
│  ┌──────────────────── REST Endpoint (NEW) ──────────────────┐               │
│  │                                                            │               │
│  │  POST /api/users/google/login                            │               │
│  │  ├─ Accepts: JSON body (email, name)                     │               │
│  │  ├─ Returns: JWT Token                                   │               │
│  │  ├─ Status: 200 OK (Success)                             │               │
│  │  └─ Previously: 403 Forbidden ❌ FIXED ✅                 │               │
│  │                                                            │               │
│  └────────────────────────────────────────────────────────────┘               │
│                                                                               │
│  ┌─── Controller Layer ──┐  ┌─── Service Layer ──┐  ┌─ Database Layer ─┐    │
│  │                       │  │                    │  │                  │    │
│  │  AuthController       │  │ GoogleLoginUseCase │  │  User Repository │    │
│  │  ├─ register()        │  │ ├─ findUser()      │  │  ├─ findByEmail()    │
│  │  ├─ login()           │  │ ├─ createUser()    │  │  └─ save()      │    │
│  │  └─ googleLogin()     │  │ └─ generateToken() │  │                  │    │
│  │      (REST API)       │  │                    │  │                  │    │
│  │                       │  │ + JwtTokenProvider │  │                  │    │
│  │                       │  │   └─ createToken() │  │                  │    │
│  │                       │  │                    │  │                  │    │
│  └───────────────────────┘  └────────────────────┘  └──────────────────┘    │
│                                                                               │
│  ┌─── OAuth2 Handlers (Browser Flow) ──────────────────────┐                 │
│  │                                                          │                 │
│  │  OAuth2AuthenticationSuccessHandler                     │                 │
│  │  ├─ Intercepts: /login/oauth2/code/google              │                 │
│  │  ├─ Extracts: email, name from OAuth2 principal        │                 │
│  │  ├─ Calls: GoogleLoginUseCase.execute()                │                 │
│  │  └─ Redirects: frontend with JWT token                 │                 │
│  │                                                          │                 │
│  │  OAuth2AuthenticationFailureHandler                     │                 │
│  │  └─ Handles: authentication failures                    │                 │
│  │                                                          │                 │
│  └──────────────────────────────────────────────────────────┘                 │
│                                                                               │
└─────────────────────────────────────────────────────────────────────────────┘


## Two Authentication Flows

### Flow 1: Browser OAuth2 (Traditional "Login with Google")

```
Frontend                          Backend                         Google
   │                               │                               │
   ├─ Click "Login with Google"──→ │                               │
   │                               ├─ Redirect to Google Auth ────→│
   │                               │                               │
   │                    (User authenticates with Google)          │
   │                               │                               │
   │                               │←─ Redirect Code ──────────────┤
   │                               │                               │
   │                               ├─ Exchange Code for Token ────→│
   │                               │                               │
   │                               │←─ OAuth2 Token & User Info ───┤
   │                               │                               │
   │                               ├─ OAuth2AuthenticationSuccess  │
   │                               │  Handler triggered           │
   │                               │                               │
   │                               ├─ Extract email & name        │
   │                               │                               │
   │                               ├─ Call GoogleLoginUseCase     │
   │                               │  (Create/Update User)        │
   │                               │                               │
   │                               ├─ Generate JWT Token          │
   │                               │                               │
   │←─ Redirect with Token ────────┤                               │
   │  (http://localhost:3000/...) │                               │
   │                               │                               │
   ├─ Store JWT Token ────────────→│ (On subsequent requests)      │
   │  (localStorage/sessionStorage)│                               │


### Flow 2: Direct REST API (Mobile, Postman, Frontend without OAuth2)

```
Client (Postman/Mobile)           Backend
   │                               │
   ├─ POST /api/users/google/login │
   │  {                            │
   │    "email": "user@...",       │
   │    "name": "John Doe"         │
   │  }                            │
   ├────────────────────────────→  │
   │                               │
   │                               ├─ AuthController.googleLogin()
   │                               │
   │                               ├─ Call GoogleLoginUseCase
   │                               │
   │                               ├─ Check if user exists
   │                               │  ├─ If YES: Update user
   │                               │  └─ If NO: Create new user
   │                               │
   │                               ├─ Generate JWT Token
   │                               │
   │←─ Return JWT Token ───────────┤
   │  (200 OK)                     │
   │  "eyJhbGc..."                 │
   │                               │
   ├─ Store JWT Token ────────────→│
   │                               │
   ├─ Use Token for Auth ─────────→│
   │  (Authorization: Bearer ...) │
   │                               │


## Data Flow Diagram

```
                                    ┌──────────────────────┐
                                    │  Postman / Client    │
                                    └──────────┬───────────┘
                                               │
                                        POST request with
                                        email & name
                                               │
                                               ↓
                    ┌──────────────────────────────────────────┐
                    │  Spring Security Filter Chain            │
                    │  ├─ Check if path is permitted  ✅       │
                    │  │  /api/users/google/login              │
                    │  └─ Allow request to pass               │
                    └────────────────┬─────────────────────────┘
                                     │
                                     ↓
                    ┌─────────────────────────────────────────┐
                    │  AuthController                         │
                    │  googleLogin(@RequestBody GoogleUserInfo)
                    └────────────────┬──────────────────────────┘
                                     │
                              Create GoogleLoginCommand
                                     │
                                     ↓
                    ┌──────────────────────────────────────┐
                    │  GoogleLoginUseCase                  │
                    │  execute(command)                    │
                    └────────────────┬─────────────────────┘
                                     │
                        ┌────────────┴────────────┐
                        │                         │
                        ↓                         ↓
            ┌─────────────────────┐  ┌──────────────────────┐
            │ Check User Exists   │  │ Create New User      │
            │ findByEmail(email)  │  │ save(newUser)        │
            └──────────┬──────────┘  └──────────┬───────────┘
                       │                        │
            ┌──────────┴────────────┐           │
            │                       │           │
         Found                    Not Found     │
            │                       │───────────┘
            └──────────┬────────────┘
                       │
                       ↓
        ┌──────────────────────────────┐
        │ Generate JWT Token           │
        │ jwtTokenProvider.create(...)  │
        └───────────────┬──────────────┘
                        │
                        ↓
        ┌──────────────────────────────┐
        │ Return Token in Response     │
        │ ResponseEntity.ok(token)     │
        └───────────────┬──────────────┘
                        │
                        ↓
        ┌──────────────────────────────┐
        │ Client (Postman / Mobile)    │
        │ Receives JWT Token (200 OK)  │
        └──────────────────────────────┘


## Security Flow

```
Request to /api/users/google/login
          │
          ↓
    ┌─────────────┐
    │  BEFORE FIX │ → 403 Forbidden (Endpoint not in whitelist)
    └─────────────┘
          │
          ↓
    ┌─────────────┐
    │  AFTER FIX  │ → Allowed (Endpoint added to whitelist)
    └─────────────┘
          │
          ↓
    Request reaches Controller
          │
          ↓
    Business Logic Executes
          │
          ├─ Validate email format
          ├─ Check if user exists
          ├─ Create/Update user
          ├─ Generate JWT token
          │
          ↓
    Response: JWT Token (200 OK)


## Configuration Change

```
SecurityConfig.java (Line 37-48)

BEFORE (Problematic):
────────────────────
.authorizeHttpRequests(auth -> auth
    .requestMatchers(
        "/api/users/register",
        "/api/users/login",
        // ❌ /api/users/google/login was missing
        "/api/users/google/callback",
        "/error",
        "/oauth2/**",
        "/login/**"
    ).permitAll()
    .anyRequest().authenticated()
)

Result: Any request to /api/users/google/login → 403 Forbidden


AFTER (Fixed):
──────────────
.authorizeHttpRequests(auth -> auth
    .requestMatchers(
        "/api/users/register",
        "/api/users/login",
        "/api/users/google/login",  // ✅ ADDED THIS LINE
        "/api/users/google/callback",
        "/error",
        "/oauth2/**",
        "/login/**"
    ).permitAll()
    .anyRequest().authenticated()
)

Result: Request to /api/users/google/login → Allowed → 200 OK with JWT Token
```

---

## Component Details

### GoogleUserInfo (DTO)
```
Request Body Structure:
{
  "email": "user@gmail.com",    // String - Email address
  "name": "John Doe"            // String - User's name
}
```

### GoogleLoginCommand (Command Object)
```
Carries data from Controller to UseCase:
- email: String
- name: String
```

### JWT Token Response
```
Response Body:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

Used in subsequent requests:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## Summary

✅ **What Was Fixed**: Added `/api/users/google/login` to Spring Security's permitted endpoints list
✅ **Why It Works**: Spring Security now recognizes the endpoint as public and allows unauthenticated access
✅ **Impact**: Users can now successfully authenticate using the Google login API endpoint
✅ **Two Flows**: System supports both browser OAuth2 flow AND direct REST API flow

