# Google Login - Visual Flows & Diagrams

## Flow 1: OAuth2 Login Flow (Recommended)

```
┌──────────────────────────────────────────────────────────────────┐
│ 1. Frontend - User clicks "Login with Google" button             │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 2. Frontend redirects to:                                        │
│    http://localhost:8080/oauth2/authorization/google             │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 3. Spring Security OAuth2 Filter Chain                          │
│    - Intercepts request                                          │
│    - Redirects to Google OAuth2 authorization endpoint           │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 4. Google OAuth2 Server                                          │
│    - User enters Google credentials                              │
│    - User grants permission to app                               │
│    - Returns authorization code                                  │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 5. Spring Security processes callback:                           │
│    GET /login/oauth2/code/google?code=AUTH_CODE&state=STATE      │
│    - Exchanges auth code for access token                        │
│    - Fetches user info from Google API                           │
│    - Creates OAuth2User with email, name, etc.                   │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 6. OAuth2AuthenticationSuccessHandler.onAuthenticationSuccess    │
│    - Extracts email from OAuth2User                              │
│    - Extracts name from OAuth2User                               │
│    - Creates GoogleLoginCommand(email, name)                     │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 7. GoogleLoginUseCase.execute(command)                           │
│    - Validates email with Email value object                     │
│    - Calls userRepository.findByEmail(email)                     │
│                                                                   │
│    ┌────────────────────────────────────────────────────┐        │
│    │ Is user found?                                     │        │
│    │                                                    │        │
│    │ YES ──► Use existing user                         │        │
│    │                                                    │        │
│    │ NO ──► Call User.registerWithGoogle(email)        │        │
│    │         Creates new user with:                    │        │
│    │         - email: from Google                      │        │
│    │         - username: email prefix                  │        │
│    │         - authProvider: GOOGLE                    │        │
│    │         - password: null                          │        │
│    │         - status: ACTIVE                          │        │
│    │         Save with userRepository.saveUserWithReturnValue() │
│    │                                                    │        │
│    └────────────────────────────────────────────────────┘        │
│    - Calls tokenProvider.generateToken(user)                     │
│    - Returns JWT token                                           │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 8. OAuth2AuthenticationSuccessHandler redirects                  │
│    http://localhost:3000/auth/callback?token=<JWT_TOKEN>         │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 9. Frontend processes callback                                   │
│    - Extracts token from URL parameter                           │
│    - Stores token in localStorage                                │
│    - Redirects to home page / dashboard                          │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 10. Frontend uses token for authenticated requests               │
│     Header: Authorization: Bearer <JWT_TOKEN>                    │
│     Example: GET /api/transactions                               │
│              GET /api/users/profile                              │
└──────────────────────────────────────────────────────────────────┘
```

---

## Flow 2: Direct API Login Flow

```
┌──────────────────────────────────────────────────────────────────┐
│ 1. Client (Frontend/Mobile) makes POST request                   │
│    POST /api/users/google/login                                  │
│    Content-Type: application/json                                │
│                                                                   │
│    {                                                              │
│      "email": "user@gmail.com",                                   │
│      "name": "John Doe"                                           │
│    }                                                              │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 2. AuthController.googleLogin(GoogleUserInfo)                    │
│    - Receives googleUserInfo                                     │
│    - Creates GoogleLoginCommand                                  │
│    - Calls googleLoginUseCase.execute(command)                   │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 3. GoogleLoginUseCase.execute(command)                           │
│    - Creates Email value object (validates email)                │
│    - Calls userRepository.findByEmail(email)                     │
│                                                                   │
│    Decision:                                                      │
│    ┌─────────────────────────────────────────────────────┐       │
│    │ User Exists?                                        │       │
│    │ YES → Use existing user                            │       │
│    │ NO  → User.registerWithGoogle(email)               │       │
│    │       userRepository.saveUserWithReturnValue(user)  │       │
│    └─────────────────────────────────────────────────────┘       │
│                                                                   │
│    - Calls tokenProvider.generateToken(user)                     │
│    - Returns JWT token                                           │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 4. AuthController returns response                               │
│    HTTP 200 OK                                                    │
│    Body: JWT token string                                        │
│                                                                   │
│    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM...      │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 5. Client stores token                                           │
│    localStorage.setItem('jwt_token', token)                      │
│    or                                                             │
│    sessionStorage.setItem('jwt_token', token)                    │
│    or                                                             │
│    httpOnly cookie (recommended)                                 │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────┐
│ 6. Client uses token in subsequent requests                      │
│    GET /api/transactions HTTP/1.1                                │
│    Host: localhost:8080                                          │
│    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..  │
└──────────────────────────────────────────────────────────────────┘
```

---

## Flow 3: New User Creation Details

```
When User Does NOT Exist in Database:

┌─────────────────────────────────────────────────────────────────┐
│ GoogleLoginUseCase receives:                                    │
│   email: "newuser@gmail.com"                                    │
│   name: "New User"                                              │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Email validation:                                               │
│   new Email("newuser@gmail.com")                                │
│   ✓ Valid email format                                          │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Check existence:                                                │
│   userRepository.findByEmail(email)                             │
│   Returns: Optional.empty()  [NOT FOUND]                        │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Create new User:                                                │
│   User.registerWithGoogle(email)                                │
│                                                                  │
│   Sets:                                                          │
│   • id: UUID generated                                          │
│   • email: "newuser@gmail.com"                                  │
│   • username: "newuser"  [from email prefix]                    │
│   • password: null  [not needed for OAuth2]                     │
│   • authProvider: AuthProvider.GOOGLE                           │
│   • dateOfBirth: "1970-01-01"  [default]                        │
│   • status: UserStatus.ACTIVE                                   │
│   • createdAt: LocalDateTime.now()                              │
│   • updatedAt: LocalDateTime.now()                              │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Save to database:                                               │
│   userRepository.saveUserWithReturnValue(user)                  │
│                                                                  │
│   JpaUserRepositoryAdapter:                                      │
│   1. Convert domain User to JpaEntity                           │
│   2. Call JpaRepository.save(entity)                            │
│   3. Database generates ID (if auto-increment)                  │
│   4. Convert back to domain User                                │
│   5. Return User with ID set                                    │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Database INSERT:                                                │
│                                                                  │
│   INSERT INTO user (                                            │
│     id,                    // UUID                              │
│     email,                 // newuser@gmail.com                 │
│     username,              // newuser                           │
│     password,              // NULL                              │
│     auth_provider,         // GOOGLE                            │
│     date_of_birth,         // 1970-01-01                        │
│     status,                // ACTIVE                            │
│     created_at,            // NOW()                             │
│     updated_at             // NOW()                             │
│   ) VALUES (...)                                                │
│                                                                  │
│   Result: ✓ User created in database                            │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Generate JWT Token:                                             │
│   tokenProvider.generateToken(user)                             │
│   JwtTokenProvider creates token with:                          │
│   • user ID                                                     │
│   • email                                                       │
│   • expiration (usually 24 hours)                               │
│   • secret key                                                  │
│                                                                  │
│   Returns: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."            │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Return to caller:                                               │
│   JWT token                                                     │
│   (sent to OAuth2 redirect or API response)                     │
└─────────────────────────────────────────────────────────────────┘
```

---

## Flow 4: Existing User Login Details

```
When User ALREADY EXISTS in Database:

┌─────────────────────────────────────────────────────────────────┐
│ GoogleLoginUseCase receives:                                    │
│   email: "existing@gmail.com"                                   │
│   name: "Existing User"                                         │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Email validation:                                               │
│   new Email("existing@gmail.com")                               │
│   ✓ Valid email format                                          │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Check existence:                                                │
│   userRepository.findByEmail(email)                             │
│   Returns: Optional.of(user)  [FOUND]                           │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Use existing user:                                              │
│   User {                                                        │
│     id: UUID (existing)                                         │
│     email: "existing@gmail.com"                                 │
│     username: "existing"  (unchanged)                           │
│     authProvider: GOOGLE  (may be GOOGLE or EMAIL_AND_PASSWORD) │
│     status: ACTIVE        (verified)                            │
│   }                                                              │
│                                                                  │
│   NO database changes made                                      │
│   NO duplicate creation                                         │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Generate JWT Token:                                             │
│   tokenProvider.generateToken(user)                             │
│   Token includes:                                               │
│   • Existing user ID                                            │
│   • Existing email                                              │
│   • New expiration timestamp                                    │
│                                                                  │
│   Returns: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."            │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Return to caller:                                               │
│   JWT token                                                     │
│   (sent to OAuth2 redirect or API response)                     │
│                                                                  │
│   User is logged in, can access APIs                            │
└─────────────────────────────────────────────────────────────────┘
```

---

## Architecture Layer Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                           │
│                    (Frontend - React/Vue)                       │
│  - Login buttons                                                │
│  - Token storage                                                │
│  - API calls with Authorization header                          │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     │ HTTP Requests
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                    API LAYER                                    │
│                (REST Controllers)                               │
│  - AuthController.java                                          │
│  - POST /api/users/google/login                                 │
│  - POST /api/users/register                                     │
│  - POST /api/users/login                                        │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                APPLICATION LAYER                                │
│            (Use Cases & Commands)                               │
│  - GoogleLoginUseCase                                           │
│  - GoogleLoginCommand                                           │
│  - LoginUserUseCase                                             │
│  - RegisterUserUseCase                                          │
│  - LoginCommand                                                 │
│  - RegisterUserCommand                                          │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                  DOMAIN LAYER                                   │
│          (Entities & Value Objects)                             │
│  - User (with registerWithGoogle factory)                       │
│  - Email (validates format)                                     │
│  - Password                                                     │
│  - UserName                                                     │
│  - DateOfBirth                                                  │
│  - UserId                                                       │
│  - AuthProvider (enum: GOOGLE, EMAIL_AND_PASSWORD)              │
│  - UserStatus (enum: ACTIVE, INACTIVE, LOCKED)                  │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     │
┌────────────────────▼────────────────────────────────────────────┐
│              INFRASTRUCTURE LAYER                               │
│      (Adapters, Handlers, Configuration)                        │
│                                                                  │
│  Repositories:                                                  │
│  - JpaUserRepositoryAdapter                                     │
│  - UserJpaRepository (Spring Data)                              │
│                                                                  │
│  Ports (Interfaces):                                            │
│  - UserRepository (port)                                        │
│  - TokenProvider (port)                                         │
│  - PasswordHasher (port)                                        │
│                                                                  │
│  Implementations:                                               │
│  - JwtTokenProvider                                             │
│  - BCryptPasswordHasher                                         │
│                                                                  │
│  Security:                                                      │
│  - OAuth2AuthenticationSuccessHandler                           │
│  - OAuth2AuthenticationFailureHandler                           │
│  - SecurityConfig                                               │
│  - UserModuleConfig                                             │
│                                                                  │
│  Mappers:                                                       │
│  - UserMapper (domain ↔ JPA)                                    │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     │
┌────────────────────▼────────────────────────────────────────────┐
│                DATA ACCESS LAYER                                │
│            (Database & Persistence)                             │
│  - MySQL Database                                               │
│  - user table with columns:                                     │
│    • id (UUID)                                                  │
│    • email (VARCHAR)                                            │
│    • username (VARCHAR)                                         │
│    • password (VARCHAR, nullable)                               │
│    • auth_provider (ENUM: GOOGLE, EMAIL_AND_PASSWORD)           │
│    • date_of_birth (DATE)                                       │
│    • status (ENUM: ACTIVE, INACTIVE, LOCKED)                    │
│    • created_at (TIMESTAMP)                                     │
│    • updated_at (TIMESTAMP)                                     │
└─────────────────────────────────────────────────────────────────┘
```

---

## Dependency Injection Flow

```
┌─────────────────────────────────────────────────────────────────┐
│ Spring ApplicationContext                                       │
└──────────┬──────────────────────────────────────────────────────┘
           │
           │ Creates beans from UserModuleConfig
           │
           ├─► UserRepository bean
           │   └─ userRepository(UserJpaRepository jpa)
           │      └ return new JpaUserRepositoryAdapter(jpa)
           │
           ├─► PasswordHasher bean
           │   └ return new BCryptPasswordHasher()
           │
           ├─► TokenProvider bean
           │   └ return new JwtTokenProvider()
           │
           ├─► GoogleLoginUseCase bean
           │   └ googleLoginUseCase(UserRepository repo, TokenProvider token)
           │      └ return new GoogleLoginUseCase(repo, token)
           │
           ├─► LoginUserUseCase bean
           │   └ loginUserUseCase(UserRepository repo, PasswordHasher hasher, TokenProvider token)
           │      └ return new LoginUserUseCase(repo, hasher, token)
           │
           └─► RegisterUserUseCase bean
               └ registerUserUseCase(UserRepository repo, PasswordHasher hasher)
                  └ return new RegisterUserUseCase(repo, hasher)

                  From SecurityConfig:
           ├─► OAuth2AuthenticationSuccessHandler bean
           │   └ constructor receives GoogleLoginUseCase
           │
           └─► OAuth2AuthenticationFailureHandler bean

           From AuthController:
           └ Constructor receives:
              ├─ RegisterUserUseCase
              ├─ LoginUserUseCase
              └─ GoogleLoginUseCase
```

---

## Database State Transitions

```
┌──────────────────────────────────────────────────────────────────┐
│ Initial State: User NOT in database                              │
│ Email: newuser@gmail.com                                         │
└────────────────────┬─────────────────────────────────────────────┘
                     │
                     ▼ GoogleLoginUseCase.execute()
                     
┌──────────────────────────────────────────────────────────────────┐
│ Action: INSERT new user                                          │
│                                                                   │
│ INSERT INTO user VALUES (                                        │
│   '550e8400-e29b-41d4-a716-446655440001',  // id (UUID)         │
│   'newuser@gmail.com',                     // email              │
│   'newuser',                               // username           │
│   NULL,                                    // password           │
│   'GOOGLE',                                // auth_provider      │
│   '1970-01-01',                            // date_of_birth      │
│   'ACTIVE',                                // status             │
│   '2026-01-01 12:00:00',                   // created_at         │
│   '2026-01-01 12:00:00'                    // updated_at         │
│ )                                                                 │
└────────────────────┬─────────────────────────────────────────────┘
                     │
                     ▼ 
                     
┌──────────────────────────────────────────────────────────────────┐
│ Final State: User NOW in database                                │
│                                                                   │
│ SELECT * FROM user WHERE email = 'newuser@gmail.com'            │
│                                                                   │
│ Result:                                                           │
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │ id  : 550e8400-e29b-41d4-a716-446655440001                 │ │
│ │ email        : newuser@gmail.com                            │ │
│ │ username     : newuser                                      │ │
│ │ password     : NULL                                         │ │
│ │ auth_provider: GOOGLE                                       │ │
│ │ date_of_birth: 1970-01-01                                   │ │
│ │ status       : ACTIVE                                       │ │
│ │ created_at   : 2026-01-01 12:00:00                          │ │
│ │ updated_at   : 2026-01-01 12:00:00                          │ │
│ └──────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────┘
```

---

## Exception Handling Flow

```
┌─────────────────────────────────────────────────────────────────┐
│ Invalid Email Format Scenario                                   │
│ Input: "invalid-email-format"                                   │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ GoogleLoginUseCase.execute()                                    │
│   Email email = new Email("invalid-email-format")               │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Email.constructor()                                             │
│   if (!value.matches(emailRegex)) {                             │
│       throw new IllegalArgumentException("Invalid email")       │
│   }                                                              │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ IllegalArgumentException propagates up                          │
│                                                                  │
│ Stack trace:                                                    │
│   → AuthController.googleLogin()                                │
│   → GoogleLoginUseCase.execute()                                │
│   → Email.<init>()                                              │
│   → IllegalArgumentException                                    │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│ Spring Global Exception Handler (GlobalExceptionHandler)        │
│   Catches IllegalArgumentException                              │
│   Returns HTTP 400 Bad Request with error message               │
│                                                                  │
│   Response:                                                      │
│   {                                                              │
│     "error": "Invalid email",                                    │
│     "message": "Email validation failed"                         │
│   }                                                              │
└─────────────────────────────────────────────────────────────────┘
```

---

## Summary of Diagrams

✅ **Flow 1**: OAuth2 Login Flow - Complete Google OAuth2 authentication
✅ **Flow 2**: Direct API Login Flow - Direct API endpoint for Google login
✅ **Flow 3**: New User Creation - Detailed steps for first-time users
✅ **Flow 4**: Existing User Login - How returning users are handled
✅ **Architecture**: Layered architecture showing clean separation
✅ **Dependency Injection**: Spring bean creation and wiring
✅ **Database Transitions**: Before/after state of database
✅ **Exception Handling**: How errors are caught and reported

