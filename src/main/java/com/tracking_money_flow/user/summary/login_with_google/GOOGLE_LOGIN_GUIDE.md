# Google OAuth2 Login Implementation Guide

## Overview
This document explains the Google OAuth2 login implementation with automatic account creation for new users.

## Architecture
The implementation follows **Clean Architecture** principles with the following layers:

### Domain Layer (Entities & Value Objects)
- **User**: Domain entity with `registerWithGoogle()` factory method
- **Email**: Value object that validates email format
- **AuthProvider**: Enum supporting EMAIL_AND_PASSWORD and GOOGLE

### Application Layer (Use Cases)
- **GoogleLoginUseCase**: Orchestrates the login flow
  - If user exists: Returns existing user
  - If user doesn't exist: Creates new user with Google provider and returns it
  - Generates and returns JWT token

### Infrastructure Layer
- **OAuth2AuthenticationSuccessHandler**: Handles successful OAuth2 authentication
  - Extracts email and name from OAuth2User
  - Calls GoogleLoginUseCase
  - Redirects to frontend with JWT token
- **OAuth2AuthenticationFailureHandler**: Handles authentication failures
- **SecurityConfig**: Spring Security configuration with OAuth2 login enabled

## Key Features

### 1. Automatic Account Creation
When a user logs in with Google for the first time:
- Email is extracted from Google profile
- Username is auto-generated from email (part before @)
- Account is created with GOOGLE as AuthProvider
- Default date of birth: 1970-01-01
- Account status: ACTIVE

### 2. Email Validation
- Only valid email formats are accepted
- Duplicate emails are prevented at repository level

### 3. JWT Token Generation
- JWT token is generated upon successful login
- Token is returned to frontend for subsequent API calls

## API Endpoints

### 1. Traditional Login (Email & Password)
```
POST /api/users/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!"
}

Response: JWT Token (String)
```

### 2. Google Login (Direct)
```
POST /api/users/google/login
Content-Type: application/json

{
  "email": "user@gmail.com",
  "name": "John Doe"
}

Response: JWT Token (String)
```

### 3. Google OAuth2 Flow
```
1. User clicks "Login with Google" button on frontend
2. Frontend redirects to: http://localhost:8080/oauth2/authorization/google
3. Google OAuth2 server authenticates user
4. Google redirects back to: http://localhost:8080/login/oauth2/code/google
5. OAuth2AuthenticationSuccessHandler processes the response
6. User is redirected to: http://localhost:3000/auth/callback?token=<JWT_TOKEN>
```

## Configuration

### application.yml
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
            scope:
              - email
              - profile
            redirect-uri: http://localhost:8080/login/oauth2/code/google
        provider:
          google:
            authorization-uri: https://accounts.google.com/o/oauth2/v2/auth
            token-uri: https://oauth2.googleapis.com/token
            user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo
            user-name-attribute: sub
```

### Frontend Setup (Example)
```javascript
// Redirect to OAuth2 login
window.location.href = 'http://localhost:8080/oauth2/authorization/google';

// Or use a login button
<a href="http://localhost:8080/oauth2/authorization/google">
  Login with Google
</a>

// Handle callback
useEffect(() => {
  const params = new URLSearchParams(window.location.search);
  const token = params.get('token');
  
  if (token) {
    localStorage.setItem('jwt_token', token);
    // Redirect to home page or dashboard
  }
}, []);
```

## Database Schema

The User table now includes:
- `auth_provider`: Enum field (EMAIL_AND_PASSWORD, GOOGLE)
- Existing columns for email, username, password hash, etc.

Example migration:
```sql
ALTER TABLE user ADD COLUMN auth_provider VARCHAR(50) NOT NULL DEFAULT 'EMAIL_AND_PASSWORD';
ALTER TABLE user ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE user ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
```

## User Journey

### New User - Google Login
1. User clicks "Login with Google"
2. Google OAuth2 flow completes
3. System checks if email exists in database
4. Email not found → Create new user with:
   - Email from Google
   - Username from email prefix
   - AuthProvider = GOOGLE
   - Password = null (not needed for OAuth2)
   - Status = ACTIVE
5. Generate and return JWT token
6. User is redirected with token to frontend

### Existing User - Google Login
1. User clicks "Login with Google"
2. Google OAuth2 flow completes
3. System checks if email exists in database
4. Email found → Retrieve user
5. Generate and return JWT token
6. User is redirected with token to frontend

## Security Considerations

1. **CSRF Protection**: Disabled for stateless JWT authentication
2. **CORS**: Configured to allow requests from authorized domains
3. **Session Management**: Set to STATELESS (no server-side sessions)
4. **Token Storage**: Recommend storing JWT in httpOnly cookies or secure storage on frontend
5. **Redirect URI**: Must match exactly in Google OAuth2 application settings

## Error Handling

### Common Errors

1. **Email Not Found in Google Profile**
   ```
   Redirect: /login?error=email_not_found
   ```

2. **Authentication Failed**
   ```
   Redirect: /login?error=authentication_failed
   ```

3. **OAuth2 Failure**
   ```
   Redirect: /login?error=oauth2_failure
   ```

## Testing

### Unit Tests
Run GoogleLoginUseCaseTest:
```bash
mvn test -Dtest=GoogleLoginUseCaseTest
```

### Integration Tests
Test the complete flow:
1. Start application: `mvn spring-boot:run`
2. Open browser and navigate to: `http://localhost:8080/oauth2/authorization/google`
3. Complete Google login
4. Verify token is returned
5. Verify new user exists in database (if first-time user)

## Future Enhancements

1. **Provider Linking**: Allow users to link Google account to existing email/password account
2. **Profile Updates**: Sync Google profile data (name, picture) periodically
3. **Refresh Tokens**: Implement Google token refresh
4. **Multiple Providers**: Support other OAuth2 providers (GitHub, Facebook, etc.)
5. **Audit Logging**: Log all authentication attempts for security monitoring

## Dependencies

Required Maven dependencies (already in pom.xml):
- spring-boot-starter-security
- spring-boot-starter-oauth2-client
- jjwt (for JWT generation)

## Troubleshooting

### Issue: "Invalid redirect_uri"
**Solution**: Ensure redirect URI in Google OAuth2 console matches exactly:
- Expected: `http://localhost:8080/login/oauth2/code/google`

### Issue: "Email claim not found"
**Solution**: Ensure Google scope includes `email` in application.yml

### Issue: "User not found after creation"
**Solution**: Verify JPA entity mapping and saveUserWithReturnValue implementation

## References

- [Spring Security OAuth2 Documentation](https://spring.io/projects/spring-security)
- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)
- [JWT with Spring Security](https://jwt.io/)

