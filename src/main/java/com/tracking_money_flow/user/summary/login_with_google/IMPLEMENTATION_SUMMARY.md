# Google Login Implementation - Summary

## What Was Implemented

### 1. **New Use Case: GoogleLoginUseCase**
   - **File**: `src/main/java/com/tracking_money_flow/user/application/usecase/GoogleLoginUseCase.java`
   - **Responsibility**: 
     - Handles login for Google OAuth2 users
     - If user exists: Returns existing user
     - If user doesn't exist: Creates new user automatically with email and Google provider
     - Generates JWT token for authentication
   - **Logic Flow**:
     ```
     Input: GoogleLoginCommand(email, name)
         ↓
     Check if user exists by email
         ↓
     User Exists?
     ├─ YES → Use existing user
     └─ NO → Create new user with registerWithGoogle()
         ↓
     Generate JWT token
         ↓
     Return token to caller
     ```

### 2. **New Command: GoogleLoginCommand**
   - **File**: `src/main/java/com/tracking_money_flow/user/application/command/GoogleLoginCommand.java`
   - **Type**: Record (Java 16+)
   - **Fields**:
     - `email`: String (from Google profile)
     - `name`: String (from Google profile, optional)

### 3. **OAuth2 Success Handler**
   - **File**: `src/main/java/com/tracking_money_flow/user/infrastructure/security/OAuth2AuthenticationSuccessHandler.java`
   - **Responsibility**:
     - Invoked after successful Google OAuth2 authentication
     - Extracts email and name from OAuth2User
     - Calls GoogleLoginUseCase
     - Redirects to frontend with JWT token in URL query parameter
     - Format: `http://localhost:3000/auth/callback?token=<JWT_TOKEN>`

### 4. **OAuth2 Failure Handler**
   - **File**: `src/main/java/com/tracking_money_flow/user/infrastructure/security/OAuth2AuthenticationFailureHandler.java`
   - **Responsibility**:
     - Invoked when OAuth2 authentication fails
     - Redirects to error page with error parameter

### 5. **Updated Security Configuration**
   - **File**: `src/main/java/com/tracking_money_flow/user/infrastructure/config/SecurityConfig.java`
   - **Changes**:
     - Added constructor injection for OAuth2 handlers
     - Enabled `.oauth2Login()` in security filter chain
     - Added `/api/users/google/login` and `/oauth2/**` to permit all
     - Configured custom success and failure handlers

### 6. **Updated Module Configuration**
   - **File**: `src/main/java/com/tracking_money_flow/user/infrastructure/config/UserModuleConfig.java`
   - **Changes**:
     - Added `GoogleLoginUseCase` bean definition
     - Wired dependencies: UserRepository and TokenProvider

### 7. **Updated Auth Controller**
   - **File**: `src/main/java/com/tracking_money_flow/user/api/controller/AuthController.java`
   - **New Endpoint**: `POST /api/users/google/login`
   - **Purpose**: Direct endpoint for Google login (alternative to OAuth2 flow)
   - **Usage**:
     ```bash
     POST /api/users/google/login
     Content-Type: application/json
     
     {
       "email": "user@gmail.com",
       "name": "John Doe"
     }
     ```

### 8. **Updated Repository Adapter**
   - **File**: `src/main/java/com/tracking_money_flow/user/infrastructure/persistence/jpa/JpaUserRepositoryAdapter.java`
   - **Changes**:
     - Implemented `saveUserWithReturnValue()` method properly
     - Now returns the saved User entity (with generated ID)

### 9. **Updated Configuration**
   - **File**: `src/main/resources/application.yml`
   - **Changes**:
     - Added `redirect-uri` for Google OAuth2
     - Configured user-info endpoint

### 10. **Test Implementation**
   - **File**: `src/test/java/com/tracking_money_flow/user/application/usecase/GoogleLoginUseCaseTest.java`
   - **Test Cases**:
     - Test returning token when user exists
     - Test creating new user when user doesn't exist
     - Test error handling for invalid email

### 11. **Documentation**
   - **File**: `GOOGLE_LOGIN_GUIDE.md`
   - Comprehensive guide for using Google login feature

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         Frontend (React)                         │
│  - Login with Google Button                                      │
│  - Handles OAuth2 callback with token                            │
└────────────────────────────┬────────────────────────────────────┘
                             │
                    ┌────────▼────────┐
                    │  Google OAuth2   │
                    │     Server       │
                    └────────┬────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                  Spring Security Filter Chain                    │
│  - OAuth2AuthenticationFilter intercepts callback               │
│  - Loads OAuth2User from Google                                 │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│          OAuth2AuthenticationSuccessHandler                      │
│  - Extracts email and name from OAuth2User                      │
│  - Creates GoogleLoginCommand                                   │
│  - Calls GoogleLoginUseCase                                     │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                  GoogleLoginUseCase (Domain)                     │
│  - Checks if user exists                                        │
│  - If exists: retrieve user                                     │
│  - If not: create new user (registerWithGoogle)                 │
│  - Generate JWT token                                           │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                   UserRepository (Port)                          │
│  - findByEmail(email)                                           │
│  - saveUserWithReturnValue(user)                                │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                 JpaUserRepositoryAdapter                         │
│  - Delegates to UserJpaRepository                               │
│  - Converts between domain and JPA entities                     │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                   MySQL Database                                 │
│  - Stores user with auth_provider = 'GOOGLE'                    │
└─────────────────────────────────────────────────────────────────┘
```

## Key Features

### ✅ Automatic User Creation
- When a user logs in with Google for the first time
- System automatically creates account with:
  - Email from Google profile
  - Username derived from email (before @)
  - AuthProvider set to GOOGLE
  - Status set to ACTIVE

### ✅ Existing User Login
- If user already exists (same email)
- System retrieves existing user
- Returns JWT token

### ✅ Clean Architecture
- Domain layer: User, Email, AuthProvider
- Application layer: GoogleLoginUseCase, GoogleLoginCommand
- Infrastructure layer: Handlers, Config, Repository adapter
- Separation of concerns maintained

### ✅ Security
- JWT token generated for all users
- OAuth2 flow validated by Google
- CORS configured for frontend
- CSRF disabled (stateless authentication)

## Usage Flow

### 1. OAuth2 Flow (Recommended)
```
User clicks "Login with Google"
    ↓
Frontend redirects to: /oauth2/authorization/google
    ↓
Google OAuth2 server
    ↓
Redirect callback: /login/oauth2/code/google
    ↓
OAuth2AuthenticationSuccessHandler processes
    ↓
GoogleLoginUseCase creates/retrieves user
    ↓
Frontend receives token: /auth/callback?token=JWT
    ↓
Frontend stores token and makes authenticated requests
```

### 2. Direct API Flow
```
POST /api/users/google/login
{
  "email": "user@gmail.com",
  "name": "John Doe"
}
    ↓
GoogleLoginUseCase creates/retrieves user
    ↓
Returns JWT token to caller
    ↓
Frontend stores token and makes authenticated requests
```

## Database Changes Required

Add column to user table if not present:
```sql
ALTER TABLE user ADD COLUMN auth_provider VARCHAR(50) DEFAULT 'EMAIL_AND_PASSWORD';
```

## What's Already in Place

✅ User.registerWithGoogle() - Factory method in User domain class
✅ Email validation - Email value object validates email format
✅ UserRepository.saveUserWithReturnValue() - Now properly implemented
✅ JwtTokenProvider - Generates JWT tokens
✅ CORS configuration - Allows frontend requests
✅ OAuth2 credentials - Already configured in application.yml

## Testing the Implementation

### Unit Tests
```bash
mvn test -Dtest=GoogleLoginUseCaseTest
```

### Integration Test
1. Start application:
   ```bash
   mvn spring-boot:run
   ```

2. Go to: `http://localhost:8080/oauth2/authorization/google`

3. Login with your Google account

4. Verify redirect to frontend with token parameter

5. Check database for new user (if first-time login)

## Files Modified/Created

### Created Files:
- ✅ `GoogleLoginCommand.java` - New command class
- ✅ `GoogleLoginUseCase.java` - New use case
- ✅ `OAuth2AuthenticationSuccessHandler.java` - OAuth2 success handler
- ✅ `OAuth2AuthenticationFailureHandler.java` - OAuth2 failure handler
- ✅ `GoogleLoginUseCaseTest.java` - Unit tests
- ✅ `GOOGLE_LOGIN_GUIDE.md` - Documentation

### Modified Files:
- ✅ `SecurityConfig.java` - Enabled OAuth2 login
- ✅ `UserModuleConfig.java` - Added GoogleLoginUseCase bean
- ✅ `AuthController.java` - Added /google/login endpoint
- ✅ `JpaUserRepositoryAdapter.java` - Implemented saveUserWithReturnValue
- ✅ `application.yml` - Updated OAuth2 config

### No Changes Needed:
- ✅ User.java - Already has registerWithGoogle() method
- ✅ .gitignore - Already excludes .idea folder
- ✅ Email.java - Email validation already in place

## Next Steps

1. **Verify Database Schema**: Ensure `auth_provider` column exists in user table
2. **Test OAuth2 Flow**: Use Google OAuth2 credentials from application.yml
3. **Frontend Integration**: Implement token storage and authenticated API calls
4. **Error Handling**: Test error scenarios (invalid email, network errors)
5. **Production Deployment**: Update redirect_uri in Google OAuth2 console for production domain

## Troubleshooting

### Issue: "User not automatically created"
**Check**:
1. Verify User.registerWithGoogle() is being called
2. Check saveUserWithReturnValue() is returning user with ID
3. Verify database constraints allow new user insertion

### Issue: "Token not returned"
**Check**:
1. Verify TokenProvider.generateToken() implementation
2. Check OAuth2AuthenticationSuccessHandler redirect URL
3. Verify frontend is capturing token from URL parameter

### Issue: "Email already in use error"
**Expected Behavior**: 
- If email exists, return existing user (not an error)
- GoogleLoginUseCase should handle this gracefully
- This is working as designed

## Code Quality

✅ Follows Clean Architecture principles
✅ All classes have single responsibility
✅ Dependency injection used throughout
✅ Exception handling implemented
✅ Unit tests provided
✅ Documentation complete
✅ Backward compatible with existing login methods

