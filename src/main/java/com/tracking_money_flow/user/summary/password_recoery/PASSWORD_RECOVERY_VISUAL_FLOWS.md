# Password Recovery - Visual Flow Diagrams

## 1. High-Level System Flow

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        USER INTERACTIONS                                │
└─────────────────────────────────────────────────────────────────────────┘

    User Forgot Password
            ↓
    ┌───────────────────────┐
    │  Request Recovery     │
    │  (Email Address)      │
    └───────────┬───────────┘
                ↓
    ┌───────────────────────┐          ┌───────────────────────┐
    │  Check Email Inbox    │ ◄────┬──►│  Receive Email with   │
    │                       │      │   │  Recovery Code        │
    └───────────┬───────────┘      │   └───────────────────────┘
                ↓                   │
    ┌───────────────────────┐      │
    │  Click Recovery Link  │──────┘
    │  Or Enter Code        │
    └───────────┬───────────┘
                ↓
    ┌───────────────────────┐
    │  Enter New Password   │
    └───────────┬───────────┘
                ↓
    ┌───────────────────────┐
    │  Password Changed!    │
    └───────────────────────┘
```

---

## 2. Request Password Recovery - Detailed Flow

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    STEP 1: REQUEST RECOVERY FLOW                        │
└─────────────────────────────────────────────────────────────────────────┘

CLIENT SIDE (Frontend)
    │
    ├─► User enters email in "Forgot Password" form
    │
    └─► POST /api/users/me/password-recovery-request
        Content-Type: application/json
        {
          "email": "user@example.com"
        }
        │
        │
        ▼
SERVER SIDE (Backend)
    │
    ├─► HTTP Request arrives
    │
    ├─ LAYER 1: SECURITY
    │   ├─► Spring Security Filter Chain
    │   ├─► Check endpoint in SecurityConfig.permitAll()
    │   ├─► Endpoint: /api/users/me/password-recovery-request ✓
    │   └─► Allow request to proceed
    │
    ├─ LAYER 2: PRESENTATION (Controller)
    │   ├─► AuthController.recoverPassword()
    │   ├─► Parse @RequestBody PasswordRecoveryRequest
    │   ├─► Extract email from request
    │   ├─► Create PasswordRecoveryRequestCommand
    │   └─► Call authService.passwordRecoveryRequest(cmd)
    │
    ├─ LAYER 3: APPLICATION (Service)
    │   ├─► @Transactional starts
    │   ├─► AuthService.passwordRecoveryRequest()
    │   └─► Call passwordRecoveryRequestUseCase.execute()
    │
    ├─ LAYER 4: APPLICATION (Use Case)
    │   ├─► PasswordRecoveryRequestUseCase.execute()
    │   │
    │   ├─► STEP A: Create Recovery Entity
    │   │   ├─► PasswordRecovery.create(email, true, timestamp, null)
    │   │   ├─► Generate 6-digit code: "847291"
    │   │   ├─► Create domain object:
    │   │   │   {
    │   │   │     attachedId: "847291",
    │   │   │     requestBy: "user@example.com",
    │   │   │     isExpired: true,
    │   │   │     requestedAt: 2026-01-05 22:51:24,
    │   │   │     expiredAt: null
    │   │   │   }
    │   │   └─► Return PasswordRecovery entity
    │   │
    │   ├─► STEP B: Save to Database
    │   │   ├─► Call repo.save(passwordRecovery)
    │   │   │
    │   │   └─ LAYER 5: INFRASTRUCTURE (Adapter)
    │   │       ├─► @Component @Transactional
    │   │       ├─► PasswordRecoveryRepositoryAdapter.save()
    │   │       ├─► Map domain to JPA entity
    │   │       ├─► Call jpaRepository.save()
    │   │       │
    │   │       └─ DATABASE
    │   │           ├─► INSERT INTO password_recovery
    │   │           ├─► (attached_id, requested_by, is_expired, ...)
    │   │           ├─► VALUES ("847291", "user@example.com", 1, ...)
    │   │           └─► ✓ COMMITTED
    │   │
    │   └─► STEP C: Send Email
    │       ├─► Call emailSending.sendEmail(email)
    │       ├─► EmailSendingAdapter.sendEmail()
    │       ├─► Load password-recovery.html template
    │       ├─► Replace placeholders:
    │       │   {userName: "john_doe", recoveryLink: "...847291..."}
    │       ├─► Send via SMTP to user@example.com
    │       └─► ✓ Email delivered
    │
    └─► Return HTTP 200 OK
        │
        ▼
CLIENT SIDE
    │
    └─► User receives email with recovery code
        "Click here to reset password: http://.../?code=847291"
```

---

## 3. Recover Password - Detailed Flow

```
┌─────────────────────────────────────────────────────────────────────────┐
│                  STEP 2: RECOVER PASSWORD FLOW                          │
└─────────────────────────────────────────────────────────────────────────┘

CLIENT SIDE (Frontend)
    │
    ├─► User enters recovery code and new password
    │
    └─► POST /api/users/me/password-recovery
        Content-Type: application/json
        {
          "attachedId": "847291",
          "newPassword": "NewSecurePass123!"
        }
        │
        │
        ▼
SERVER SIDE (Backend)
    │
    ├─► HTTP Request arrives
    │
    ├─ LAYER 1: SECURITY
    │   ├─► Check endpoint in SecurityConfig.permitAll()
    │   ├─► Endpoint: /api/users/me/password-recovery ✓
    │   └─► Allow request to proceed
    │
    ├─ LAYER 2: PRESENTATION (Controller)
    │   ├─► AuthController.passwordRecovery()
    │   ├─► Parse @RequestBody PasswordRecoveryRequest
    │   ├─► Extract attachedId and newPassword
    │   ├─► Create PasswordRecoveryCommand
    │   └─► Call authService.passwordRecovery(cmd)
    │
    ├─ LAYER 3: APPLICATION (Service)
    │   ├─► @Transactional starts
    │   └─► AuthService.passwordRecovery()
    │       └─► Call passwordRecoveryUseCase.execute()
    │
    ├─ LAYER 4: APPLICATION (Use Case)
    │   ├─► PasswordRecoveryUseCase.execute()
    │   │
    │   ├─► STEP A: Validate Recovery Code
    │   │   ├─► Query: SELECT FROM password_recovery
    │   │   │           WHERE attached_id = "847291"
    │   │   │
    │   │   └─ LAYER 5: INFRASTRUCTURE (Adapter & Database)
    │   │       ├─► PasswordRecoveryRepositoryAdapter.findById("847291")
    │   │       ├─► JpaRepository.findById("847291")
    │   │       │
    │   │       └─► Database Result:
    │   │           {
    │   │             attachedId: "847291",
    │   │             requestBy: "user@example.com",
    │   │             isExpired: true,      ◄─── Code is valid
    │   │             requestedAt: 2026-01-05 22:51:24,
    │   │             expiredAt: null
    │   │           }
    │   │
    │   ├─► STEP B: Check Code Expiration
    │   │   ├─► Get isExpired = true
    │   │   ├─► Check: if(isExpired) throw exception
    │   │   ├─► Logic: true = code IS expired/used
    │   │   ├─► Condition false (code is valid, not expired)
    │   │   └─► ✓ Proceed to next step
    │   │
    │   ├─► STEP C: Find User
    │   │   ├─► Query: SELECT FROM user
    │   │   │           WHERE email = "user@example.com"
    │   │   │
    │   │   └─► Database Result:
    │   │       {
    │   │         id: "550e8400-...",
    │   │         email: "user@example.com",
    │   │         username: "john_doe",
    │   │         password: "$2a$10$...",  ◄─── Old hash
    │   │         ...
    │   │       }
    │   │
    │   ├─► STEP D: Hash New Password
    │   │   ├─► Input: "NewSecurePass123!"
    │   │   ├─► passwordHasher.hash(newPassword)
    │   │   ├─► Algorithm: BCrypt with salt
    │   │   └─► Output: "$2a$10$..." (60-char hash)
    │   │
    │   ├─► STEP E: Update User Password
    │   │   ├─► user.changePassword(Password.hashed(hash))
    │   │   ├─► userRepository.save(user)
    │   │   │
    │   │   └─► Database Update:
    │   │       UPDATE user
    │   │       SET password = "$2a$10$...",
    │   │           updated_at = 2026-01-05 22:52:10
    │   │       WHERE id = "550e8400-..."
    │   │       ✓ UPDATED
    │   │
    │   └─► STEP F: Mark Recovery Code as Used
    │       ├─► Call updateExpiredStatus("847291")
    │       ├─► Set isExpired = false (now used/expired)
    │       ├─► Set expiredAt = current timestamp
    │       │
    │       └─► Database Update:
    │           UPDATE password_recovery
    │           SET is_expired = 0,
    │               expired_at = 2026-01-05 22:52:10
    │           WHERE attached_id = "847291"
    │           ✓ UPDATED
    │
    ├─► All database changes committed ✓
    │
    └─► Return HTTP 200 OK
        │
        ▼
CLIENT SIDE
    │
    └─► Show success message
        "Password changed successfully!"
        Redirect to login page
```

---

## 4. Error Scenarios & Handling

### Scenario A: Invalid Email on Request

```
POST /api/users/me/password-recovery-request
{
  "email": "nonexistent@example.com"
}
    │
    ▼
PasswordRecoveryRequestUseCase.execute()
    ├─► PasswordRecovery.create(email, true, ...)
    ├─► Validation: if(email.isEmpty())
    │   throw InvalidDataException
    └─► HTTP 400 Bad Request
        {
          "error": "requestBy cannot be null or empty"
        }
```

### Scenario B: Invalid Code on Recovery

```
POST /api/users/me/password-recovery
{
  "attachedId": "999999",
  "newPassword": "..."
}
    │
    ▼
PasswordRecoveryUseCase.execute()
    │
    ├─► Query: SELECT FROM password_recovery
    │   WHERE attached_id = "999999"
    │
    └─► No record found
        └─► throw InvalidPasswordRecoveryIdException
            HTTP 400 Bad Request
            {
              "error": "Invalid password recovery code"
            }
```

### Scenario C: Reusing Expired Code

```
POST /api/users/me/password-recovery
{
  "attachedId": "847291",  (Already used)
  "newPassword": "..."
}
    │
    ▼
PasswordRecoveryUseCase.execute()
    │
    ├─► Query returns:
    │   {
    │     attachedId: "847291",
    │     isExpired: false  ◄─── Already marked as used
    │   }
    │
    ├─► Check: if(isExpired) throw exception
    │   Logic: false means "already used/expired"
    │   (Semantic confusion here!)
    │
    └─► HTTP 400 Bad Request
        {
          "error": "The password recovery code is expired!"
        }
```

**Note:** There's a semantic inconsistency in the field naming:
- When creating: `isExpired = true` means "not yet expired" (valid)
- When validating: `if(isExpired) throw exception` checks if code is expired
- After use: `isExpired = false` means "has been expired" (used)

---

## 5. Database State Transitions

### Password Recovery Table

```
Timeline:

T0: Request Recovery
    ┌──────────────────────────────────────────┐
    │ No record exists yet                     │
    └──────────────────────────────────────────┘
             │
             ▼ repo.save()
    ┌──────────────────────────────────────────┐
    │ T1: After Request                        │
    ├──────────────────────────────────────────┤
    │ attached_id      : 847291                │
    │ requested_by     : user@example.com      │
    │ is_expired       : 1 (true = valid)      │
    │ requested_at     : 2026-01-05 22:51:24   │
    │ expired_at       : NULL                  │
    └──────────────────────────────────────────┘
             │
             ▼ User uses code
    ┌──────────────────────────────────────────┐
    │ T2: After Recovery                       │
    ├──────────────────────────────────────────┤
    │ attached_id      : 847291 (same)         │
    │ requested_by     : user@example.com      │
    │ is_expired       : 0 (false = used)      │
    │ requested_at     : 2026-01-05 22:51:24   │
    │ expired_at       : 2026-01-05 22:52:10   │
    └──────────────────────────────────────────┘
             │
             ▼ User tries again with same code
    ┌──────────────────────────────────────────┐
    │ Query finds record with is_expired = 0   │
    │ Logic: if(false) = if(isExpired)         │
    │ ✓ Does not throw (code is still valid)   │
    │ ❌ BUG: Should reject code, but accepts it
    └──────────────────────────────────────────┘
```

### User Table

```
Timeline:

T0: Initial State
    ┌───────────────────────────────────────────────┐
    │ User exists with old password hash            │
    ├───────────────────────────────────────────────┤
    │ id          : 550e8400-...                    │
    │ email       : user@example.com                │
    │ username    : john_doe                        │
    │ password    : $2a$10$oldHashValue...          │
    │ updated_at  : 2026-01-01 10:00:00             │
    └───────────────────────────────────────────────┘
             │
             ▼ User calls passwordRecovery with new password
    ┌───────────────────────────────────────────────┐
    │ T1: After Password Change                     │
    ├───────────────────────────────────────────────┤
    │ id          : 550e8400-...                    │
    │ email       : user@example.com                │
    │ username    : john_doe                        │
    │ password    : $2a$10$newHashValue...          │ ◄─ Changed!
    │ updated_at  : 2026-01-05 22:52:10             │ ◄─ Updated!
    └───────────────────────────────────────────────┘
             │
             ▼ User logs in with new password
    ┌───────────────────────────────────────────────┐
    │ Authentication successful                     │
    │ User can access account                       │
    └───────────────────────────────────────────────┘
```

---

## 6. Class Interaction Diagram

```
┌────────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER                         │
│  AuthController                                                │
│  ├── @PostMapping("/me/password-recovery-request")            │
│  └── @PostMapping("/me/password-recovery")                    │
└────────────────────┬─────────────────────────────────────────┘
                     │ uses
                     ▼
┌────────────────────────────────────────────────────────────────┐
│                    APPLICATION LAYER                           │
│                                                                │
│  AuthService                                                  │
│  ├── passwordRecoveryRequest(cmd)                            │
│  └── passwordRecovery(cmd)                                   │
│       │                                                       │
│       ├─► PasswordRecoveryRequestUseCase                     │
│       │   └── execute(cmd)                                  │
│       │       ├─► PasswordRecovery.create()                │
│       │       ├─► repo.save()                              │
│       │       └─► emailSending.sendEmail()                │
│       │                                                      │
│       └─► PasswordRecoveryUseCase                           │
│           └── execute(cmd)                                 │
│               ├─► repo.findById()                          │
│               ├─► userRepository.findByEmail()             │
│               ├─► passwordHasher.hash()                    │
│               ├─► user.changePassword()                    │
│               ├─► userRepository.save()                    │
│               └─► repo.updateExpiredStatus()               │
└────────────────────┬─────────────────────────────────────────┘
                     │ implements
                     ▼
┌────────────────────────────────────────────────────────────────┐
│                      DOMAIN LAYER                              │
│                                                                │
│  PasswordRecovery (Entity)                                    │
│  ├── attachedId: String                                      │
│  ├── requestBy: String                                       │
│  ├── isExpired: boolean                                      │
│  ├── requestedAt: Timestamp                                  │
│  └── expiredAt: Timestamp                                    │
│      ├── static create()                                     │
│      ├── static createWithAttachedId()                       │
│      └── static generateAttachedId()                         │
│                                                               │
│  User (Entity)                                               │
│  ├── id: UserId                                              │
│  ├── email: Email                                            │
│  ├── username: UserName                                      │
│  ├── password: Password                                      │
│  └── changePassword(password)                                │
│                                                               │
│  Password (Value Object)                                     │
│  └── hashed(hash): Password                                  │
└────────────────────┬─────────────────────────────────────────┘
                     │ persistence
                     ▼
┌────────────────────────────────────────────────────────────────┐
│                   INFRASTRUCTURE LAYER                         │
│                                                                │
│  PasswordRecoveryRepositoryAdapter                           │
│  ├── @Component                                              │
│  ├── @Transactional                                          │
│  ├── findById(id)                                            │
│  ├── save(entity)                                            │
│  └── updateExpiredStatus(token)                              │
│       │                                                       │
│       ▼ delegates to                                         │
│  PasswordRecoveryJpaRepository                               │
│  └── JpaRepository<PasswordRecoveryJpaEntity, String>        │
│       ├── findById(id)                                       │
│       └── save(entity)                                       │
│                                                               │
│  JpaUserRepositoryAdapter                                    │
│  └── findByEmail(email)                                      │
│       │                                                       │
│       ▼ delegates to                                         │
│  UserJpaRepository                                           │
│  └── findByEmail(email)                                      │
│                                                               │
│  EmailSendingAdapter                                         │
│  └── sendEmail(email)                                        │
│      ├── Load template                                       │
│      ├── Render template                                     │
│      └── Send via SMTP                                       │
└────────────────────┬─────────────────────────────────────────┘
                     │
                     ▼
┌────────────────────────────────────────────────────────────────┐
│                      DATABASE                                  │
│                                                                │
│  password_recovery table                                      │
│  └── attached_id (PK): VARCHAR(10)                           │
│      requested_by: VARCHAR(255)                              │
│      is_expired: BOOLEAN                                      │
│      requested_at: TIMESTAMP                                  │
│      expired_at: TIMESTAMP                                    │
│                                                               │
│  user table                                                   │
│  └── id (PK): VARCHAR(36)                                    │
│      email: VARCHAR(255)                                      │
│      password: VARCHAR(255)                                   │
│      ...                                                      │
└────────────────────────────────────────────────────────────────┘
```

---

## 7. Transaction Flow & Rollback

```
REQUEST RECOVERY TRANSACTION
┌────────────────────────────────────────────────┐
│ @Transactional Start                           │
│ (AuthService.passwordRecoveryRequest)          │
├────────────────────────────────────────────────┤
│                                                │
│ ① PasswordRecovery.create()      ✓ Success    │
│ ② repo.save()                    ✓ Success    │
│                                                │
│    If either fails:                           │
│    └─► Trigger ROLLBACK                      │
│        ├─► Undo INSERT password_recovery     │
│        └─► Return 500 error                  │
│                                                │
│ ③ emailSending.sendEmail()      ✓ Success    │
│                                                │
├────────────────────────────────────────────────┤
│ @Transactional Commit                          │
│ (If all steps succeed)                        │
└────────────────────────────────────────────────┘

RECOVER PASSWORD TRANSACTION
┌────────────────────────────────────────────────┐
│ @Transactional Start                           │
│ (AuthService.passwordRecovery)                 │
├────────────────────────────────────────────────┤
│                                                │
│ ① repo.findById()                ✓ Success    │
│ ② Validation (is_expired check)  ✓ Success    │
│ ③ userRepository.findByEmail()   ✓ Success    │
│ ④ passwordHasher.hash()          ✓ Success    │
│ ⑤ user.changePassword()          ✓ Success    │
│ ⑥ userRepository.save()          ✓ Success    │
│ ⑦ repo.updateExpiredStatus()     ✓ Success    │
│                                                │
│    If any step fails:                         │
│    └─► Trigger ROLLBACK                      │
│        ├─► Undo UPDATE user password         │
│        ├─► Undo UPDATE password_recovery     │
│        └─► Return error                      │
│                                                │
├────────────────────────────────────────────────┤
│ @Transactional Commit                          │
│ (If all steps succeed)                        │
│ ├─► Commit UPDATE user                        │
│ └─► Commit UPDATE password_recovery           │
└────────────────────────────────────────────────┘
```

---

## 8. State Machine

```
PASSWORD RECOVERY CODE STATE MACHINE

                    ┌─────────────────┐
                    │  NOT_GENERATED  │
                    └────────┬────────┘
                             │
                   requestPasswordRecovery()
                             │
                             ▼
                    ┌─────────────────┐
                    │     VALID       │  ◄─ is_expired = true
                    │   (isExpired:1) │     requestedAt = NOW()
                    │                 │     expiredAt = NULL
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
         (1)  │          (2) │          (3) │
              │              │              │
              ▼              ▼              ▼
         ┌─────────┐   ┌──────────┐   ┌──────────┐
         │ EXPIRED │   │ REUSED   │   │ SUCCESS  │
         │(timeout)│   │(invalid) │   │(changed) │
         └─────────┘   └──────────┘   └────┬─────┘
                                            │
                                      updateExpiredStatus()
                                            │
                                            ▼
                                   ┌─────────────────┐
                                   │      USED       │
                                   │  (isExpired:0)  │
                                   │ expiredAt = NOW()
                                   └─────────────────┘

State Transitions:
1. VALID → EXPIRED: Code not used within time limit
   - handled by frontend/backend validation
   - Check: requestedAt + 24h < NOW()

2. VALID → REUSED: User tries to use code that was already used
   - Check in updateExpiredStatus()
   - if(isExpired == false) reject

3. VALID → USED: User successfully changes password
   - Set isExpired = false
   - Set expiredAt = NOW()
```

---

## Summary

This comprehensive flow documentation covers:
- ✅ High-level user interactions
- ✅ Detailed step-by-step processes
- ✅ API request/response flows
- ✅ Database state transitions
- ✅ Error scenarios and handling
- ✅ Transaction boundaries
- ✅ Class interactions
- ✅ State machine diagram

Each flow includes specific details about what happens at each layer (security, controller, service, usecase, adapter, database).

