# Password Recovery - API Specification & Testing Guide

## 1. API Endpoints Overview

### Endpoint 1: Request Password Recovery

```
Endpoint:  POST /api/users/me/password-recovery-request
Security:  PUBLIC (permitAll)
Method:    HTTP POST
```

#### Request

**URL:** `http://localhost:8080/api/users/me/password-recovery-request`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "email": "user@example.com"
}
```

**Request DTO:**
```java
public record PasswordRecoveryRequest(
    String email
) {}
```

**Validation Rules:**
- email must not be null
- email must be a valid email format
- email must exist in the system

#### Response

**Success (HTTP 200 OK):**
```json
{}
```
(Empty response body)

**Errors:**

1. **Invalid Email (HTTP 400)**
```json
{
  "error": "Email cannot be empty",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery-request"
}
```

2. **User Not Found (HTTP 404)**
```json
{
  "error": "User not found",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery-request"
}
```

3. **Server Error (HTTP 500)**
```json
{
  "error": "Failed to send recovery email",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery-request"
}
```

---

### Endpoint 2: Recover Password

```
Endpoint:  POST /api/users/me/password-recovery
Security:  PUBLIC (permitAll)
Method:    HTTP POST
```

#### Request

**URL:** `http://localhost:8080/api/users/me/password-recovery`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "attachedId": "847291",
  "newPassword": "NewSecurePassword123!"
}
```

**Request DTO:**
```java
public record PasswordRecoveryRequest(
    String attachedId,
    String newPassword
) {}
```

**Validation Rules:**
- attachedId must not be null (6-digit code)
- newPassword must not be null
- newPassword must meet complexity requirements
  - Minimum 8 characters
  - At least one uppercase letter
  - At least one lowercase letter
  - At least one digit
  - At least one special character

#### Response

**Success (HTTP 200 OK):**
```json
{}
```

**Errors:**

1. **Invalid Code (HTTP 400)**
```json
{
  "error": "Invalid password recovery code",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery"
}
```

2. **Code Already Used (HTTP 400)**
```json
{
  "error": "The password recovery code is expired!",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery"
}
```

3. **User Not Found (HTTP 404)**
```json
{
  "error": "No user found!",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery"
}
```

4. **Invalid Password (HTTP 400)**
```json
{
  "error": "Password is invalid",
  "timestamp": "2026-01-05T22:51:24Z",
  "path": "/api/users/me/password-recovery"
}
```

---

## 2. Testing Guide - cURL Examples

### Test 1: Request Password Recovery - Success

```bash
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com"
  }'
```

**Expected Response:**
```
HTTP/1.1 200 OK

(empty body)
```

**Verify:**
1. Check database for new record:
```sql
SELECT * FROM password_recovery 
WHERE requested_by = 'john@example.com'
ORDER BY requested_at DESC 
LIMIT 1;

-- Result:
-- attached_id: 847291
-- requested_by: john@example.com
-- is_expired: 1
-- requested_at: 2026-01-05 22:51:24
-- expired_at: NULL
```

2. Check email inbox:
   - Should receive email from system
   - Subject: "Password Recovery Request"
   - Body contains recovery link with code

---

### Test 2: Request Password Recovery - User Not Found

```bash
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{
    "email": "nonexistent@example.com"
  }'
```

**Expected Response:**
```
HTTP/1.1 404 Not Found

{
  "error": "User not found"
}
```

---

### Test 3: Request Password Recovery - Invalid Email

```bash
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{
    "email": ""
  }'
```

**Expected Response:**
```
HTTP/1.1 400 Bad Request

{
  "error": "Email cannot be empty"
}
```

---

### Test 4: Multiple Recovery Requests for Same Email

```bash
# First request
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{"email": "john@example.com"}'

# Response: HTTP 200 OK
# Note code from database or email: e.g., "847291"

# Second request (same email)
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{"email": "john@example.com"}'

# Response: HTTP 200 OK
# Note new code from database or email: e.g., "392847"
```

**Expected Behavior:**
- ✅ Both requests succeed (HTTP 200)
- ✅ Two separate records in password_recovery table
- ✅ Both codes are valid

**Database Verification:**
```sql
SELECT * FROM password_recovery 
WHERE requested_by = 'john@example.com'
ORDER BY requested_at DESC;

-- Result: 2 rows
-- Row 1: attached_id: 392847, is_expired: 1, requested_at: 2026-01-05 22:52:10
-- Row 2: attached_id: 847291, is_expired: 1, requested_at: 2026-01-05 22:51:24
```

---

### Test 5: Recover Password - Success

```bash
# First get recovery code
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{"email": "john@example.com"}'

# Query database to get the code
# SELECT attached_id FROM password_recovery 
# WHERE requested_by = 'john@example.com' 
# ORDER BY requested_at DESC LIMIT 1;
# Code: 847291

# Now use the code to reset password
curl -X POST http://localhost:8080/api/users/me/password-recovery \
  -H "Content-Type: application/json" \
  -d '{
    "attachedId": "847291",
    "newPassword": "NewSecurePass123!"
  }'
```

**Expected Response:**
```
HTTP/1.1 200 OK

(empty body)
```

**Verify:**
1. User password updated in database:
```sql
SELECT id, email, password FROM user 
WHERE email = 'john@example.com';

-- Result:
-- password hash should start with $2a$10$ (BCrypt)
-- Old hash is replaced
```

2. Recovery code marked as used:
```sql
SELECT * FROM password_recovery 
WHERE attached_id = '847291';

-- Result:
-- is_expired: 0 (false = used)
-- expired_at: 2026-01-05 22:52:10
```

3. User can login with new password:
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "NewSecurePass123!"
  }'

# Expected: JWT token returned (success)
```

---

### Test 6: Recover Password - Invalid Code

```bash
curl -X POST http://localhost:8080/api/users/me/password-recovery \
  -H "Content-Type: application/json" \
  -d '{
    "attachedId": "999999",
    "newPassword": "NewSecurePass123!"
  }'
```

**Expected Response:**
```
HTTP/1.1 400 Bad Request

{
  "error": "Invalid password recovery code"
}
```

---

### Test 7: Recover Password - Code Already Used

```bash
# First, use code successfully (from Test 5)
# Code: 847291

# Try to use the same code again
curl -X POST http://localhost:8080/api/users/me/password-recovery \
  -H "Content-Type: application/json" \
  -d '{
    "attachedId": "847291",
    "newPassword": "AnotherPassword123!"
  }'
```

**Expected Response:**
```
HTTP/1.1 400 Bad Request

{
  "error": "The password recovery code is expired!"
}
```

**Database State:**
```sql
SELECT * FROM password_recovery 
WHERE attached_id = '847291';

-- Result:
-- is_expired: 0 (false = marked as used/expired)
-- expired_at: 2026-01-05 22:52:10
```

---

### Test 8: Recover Password - Invalid Password Format

```bash
curl -X POST http://localhost:8080/api/users/me/password-recovery \
  -H "Content-Type: application/json" \
  -d '{
    "attachedId": "847291",
    "newPassword": "weak"
  }'
```

**Expected Response:**
```
HTTP/1.1 400 Bad Request

{
  "error": "Password is invalid"
}
```

---

## 3. Postman Collection

### Import Configuration

**Base URL:** `http://localhost:8080`

### Request 1: Forgot Password

```json
{
  "name": "Request Password Recovery",
  "method": "POST",
  "url": "{{base_url}}/api/users/me/password-recovery-request",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": {
    "mode": "raw",
    "raw": "{\"email\": \"john@example.com\"}"
  }
}
```

### Request 2: Reset Password

```json
{
  "name": "Recover Password",
  "method": "POST",
  "url": "{{base_url}}/api/users/me/password-recovery",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": {
    "mode": "raw",
    "raw": "{\"attachedId\": \"847291\", \"newPassword\": \"NewSecurePass123!\"}"
  }
}
```

---

## 4. Complete E2E Test Scenario

### Scenario: User Forgot Password and Resets It

**Setup:**
- User already registered: `john@example.com` / `OldPassword123`
- System is running on `http://localhost:8080`

**Steps:**

#### Step 1: User Requests Password Recovery
```bash
POST /api/users/me/password-recovery-request
{
  "email": "john@example.com"
}

Response: 200 OK
```

**Actions:**
- Email sent to user
- Record created: 
  - attached_id: (random 6-digit)
  - requested_by: john@example.com
  - is_expired: 1
  - requested_at: NOW()

#### Step 2: Check Database for Code
```bash
mysql> SELECT attached_id FROM password_recovery 
        WHERE requested_by = 'john@example.com' 
        ORDER BY requested_at DESC LIMIT 1;

attached_id
847291
```

#### Step 3: User Receives Email
- Email subject: "Password Recovery Request"
- Body contains: "Your recovery code: 847291"
- Or link: "http://yourapp.com/reset-password?code=847291"

#### Step 4: User Submits Recovery Form
```bash
POST /api/users/me/password-recovery
{
  "attachedId": "847291",
  "newPassword": "NewSecurePassword123!"
}

Response: 200 OK
```

**Actions:**
- User password updated in database
- Recovery code marked as used
- Database updates:
  - user.password: NEW_HASH
  - user.updated_at: NOW()
  - password_recovery.is_expired: 0
  - password_recovery.expired_at: NOW()

#### Step 5: User Logs In with New Password
```bash
POST /api/users/login
{
  "email": "john@example.com",
  "password": "NewSecurePassword123!"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Verification:**
- ✅ User successfully authenticated
- ✅ JWT token issued
- ✅ Password recovery complete

---

## 5. Database Schema Queries

### View Password Recovery Records

```sql
-- All recovery requests
SELECT 
  attached_id,
  requested_by,
  is_expired,
  requested_at,
  expired_at,
  CASE 
    WHEN is_expired = 1 THEN 'VALID'
    WHEN is_expired = 0 THEN 'USED'
  END as status
FROM password_recovery
ORDER BY requested_at DESC;

-- Unused recovery codes
SELECT * FROM password_recovery
WHERE is_expired = 1
AND requested_at > DATE_SUB(NOW(), INTERVAL 24 HOUR);

-- Used recovery codes
SELECT * FROM password_recovery
WHERE is_expired = 0
ORDER BY expired_at DESC;

-- Recovery attempts by user
SELECT 
  requested_by,
  COUNT(*) as total_requests,
  MAX(requested_at) as last_request
FROM password_recovery
GROUP BY requested_by
ORDER BY last_request DESC;
```

### Monitor Password Changes

```sql
-- Recent password changes
SELECT 
  id,
  email,
  updated_at,
  CASE 
    WHEN updated_at > DATE_SUB(NOW(), INTERVAL 1 DAY) 
    THEN 'TODAY'
    ELSE 'OLDER'
  END as recency
FROM user
ORDER BY updated_at DESC
LIMIT 10;

-- Users who changed password in last 24 hours
SELECT email, updated_at
FROM user
WHERE updated_at > DATE_SUB(NOW(), INTERVAL 1 DAY)
ORDER BY updated_at DESC;
```

---

## 6. Troubleshooting Guide

### Issue: Email Not Received

**Checklist:**
1. Verify SMTP configuration in `application.yml`
   ```yaml
   spring:
     mail:
       host: smtp.gmail.com
       port: 587
       username: your-email@gmail.com
       password: your-app-password
   ```

2. Check application logs for email errors
   ```
   grep -i "email\|mail" logs/spring.log
   ```

3. Verify recovery record created in database
   ```sql
   SELECT * FROM password_recovery 
   WHERE requested_by = 'user@example.com';
   ```

4. Check spam/junk folder in email client

---

### Issue: Code Already Used Error

**Solution:** Each code can only be used once. User needs to request new code.

**Steps:**
1. Request new recovery code:
   ```bash
   POST /api/users/me/password-recovery-request
   {"email": "user@example.com"}
   ```

2. Get new code from database:
   ```sql
   SELECT attached_id FROM password_recovery
   WHERE requested_by = 'user@example.com'
   AND is_expired = 1
   ORDER BY requested_at DESC LIMIT 1;
   ```

3. Use new code to reset password

---

### Issue: Invalid Code Error

**Verification:**
```bash
# Check if code exists
mysql> SELECT * FROM password_recovery 
        WHERE attached_id = '847291';

# If no rows:
# - Code doesn't exist
# - Code format incorrect
# - Code already expired/deleted

# If found but is_expired = 0:
# - Code already used
# - User must request new code
```

---

## 7. Load Testing

### Test High Volume Recovery Requests

```bash
# Using Apache Bench (ab)
ab -n 1000 -c 10 \
  -p /tmp/request.json \
  -T application/json \
  http://localhost:8080/api/users/me/password-recovery-request

# Using wrk
wrk -t4 -c100 -d30s \
  -s /tmp/script.lua \
  http://localhost:8080/api/users/me/password-recovery-request
```

### Expected Performance
- Response Time: < 500ms
- Throughput: > 100 requests/sec
- Database: No connection pool exhaustion
- Email Queue: No stuck messages

---

## 8. Security Testing

### Test 1: SQL Injection
```bash
curl -X POST http://localhost:8080/api/users/me/password-recovery-request \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com'\'' OR '\''1'\''='\'1"
  }'

# Expected: Input validation error or handled safely
```

### Test 2: Code Guessing
```bash
# Try sequential codes
for i in {000000..999999}; do
  curl -X POST http://localhost:8080/api/users/me/password-recovery \
    -H "Content-Type: application/json" \
    -d "{\"attachedId\": \"$(printf %06d $i)\", \"newPassword\": \"Pass123!\"}"
done

# Expected: Rate limiting or account lockout (future enhancement)
```

### Test 3: Code Reuse
```bash
# Use code first time - should succeed
# Use code second time - should fail

curl -X POST http://localhost:8080/api/users/me/password-recovery \
  -H "Content-Type: application/json" \
  -d '{"attachedId": "847291", "newPassword": "NewPass123!"}'

# Response: 200 OK

# Try again with same code
curl -X POST http://localhost:8080/api/users/me/password-recovery \
  -H "Content-Type: application/json" \
  -d '{"attachedId": "847291", "newPassword": "AnotherPass123!"}'

# Expected: 400 Bad Request - Code already used
```

---

## Summary

This API specification includes:
- ✅ Complete endpoint documentation
- ✅ Request/Response examples
- ✅ Testing procedures with cURL
- ✅ Database verification queries
- ✅ E2E test scenarios
- ✅ Troubleshooting guide
- ✅ Security testing
- ✅ Performance considerations

All examples are ready to use and can be imported into Postman for API testing.

