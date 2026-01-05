# Password Recovery Feature - Complete Documentation Index

## Overview

This comprehensive documentation suite covers the password recovery feature of the Lavendin Money Flow application. The feature enables users to securely reset forgotten passwords through a two-step verification process.

---

## 📚 Documentation Files

### 1. **PASSWORD_RECOVERY_FLOW.md** (Main Documentation)
   - **Purpose:** Comprehensive explanation of password recovery flows
   - **Contents:**
     - System architecture (5-layer Clean Architecture)
     - Flow 1: Request Password Recovery (9 steps)
     - Flow 2: Recover Password (11 steps)
     - Data models and entities
     - Error handling scenarios
     - Transaction boundaries
     - Security considerations
     - Testing checklist
     - Performance optimization
     - Future enhancements
   - **Best for:** Understanding business logic and system design

### 2. **PASSWORD_RECOVERY_VISUAL_FLOWS.md** (Visual Diagrams)
   - **Purpose:** ASCII diagrams and visual flow charts
   - **Contents:**
     - High-level system flow
     - Request recovery detailed flow (8 diagrams)
     - Recover password detailed flow (6 diagrams)
     - Error scenarios (3 diagrams)
     - Database state transitions
     - Class interaction diagram
     - Transaction flow & rollback
     - State machine diagram
   - **Best for:** Visual learners and quick reference

### 3. **PASSWORD_RECOVERY_API_GUIDE.md** (Testing & API)
   - **Purpose:** API specifications and testing guide
   - **Contents:**
     - API endpoints overview
     - Request/response specifications
     - cURL examples (8 test scenarios)
     - Postman collection JSON
     - Complete E2E test scenario
     - Database query examples
     - Troubleshooting guide
     - Load testing procedures
     - Security testing
   - **Best for:** Testing, API integration, and debugging

---

## 🎯 Quick Navigation

### For Understanding the System
1. Start with **PASSWORD_RECOVERY_VISUAL_FLOWS.md** → High-Level System Flow
2. Read **PASSWORD_RECOVERY_FLOW.md** → System Architecture
3. Review step-by-step flows

### For Testing
1. Open **PASSWORD_RECOVERY_API_GUIDE.md** → Testing Guide
2. Copy cURL examples
3. Run tests against local/dev environment
4. Verify database state using provided SQL queries

### For Integration
1. Review **PASSWORD_RECOVERY_API_GUIDE.md** → API Endpoints
2. Check error codes and responses
3. Implement frontend form
4. Use Postman collection for testing
5. Handle error scenarios

### For Debugging
1. Check **PASSWORD_RECOVERY_API_GUIDE.md** → Troubleshooting
2. Review **PASSWORD_RECOVERY_FLOW.md** → Error Handling
3. Use database queries to verify state
4. Check application logs

---

## 🏗️ Architecture Layers

```
PRESENTATION LAYER
├── AuthController
│   ├── @PostMapping("/me/password-recovery-request")
│   └── @PostMapping("/me/password-recovery")
│
APPLICATION LAYER
├── AuthService @Transactional
│   ├── passwordRecoveryRequest()
│   └── passwordRecovery()
├── PasswordRecoveryRequestUseCase
│   └── execute()
└── PasswordRecoveryUseCase
    └── execute()
│
DOMAIN LAYER
├── PasswordRecovery (Entity)
│   ├── attachedId: String (6-digit code)
│   ├── requestBy: String (email)
│   ├── isExpired: boolean (1=valid, 0=used)
│   ├── requestedAt: Timestamp
│   └── expiredAt: Timestamp
└── User (Entity)
    ├── id, email, password
    └── changePassword()
│
INFRASTRUCTURE LAYER
├── PasswordRecoveryRepositoryAdapter @Component @Transactional
│   ├── save()
│   ├── findById()
│   └── updateExpiredStatus()
├── PasswordRecoveryJpaRepository (JPA)
├── EmailSendingAdapter
│   └── sendEmail()
└── JpaUserRepositoryAdapter
│
DATABASE LAYER
├── password_recovery table
├── user table
└── Indexes
```

---

## 📊 Data Flow

### Request Recovery (Flow 1)
```
User Input
    ↓
API Request (email)
    ↓
Security Check ✓
    ↓
Controller → Service → UseCase
    ↓
Generate 6-digit code
    ↓
Save to password_recovery table
    ↓
Send email notification
    ↓
HTTP 200 OK
```

### Recover Password (Flow 2)
```
User Input
    ↓
API Request (code, password)
    ↓
Security Check ✓
    ↓
Controller → Service → UseCase
    ↓
Find password_recovery record by code
    ↓
Validate code is not expired
    ↓
Find user by email
    ↓
Hash new password
    ↓
Update user password
    ↓
Mark code as used
    ↓
HTTP 200 OK
```

---

## 🔑 Key Components

### 1. PasswordRecoveryRepositoryAdapter
- **Responsibility:** Bridge between domain and persistence
- **Key Features:**
  - @Component: Spring-managed bean
  - @Transactional: Manages database transactions
  - Methods: save(), findById(), updateExpiredStatus()

### 2. PasswordRecoveryJpaRepository
- **Responsibility:** JPA/Hibernate database operations
- **Methods:** Extends JpaRepository<PasswordRecoveryJpaEntity, String>

### 3. EmailSendingAdapter
- **Responsibility:** Send recovery emails
- **Features:**
  - Template-based email generation
  - SMTP configuration
  - Synchronous delivery

### 4. PasswordRecovery Entity
- **Responsibility:** Domain logic for password recovery
- **Methods:**
  - create(): Factory method to create new recovery request
  - generateAttachedId(): Generate 6-digit code
  - Validation logic

---

## 📝 Database Schema

### password_recovery Table
```sql
CREATE TABLE password_recovery (
    attached_id VARCHAR(10) PRIMARY KEY,           -- 6-digit code
    requested_by VARCHAR(255) NOT NULL,            -- User email
    is_expired BOOLEAN NOT NULL DEFAULT TRUE,      -- 1=valid, 0=used
    requested_at TIMESTAMP NOT NULL,               -- Creation time
    expired_at TIMESTAMP NULL                      -- Usage time
);

CREATE INDEX idx_password_recovery_requested_by 
ON password_recovery(requested_by);
```

**States:**
- **New Record:** is_expired=1, expired_at=NULL → Code is valid
- **After Use:** is_expired=0, expired_at=NOW() → Code has been used

---

## 🔐 Security Features

1. **Code Generation**
   - Random 6-digit code (1 million combinations)
   - Stored as primary key (unique per request)

2. **Single Use**
   - Code can only be used once
   - Marked as expired after successful use
   - Cannot be reused

3. **Email Verification**
   - Only user with email access can receive code
   - Only user can perform password reset

4. **Password Hashing**
   - BCrypt with configurable cost factor
   - Salt automatically generated
   - Never stored in plaintext

5. **HTTPS/SSL**
   - All communications should use HTTPS
   - Codes transmitted over secure channel

6. **Rate Limiting** (Future)
   - Limit recovery requests per email (e.g., 5/hour)
   - Prevent brute force attacks

---

## ✅ Testing Checklist

### Unit Tests
- [ ] PasswordRecovery.create() validation
- [ ] generateAttachedId() produces 6-digit codes
- [ ] Password hashing works correctly

### Integration Tests
- [ ] Database save and retrieve operations
- [ ] Email sending functionality
- [ ] Transaction rollback on error

### API Tests
- [ ] Request recovery for valid email → 200
- [ ] Request recovery for invalid email → 400/404
- [ ] Multiple requests for same email → Both succeed
- [ ] Recover password with valid code → 200
- [ ] Recover password with invalid code → 400
- [ ] Reuse expired code → 400
- [ ] Update user database correctly
- [ ] Mark code as used correctly

### Security Tests
- [ ] SQL injection prevention
- [ ] Code guessing protection
- [ ] Code reuse prevention
- [ ] Email verification

### Performance Tests
- [ ] Response time < 500ms
- [ ] Database queries efficient (indexed)
- [ ] Email queue non-blocking
- [ ] High-volume load handling

---

## 🚀 Deployment Checklist

- [ ] Application configuration updated
  - [ ] SMTP credentials configured
  - [ ] Database migrations applied
  - [ ] Security endpoints permitted
  
- [ ] Database prepared
  - [ ] password_recovery table created
  - [ ] Indexes created
  - [ ] user table has email column
  
- [ ] Security configured
  - [ ] HTTPS/SSL enabled
  - [ ] CORS configured
  - [ ] Security headers set
  
- [ ] Email service ready
  - [ ] SMTP server accessible
  - [ ] Email templates validated
  - [ ] Test email sent successfully
  
- [ ] Monitoring & Logging
  - [ ] Error logging configured
  - [ ] Email delivery tracking
  - [ ] Database transaction logs
  - [ ] API request/response logging
  
- [ ] Documentation
  - [ ] User guide created
  - [ ] API documentation published
  - [ ] Support runbook prepared

---

## 📊 Metrics to Monitor

### Success Rate
- Percentage of successful password recovery requests
- Target: > 95%

### Email Delivery
- Percentage of emails successfully delivered
- Average delivery time
- Bounce rate

### Performance
- API response time (target: < 500ms)
- Database query time (target: < 50ms)
- Email sending time (target: < 2s)

### Security
- Failed recovery attempts per IP
- Unique recovery codes generated
- Codes successfully used
- Codes expired without use

---

## 🔧 Troubleshooting Quick Reference

| Problem | Solution |
|---------|----------|
| Email not received | Check SMTP config, verify email in request, check spam folder |
| Code not found | Generate new code, verify code format (6 digits) |
| Code already used | Request new recovery code |
| Password update fails | Check password requirements, verify user exists |
| Transaction rollback | Check database logs, verify all fields valid |
| HTTP 404 endpoint | Verify endpoint path in controller matches request |
| HTTP 403 forbidden | Check SecurityConfig permits endpoint |

---

## 📖 Related Code Files

### Controllers
- `AuthController.java` - REST endpoints

### Services
- `AuthService.java` - Service orchestration

### Use Cases
- `PasswordRecoveryRequestUseCase.java` - Request recovery logic
- `PasswordRecoveryUseCase.java` - Password reset logic

### Repositories
- `PasswordRecoveryRepository.java` - Port interface
- `PasswordRecoveryRepositoryAdapter.java` - Adapter implementation
- `PasswordRecoveryJpaRepository.java` - JPA repository

### Domain
- `PasswordRecovery.java` - Domain entity

### Configuration
- `SecurityConfig.java` - Security configuration
- `UserModuleConfig.java` - Spring beans configuration

---

## 📞 Support & Contributions

### For Questions
1. Review relevant documentation file
2. Check troubleshooting section
3. Examine database state with provided queries
4. Review application logs

### For Enhancements
1. Consider security implications
2. Update all documentation files
3. Add tests for new functionality
4. Update database schema if needed
5. Review transaction boundaries

---

## 📜 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | 2026-01-05 | Initial documentation |

---

## 🎓 Learning Path

1. **Beginner:** Start with PASSWORD_RECOVERY_VISUAL_FLOWS.md - High Level System Flow
2. **Intermediate:** Read PASSWORD_RECOVERY_FLOW.md completely
3. **Advanced:** Review PASSWORD_RECOVERY_API_GUIDE.md and database queries
4. **Expert:** Study implementation code and perform load testing

---

## 📱 Quick Reference

### Most Common Use Case
User forgot password → Request recovery → Receive email → Click link → Enter new password → Success

### Most Common Error
"Code already used" → Solution: Request new recovery code

### Best Testing Approach
1. Create test user
2. Request recovery code
3. Query database for code
4. Use code to reset password
5. Login with new password
6. Verify password changed in database

---

## Summary

This documentation provides complete coverage of the password recovery feature:
- ✅ Architecture and design patterns
- ✅ Step-by-step flows with diagrams
- ✅ API specifications with examples
- ✅ Testing procedures
- ✅ Deployment checklist
- ✅ Troubleshooting guide
- ✅ Security considerations
- ✅ Performance optimization

**Total Documentation:** 3 comprehensive files covering all aspects of the password recovery feature.

**Ready to:** Deploy, test, integrate, debug, and maintain the feature.

