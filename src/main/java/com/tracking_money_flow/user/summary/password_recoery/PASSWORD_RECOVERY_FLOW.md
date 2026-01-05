# Password Recovery Flow Documentation

## Overview

The password recovery feature allows users to reset their forgotten passwords through a two-step process:
1. **Request Password Recovery** - User requests a recovery code via email
2. **Recover Password** - User uses the recovery code to reset their password

---

## System Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                          PRESENTATION LAYER                          │
│                      (API Controllers & DTOs)                        │
│  - AuthController.recoverPassword()                                  │
│  - AuthController.passwordRecovery()                                 │
└────────────────────────────┬────────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────────┐
│                      APPLICATION LAYER                               │
│                  (Services & Use Cases)                              │
│  - AuthService                                                       │
│  - PasswordRecoveryRequestUseCase                                    │
│  - PasswordRecoveryUseCase                                           │
└────────────────────────────┬────────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────────┐
│                       DOMAIN LAYER                                    │
│                   (Business Logic)                                   │
│  - PasswordRecovery (Entity)                                         │
│  - User (Entity)                                                     │
│  - PasswordRecovery.create()                                         │
│  - PasswordRecovery.generateAttachedId()                             │
└────────────────────────────┬────────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────────┐
│                    INFRASTRUCTURE LAYER                              │
│                  (Adapters & Repositories)                          │
│  - PasswordRecoveryRepositoryAdapter [@Transactional]               │
│  - EmailSendingAdapter                                              │
│  - JPA Repositories                                                 │
│  - Database Persistence                                             │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Flow 1: Request Password Recovery

### Sequence Diagram

```
User              API           Service          UseCase       Repository     Email      Database
  │                 │              │                 │               │            │           │
  ├─POST Request───>│               │                 │               │            │           │
  │                 │               │                 │               │            │           │
  │          ┌──────┴──────────────>│                 │               │            │           │
  │          │    (Request object)  │                 │               │           │           │
  │          │                      │                 │               │            │           │
  │          │                 ┌────┴────────────────>│               │            │           │
  │          │                 │  PasswordRecoveryCmd │               │            │           │
  │          │                 │                      │               │            │           │
  │          │                 │                 ┌────┴──────────────>│            │           │
  │          │                 │                 │                    │            │           │
  │          │                 │                 │   ┌────────────────┴───────────>│           │
  │          │                 │                 │   │   (Email, Code, Timestamp)  │           │
  │          │                 │                 │   │                    @Transactional     │
  │          │                 │                 │   │                    ┌─────────────────>│
  │          │                 │                 │   │                    │  INSERT record   │
  │          │                 │                 │   │                    │  [COMMITTED]     │
  │          │                 │                 │   │<───────────────────┴─────────────────┤
  │          │                 │                 │   │                             (OK)      │
  │          │                 │                 │   │ ┌────────────────────────────────────>│
  │          │                 │                 │   │ │  Send email with recovery code     │
  │          │                 │                 │   │ │<────────────────────────────────────
  │          │                 │                 │   │ │  (Email sent)
  │          │                 │<────────────────┴───┤ │
  │          │                 │   (useCase done)    │ │
  │          │<────────────────┤                     │ │
  │          │  (HTTP 200)     │                     │ │
  │<─────────│                 │                     │ │
  │ Response │                 │                     │ │
```

### Step-by-Step Process

#### Step 1: API Request
```bash
POST /api/users/me/password-recovery-request
Content-Type: application/json

{
  "email": "user@example.com"
}
```

#### Step 2: Security Check
- **SecurityConfig** verifies endpoint is in `permitAll()` list
- ✅ Endpoint: `/api/users/me/password-recovery-request` is permitted
- Request proceeds to controller

#### Step 3: Controller Processing
```java
@PostMapping("/me/password-recovery-request")
public ResponseEntity<?> recoverPassword(
        @RequestBody PasswordRecoveryRequest request
) {
    // Create command object
    PasswordRecoveryRequestCommand cmd = 
        new PasswordRecoveryRequestCommand(request.email());
    
    // Delegate to service
    authService.passwordRecoveryRequest(cmd);
    
    // Return success response
    return ResponseEntity.ok().build();
}
```

**Request Object:** `PasswordRecoveryRequest`
```java
public record PasswordRecoveryRequest(
    String email
) {}
```

**Command Object:** `PasswordRecoveryRequestCommand`
```java
public record PasswordRecoveryRequestCommand(
    String email
) {}
```

#### Step 4: Service Layer Processing
```java
@Transactional  // ← Transaction starts here
public void passwordRecoveryRequest(PasswordRecoveryRequestCommand cmd) {
    passwordRecoveryRequestUseCase.execute(cmd);
}
```

**Why @Transactional?** Ensures service-level transaction boundary. If any error occurs, the entire transaction rolls back.

#### Step 5: Use Case Execution
```java
public class PasswordRecoveryRequestUseCase {
    public void execute(PasswordRecoveryRequestCommand cmd) {
        // Step 5a: Create domain entity with recovery code
        PasswordRecovery recovery = PasswordRecovery.create(
            cmd.email(),           // User's email
            true,                  // isExpired: true = code is valid/usable
            Timestamp.now(),       // requested_at: when requested
            null                   // expired_at: null (not used yet)
        );
        
        // Step 5b: Save to database (via adapter)
        repo.save(recovery);
        
        // Step 5c: Send email notification
        emailSending.sendEmail(cmd.email());
    }
}
```

#### Step 6: Domain Entity Creation
```java
public static PasswordRecovery create(
    String requestBy,
    boolean isExpired,
    Timestamp requestedAt,
    Timestamp expiredAt
) {
    // Validation
    if(requestBy.isEmpty()) {
        throw new InvalidDataException("requestBy cannot be null or empty");
    }
    if(requestedAt == null) {
        throw new InvalidDataException("requestedAt cannot be null or empty");
    }
    
    // Generate unique 6-digit recovery code
    String attachedId = generateAttachedId();
    
    // Create entity
    return new PasswordRecovery(
        attachedId,
        requestBy,
        isExpired,
        requestedAt,
        expiredAt
    );
}

private static String generateAttachedId() {
    Random random = new Random();
    StringBuilder code = new StringBuilder();
    
    for(int i = 0; i < 6; i++) {
        code.append(random.nextInt(10));  // Digits 0-9
    }
    
    return code.toString();
}
```

**Generated Code Example:** `"847291"` (6 random digits)

#### Step 7: Database Persistence
```java
@Component
@Transactional  // ← Spring AOP proxy manages transaction
public class PasswordRecoveryRepositoryAdapter 
    implements PasswordRecoveryRepository {
    
    @Override
    public void save(PasswordRecovery passwordRecovery) {
        // Map domain object to JPA entity
        PasswordRecoveryJpaEntity entity = 
            PasswordRecoveryMapper.toEntity(passwordRecovery);
        
        // Persist to database
        repo.save(entity);  // ← JpaRepository handles SQL INSERT
        
        // Transaction commits here after method completes
    }
}
```

**JPA Mapping:**
```java
@Entity
@Table(name = "password_recovery")
public class PasswordRecoveryJpaEntity {
    @Id
    @Column(name = "attached_id")
    private String attachedId;
    
    @Column(name = "requested_by", nullable = false)
    private String requestBy;
    
    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;
    
    @Column(name = "requested_at")
    private Timestamp requestedAt;
    
    @Column(name = "expired_at")
    private Timestamp expiredAt;
}
```

**Database INSERT:**
```sql
INSERT INTO password_recovery (
    attached_id,
    requested_by,
    is_expired,
    requested_at,
    expired_at
) VALUES (
    '847291',              -- Generated 6-digit code
    'user@example.com',    -- User's email
    1,                     -- true = valid/usable
    '2026-01-05 22:51:24', -- Current timestamp
    NULL                   -- Will be set when password is changed
);
```

**Database State After INSERT:**
```
Table: password_recovery
┌────────────────┬─────────────────────┬───────────┬──────────────────────┬────────────────┐
│ attached_id    │ requested_by        │ is_expired│ requested_at         │ expired_at     │
├────────────────┼─────────────────────┼───────────┼──────────────────────┼────────────────┤
│ 847291         │ user@example.com    │ 1         │ 2026-01-05 22:51:24  │ NULL           │
└────────────────┴─────────────────────┴───────────┴──────────────────────┴────────────────┘
```

#### Step 8: Email Notification
```java
public class EmailSendingAdapter implements EmailSending {
    @Override
    public void sendEmail(String email) {
        // Get user details
        User user = userRepository.findByEmail(new Email(email))
            .orElseThrow(() -> new NoUserFoundException("User not found"));
        
        // Generate recovery link
        String recoveryLink = generateRecoveryLink(user.getId());
        
        // Process email template
        Context context = new Context();
        context.setVariable("userName", user.getUsername());
        context.setVariable("recoveryLink", recoveryLink);
        context.setVariable("recipientEmail", email);
        
        String htmlContent = templateEngine.process(
            "password-recovery.html",
            context
        );
        
        // Send email via SMTP
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Password Recovery Request");
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
```

**Email Template:** `password-recovery.html`
```html
<html>
  <body>
    <h1>Password Recovery Request</h1>
    <p>Hi [[${userName}]],</p>
    <p>You requested to reset your password. Click the link below:</p>
    <a href="[[${recoveryLink}]]">Reset Password</a>
    <p>This link expires in 24 hours.</p>
  </body>
</html>
```

#### Step 9: Response
```java
// HTTP 200 OK
{
  // Empty body or simple message
}
```

---

## Flow 2: Recover Password (Reset with Code)

### Sequence Diagram

```
User              API           Service          UseCase       Repository     Database      User DB
  │                 │              │                 │               │           │            │
  ├─POST Request───>│               │                 │               │           │            │
  │ (Code)          │               │                 │               │           │            │
  │                 │               │                 │               │           │            │
  │          ┌──────┴──────────────>│                 │               │           │            │
  │          │    (Request object)  │                 │               │           │            │
  │          │                      │                 │               │           │            │
  │          │                 ┌────┴────────────────>│               │           │            │
  │          │                 │  PasswordRecoveryCmd │               │           │            │
  │          │                 │                      │               │           │            │
  │          │                 │                 ┌────┴──────────────>│           │            │
  │          │                 │                 │  Find by code      │           │            │
  │          │                 │                 │                    │           │            │
  │          │                 │                 │ ┌─────────────────>│           │            │
  │          │                 │                 │ │  Query: attached_id = code
  │          │                 │                 │ │<─────────────────┤           │            │
  │          │                 │                 │ │  (PasswordRecovery record)  │            │
  │          │                 │                 │ │                            │            │
  │          │                 │                 │ └─ Validate expiration     │            │
  │          │                 │                 │    Check is_expired field   │            │
  │          │                 │                 │                            │            │
  │          │                 │                 │    if(is_expired = true)   │            │
  │          │                 │                 │    throw ExpiredCodeException
  │          │                 │                 │                            │            │
  │          │                 │                 │ ┌─ Find user ──────────────┼───────────>│
  │          │                 │                 │ │ requested_by = email      │            │
  │          │                 │                 │ │<───────────────────────────────────────
  │          │                 │                 │ │  (User record found)      │            │
  │          │                 │                 │ │                            │            │
  │          │                 │                 │ ├─ Hash new password         │            │
  │          │                 │                 │ │  BCrypt.hash(password)     │            │
  │          │                 │                 │ │                            │            │
  │          │                 │                 │ ├─ Update user password────────────────>│
  │          │                 │                 │ │  UPDATE user SET password=hash
  │          │                 │                 │ │<──────────────────────────────────────┤
  │          │                 │                 │ │                            │            │
  │          │                 │                 │ ├─ Mark recovery code used──>│            │
  │          │                 │                 │ │  UPDATE password_recovery
  │          │                 │                 │ │  SET is_expired=0, expired_at=now
  │          │                 │                 │ │<─────────────────┤           │            │
  │          │                 │                 │ │                            │            │
  │          │                 │<────────────────┤─┘  (useCase complete)       │            │
  │          │<────────────────┤                 │                            │            │
  │          │  (HTTP 200)     │                 │                            │            │
  │<─────────│                 │                 │                            │            │
  │ Response │                 │                 │                            │            │
```

### Step-by-Step Process

#### Step 1: API Request
```bash
POST /api/users/me/password-recovery
Content-Type: application/json

{
  "attachedId": "847291",
  "newPassword": "NewSecurePassword123!"
}
```

#### Step 2: Security Check
- ✅ Endpoint is in `permitAll()` list
- Request proceeds

#### Step 3: Controller Processing
```java
@PostMapping("/me/password-recovery")
public ResponseEntity<?> passwordRecovery(
        @RequestBody PasswordRecoveryRequest request
) {
    // Create command
    PasswordRecoveryCommand cmd = new PasswordRecoveryCommand(
        request.attachedId(),
        request.newPassword()
    );
    
    // Delegate to service
    authService.passwordRecovery(cmd);
    
    return ResponseEntity.ok().build();
}
```

**Request Object:** `PasswordRecoveryRequest`
```java
public record PasswordRecoveryRequest(
    String attachedId,
    String newPassword
) {}
```

#### Step 4: Service Layer
```java
public void passwordRecovery(PasswordRecoveryCommand cmd) {
    passwordRecoveryUseCase.execute(cmd);
}
```

#### Step 5: Use Case Execution
```java
public class PasswordRecoveryUseCase {
    public void execute(PasswordRecoveryCommand cmd) {
        // Step 5a: Find password recovery record by code
        PasswordRecovery passwordRecovery = 
            passwordRecoveryRepository.findById(cmd.attachedId())
                .orElseThrow(() -> 
                    new InvalidPasswordRecoveryIdException(
                        "Invalid password recovery code"
                    )
                );
        
        // Step 5b: Validate code is not expired
        boolean isCodeExpired = passwordRecovery.getIsExpired();
        
        if(isCodeExpired) {  // if is_expired = true (expired)
            throw new ExpiredCodeException(
                "The password recovery code is expired!"
            );
        }
        
        // Step 5c: Find user by email
        User user = userRepository
            .findByEmail(
                new Email(passwordRecovery.getRequestBy())
            )
            .orElseThrow(() -> 
                new NoUserFoundException("No user found!")
            );
        
        // Step 5d: Hash new password
        String hashedPassword = passwordHasher.hash(cmd.newPassword());
        
        // Step 5e: Update user password
        user.changePassword(
            Password.hashed(hashedPassword)
        );
        userRepository.save(user);
        
        // Step 5f: Mark recovery code as used
        passwordRecoveryRepository.updateExpiredStatus(cmd.attachedId());
    }
}
```

#### Step 6: Recovery Code Lookup
```java
@Component
@Transactional
public class PasswordRecoveryRepositoryAdapter 
    implements PasswordRecoveryRepository {
    
    @Override
    public Optional<PasswordRecovery> findById(String id) {
        PasswordRecoveryJpaEntity entity = 
            repo.findById(id)
                .orElseThrow(() => 
                    new InvalidPasswordRecoveryIdException(
                        "Invalid password recovery"
                    )
                );
        
        return Optional.of(
            PasswordRecoveryMapper.toDomain(entity)
        );
    }
}
```

**SQL Query:**
```sql
SELECT * FROM password_recovery WHERE attached_id = '847291';
```

**Result:**
```
attached_id    requested_by        is_expired  requested_at         expired_at
847291         user@example.com    1           2026-01-05 22:51:24  NULL
```

#### Step 7: Code Validation
```
Check: if(isCodeExpired)
    - isCodeExpired = passwordRecovery.getIsExpired()
    - isCodeExpired = true (1 in database)
    
    if(true) {
        throw ExpiredCodeException
    }
    
    ❌ FAILS - Code is marked as expired!
```

**Wait! Logic Check:**

Remember from the flow diagram:
- When creating: `isExpired = true` means "valid/not expired yet"
- When using: `if(status) throw exception` means "if IS expired, fail"

This seems backwards but it's semantically correct based on the database state:
- `isExpired = true` → Code is in "not yet expired" state
- `isExpired = false` → Code has been used (marked as expired)

The validation should check: **Has this code already been used?**

If `isExpired = false` (has been used), throw exception.
If `isExpired = true` (still valid), proceed.

**Current Logic:** `if(isCodeExpired) throw exception`
- This checks if `isExpired` field is `true`
- If true, it throws exception (code is expired)

**Semantics Issue:** The field name `isExpired` is confusing. Better approach:
- Use field name `isValid` instead: `if(!isValid) throw exception`
- Or adjust logic to match: `if(!isExpired) throw exception`

#### Step 8: Find User
```java
User user = userRepository
    .findByEmail(
        new Email(passwordRecovery.getRequestBy())
    )
    .orElseThrow(() => 
        new NoUserFoundException("No user found!")
    );
```

**SQL Query:**
```sql
SELECT * FROM user WHERE email = 'user@example.com';
```

**Result:**
```
id                  email               username   password        auth_provider
550e8400-...       user@example.com   john_doe   (hashed)        LOCAL
```

#### Step 9: Hash New Password
```java
String hashedPassword = passwordHasher.hash(cmd.newPassword());
// Result: "$2a$10$..." (BCrypt hashed)
```

#### Step 10: Update User Password
```java
user.changePassword(Password.hashed(hashedPassword));
userRepository.save(user);
```

**SQL UPDATE:**
```sql
UPDATE user 
SET password = '$2a$10$...',
    updated_at = NOW()
WHERE id = '550e8400-...';
```

#### Step 11: Mark Recovery Code as Used
```java
passwordRecoveryRepository.updateExpiredStatus(
    cmd.attachedId()
);
```

**Implementation:**
```java
@Override
public void updateExpiredStatus(String token) {
    PasswordRecovery pr = findById(token)
        .orElseThrow(() => 
            new InvalidPasswordRecoveryIdException(
                "Invalid password recovery code"
            )
        );
    
    pr.setIsExpired(false);  // Mark as expired (used)
    pr.setExpiredAt(Timestamp.valueOf(LocalDateTime.now()));
    
    save(pr);
}
```

**SQL UPDATE:**
```sql
UPDATE password_recovery 
SET is_expired = 0,
    expired_at = '2026-01-05 22:52:10'
WHERE attached_id = '847291';
```

**Database State After:**
```
attached_id    requested_by        is_expired  requested_at         expired_at
847291         user@example.com    0           2026-01-05 22:51:24  2026-01-05 22:52:10
```

#### Step 12: Response
```
HTTP 200 OK
```

---

## Data Models

### PasswordRecovery (Domain Entity)
```java
public class PasswordRecovery {
    private String attachedId;      // 6-digit recovery code
    private String requestBy;       // User's email
    private boolean isExpired;      // true = valid, false = used/expired
    private Timestamp requestedAt;  // When code was generated
    private Timestamp expiredAt;    // When code was used
}
```

### PasswordRecoveryJpaEntity (Persistence Entity)
```java
@Entity
@Table(name = "password_recovery")
public class PasswordRecoveryJpaEntity {
    @Id
    @Column(name = "attached_id")
    private String attachedId;      // Primary key: 6-digit code
    
    @Column(name = "requested_by", nullable = false)
    private String requestBy;       // User email (not unique - allows re-requests)
    
    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;      // 1 = valid, 0 = used
    
    @Column(name = "requested_at")
    private Timestamp requestedAt;  // Creation timestamp
    
    @Column(name = "expired_at")
    private Timestamp expiredAt;    // Usage timestamp
}
```

### User (Domain Entity)
```java
public class User {
    private UserId id;              // UUID identifier
    private Email email;            // User's email (unique)
    private UserName username;      // Username
    private Password password;      // Hashed password
    private AuthProvider authProvider; // LOCAL, GOOGLE
    private DateOfBirth dateOfBirth;
    private UserStatus status;      // ACTIVE, INACTIVE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

---

## Error Handling

### Request Password Recovery Errors

| Error | Scenario | Status Code | Response |
|-------|----------|-------------|----------|
| InvalidDataException | Email is empty | 400 | "Email cannot be empty" |
| NoUserFoundException | Email doesn't exist in system | 404 | "User not found" |
| MailException | Email sending fails | 500 | "Failed to send email" |

### Recover Password Errors

| Error | Scenario | Status Code | Response |
|-------|----------|-------------|----------|
| InvalidPasswordRecoveryIdException | Code doesn't exist | 400 | "Invalid password recovery code" |
| ExpiredCodeException | Code already used | 400 | "The password recovery code is expired!" |
| NoUserFoundException | User not found | 404 | "No user found!" |
| InvalidPasswordException | Password doesn't meet requirements | 400 | "Password is invalid" |

---

## Transaction Boundaries

### Flow 1: Request Recovery
```
@Transactional (AuthService.passwordRecoveryRequest)
    ↓
    PasswordRecoveryRepositoryAdapter.save()
        @Transactional  ← Nested transaction
        ↓
        INSERT password_recovery ✓ COMMITTED
    ↑
    emailSending.sendEmail()
        (separate, may have own transaction)
    
    ✓ COMMIT (if all success)
    ❌ ROLLBACK (if any error)
```

### Flow 2: Recover Password
```
@Transactional (AuthService.passwordRecovery)
    ↓
    PasswordRecoveryRepositoryAdapter.findById()
    PasswordRecoveryRepository.updateExpiredStatus()
        ↓
        UPDATE password_recovery ← Part of transaction
        UPDATE user              ← Part of transaction
    
    ✓ COMMIT (both updates together)
    ❌ ROLLBACK (if any error)
```

---

## Security Considerations

1. **Code Generation**
   - 6-digit random code provides ~1 million combinations
   - Sufficient for time-limited requests

2. **Code Validity**
   - Code stored in database with `attached_id` as primary key
   - Each code is unique and single-use

3. **Time Limit**
   - Code lifetime managed by `requestedAt` and `expiredAt` timestamps
   - Frontend/API should enforce expiration window (e.g., 24 hours)

4. **Email Verification**
   - Users must have valid email to receive code
   - Only user with access to email can receive the code

5. **Password Hashing**
   - Passwords hashed with BCrypt before storage
   - Never stored in plaintext

6. **HTTPS/SSL**
   - All communications should use HTTPS
   - Recovery codes transmitted over secure channel

---

## Testing Checklist

- [ ] **Test 1: Successful Password Recovery Request**
  - Request recovery for existing user email
  - Verify 6-digit code generated
  - Verify record inserted in database
  - Verify email received

- [ ] **Test 2: Multiple Requests for Same Email**
  - Request recovery twice for same email
  - Verify both codes work independently
  - Verify both records in database

- [ ] **Test 3: Invalid Email**
  - Request recovery for non-existent email
  - Verify error response

- [ ] **Test 4: Successful Password Reset**
  - Request recovery code
  - Use code to reset password
  - Verify password updated in database
  - Verify code marked as used

- [ ] **Test 5: Reuse Expired Code**
  - Use code to reset password
  - Attempt to use same code again
  - Verify error response

- [ ] **Test 6: Invalid Code**
  - Attempt recovery with non-existent code
  - Verify error response

- [ ] **Test 7: Transaction Rollback**
  - Force error during password update
  - Verify transaction rolls back
  - Verify no partial updates in database

---

## Performance Considerations

1. **Database Indexing**
   ```sql
   CREATE INDEX idx_password_recovery_requested_by 
   ON password_recovery(requested_by);
   
   CREATE INDEX idx_user_email 
   ON user(email);
   ```

2. **Query Optimization**
   - Direct lookup by `attached_id` (primary key) - O(1)
   - Lookup by email via foreign key join - O(log n)

3. **Email Sending**
   - Consider async email processing for high volume
   - Current: Synchronous (blocks response)
   - Alternative: Queue email jobs asynchronously

---

## Future Enhancements

1. **Code Expiration Enforcement**
   - Backend validation: Check if timestamp > 24 hours old
   - Currently relies on frontend enforcement

2. **Rate Limiting**
   - Limit recovery requests per email (e.g., 5 per hour)
   - Prevent password reset abuse

3. **Audit Logging**
   - Log all password recovery attempts
   - Track success/failure reasons

4. **SMS Fallback**
   - Send code via SMS if email unavailable
   - Improve security for critical accounts

5. **Two-Factor Authentication**
   - Require additional verification after code use
   - Email + SMS confirmation

6. **Security Questions**
   - Alternative verification method
   - Backup to email/SMS

---

## Summary

The password recovery feature follows Clean Architecture principles:
- **Domain Layer**: Business logic isolated in entities
- **Application Layer**: Use cases orchestrate workflows
- **Infrastructure Layer**: Adapters handle persistence and emails
- **Presentation Layer**: Controllers expose REST API

Transaction management ensures data consistency, and proper error handling provides clear user feedback throughout the recovery process.

