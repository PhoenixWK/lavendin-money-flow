package com.tracking_money_flow.user.domain;

import com.tracking_money_flow.user.domain.exception.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordRecovery Domain Entity Tests")
class PasswordRecoveryTest {

    private static final String VALID_EMAIL = "user@example.com";
    private static final String VALID_ATTACHED_ID = "123456";
    private static final Timestamp CURRENT_TIME = Timestamp.valueOf(LocalDateTime.now());
    private static final Timestamp FUTURE_TIME = Timestamp.valueOf(LocalDateTime.now().plusHours(24));

    // ========== Nested Test Class: create() Factory Method ==========
    @Nested
    @DisplayName("create() factory method tests")
    class CreateMethodTests {

        @Test
        @DisplayName("Should create PasswordRecovery with valid parameters")
        void testCreateValidPasswordRecovery() {
            // Arrange
            boolean isExpired = true;

            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    isExpired,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertNotNull(recovery, "PasswordRecovery should not be null");
            assertNotNull(recovery.getAttachedId(), "AttachedId should be generated");
            assertEquals(VALID_EMAIL, recovery.getRequestBy(), "Email should match input");
            assertEquals(isExpired, recovery.getIsExpired(), "isExpired should match input");
            assertEquals(CURRENT_TIME, recovery.getRequestedAt(), "requestedAt should match input");
            assertNull(recovery.getExpiredAt(), "expiredAt should be null initially");
        }

        @Test
        @DisplayName("Should generate 6-digit attachedId when created")
        void testAttachedIdGeneration() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            String attachedId = recovery.getAttachedId();
            assertNotNull(attachedId, "AttachedId should not be null");
            assertEquals(6, attachedId.length(), "AttachedId should be exactly 6 characters");
            assertTrue(attachedId.matches("\\d{6}"), "AttachedId should contain only digits");
        }

        @Test
        @DisplayName("Should throw exception when requestBy is empty")
        void testCreateWithEmptyEmail() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.create("", true, CURRENT_TIME, null),
                    "Should throw InvalidDataException for empty email"
            );
            assertEquals("requestBy cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when requestBy is null")
        void testCreateWithNullEmail() {
            // Act & Assert
            assertThrows(
                    NullPointerException.class,
                    () -> PasswordRecovery.create(null, true, CURRENT_TIME, null),
                    "Should throw NullPointerException for null email"
            );
        }

        @Test
        @DisplayName("Should throw exception when requestedAt is null")
        void testCreateWithNullRequestedAt() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.create(VALID_EMAIL, true, null, null),
                    "Should throw InvalidDataException for null requestedAt"
            );
            assertEquals("requestedAt cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should create with isExpired = true (valid code)")
        void testCreateWithIsExpiredTrue() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertTrue(recovery.getIsExpired(), "isExpired should be true for newly created recovery code");
        }

        @Test
        @DisplayName("Should create with isExpired = false")
        void testCreateWithIsExpiredFalse() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    false,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertFalse(recovery.getIsExpired(), "isExpired should be false");
        }

        @Test
        @DisplayName("Should generate unique attachedIds on multiple calls")
        void testAttachedIdUniqueness() {
            // Act
            Set<String> generatedIds = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                PasswordRecovery recovery = PasswordRecovery.create(
                        VALID_EMAIL,
                        true,
                        CURRENT_TIME,
                        null
                );
                generatedIds.add(recovery.getAttachedId());
            }

            // Assert
            // While not all 100 should be unique (due to randomness), the majority should be
            assertTrue(generatedIds.size() >= 95, "Most generated IDs should be unique");
        }

        @Test
        @DisplayName("Should allow expiredAt to be null")
        void testCreateWithNullExpiredAt() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertNull(recovery.getExpiredAt(), "expiredAt can be null");
        }

        @Test
        @DisplayName("Should allow expiredAt to be set")
        void testCreateWithExpiredAt() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    false,
                    CURRENT_TIME,
                    FUTURE_TIME
            );

            // Assert
            assertEquals(FUTURE_TIME, recovery.getExpiredAt(), "expiredAt should match input");
        }
    }

    // ========== Nested Test Class: createWithAttachedId() Factory Method ==========
    @Nested
    @DisplayName("createWithAttachedId() factory method tests")
    class CreateWithAttachedIdMethodTests {

        @Test
        @DisplayName("Should create PasswordRecovery with provided attachedId")
        void testCreateWithProvidedAttachedId() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.createWithAttachedId(
                    VALID_ATTACHED_ID,
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    FUTURE_TIME
            );

            // Assert
            assertNotNull(recovery, "PasswordRecovery should not be null");
            assertEquals(VALID_ATTACHED_ID, recovery.getAttachedId(), "AttachedId should match input");
            assertEquals(VALID_EMAIL, recovery.getRequestBy(), "Email should match input");
            assertEquals(true, recovery.getIsExpired(), "isExpired should match input");
            assertEquals(CURRENT_TIME, recovery.getRequestedAt(), "requestedAt should match input");
            assertEquals(FUTURE_TIME, recovery.getExpiredAt(), "expiredAt should match input");
        }

        @Test
        @DisplayName("Should throw exception when attachedId is empty")
        void testCreateWithEmptyAttachedId() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.createWithAttachedId(
                            "",
                            VALID_EMAIL,
                            true,
                            CURRENT_TIME,
                            FUTURE_TIME
                    ),
                    "Should throw InvalidDataException for empty attachedId"
            );
            assertEquals("Attached id is not valid", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when attachedId is null")
        void testCreateWithNullAttachedId() {
            // Act & Assert
            assertThrows(
                    NullPointerException.class,
                    () -> PasswordRecovery.createWithAttachedId(
                            null,
                            VALID_EMAIL,
                            true,
                            CURRENT_TIME,
                            FUTURE_TIME
                    ),
                    "Should throw NullPointerException for null attachedId"
            );
        }

        @Test
        @DisplayName("Should throw exception when requestBy is empty")
        void testCreateWithEmptyRequestBy() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.createWithAttachedId(
                            VALID_ATTACHED_ID,
                            "",
                            true,
                            CURRENT_TIME,
                            FUTURE_TIME
                    ),
                    "Should throw InvalidDataException for empty requestBy"
            );
            assertEquals("requestBy cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when requestedAt is null")
        void testCreateWithNullRequestedAt() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.createWithAttachedId(
                            VALID_ATTACHED_ID,
                            VALID_EMAIL,
                            true,
                            null,
                            FUTURE_TIME
                    ),
                    "Should throw InvalidDataException for null requestedAt"
            );
            assertEquals("requestedAt or expireAt cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when expiredAt is null")
        void testCreateWithNullExpiredAt() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.createWithAttachedId(
                            VALID_ATTACHED_ID,
                            VALID_EMAIL,
                            true,
                            CURRENT_TIME,
                            null
                    ),
                    "Should throw InvalidDataException for null expiredAt"
            );
            assertEquals("requestedAt or expireAt cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when both requestedAt and expiredAt are null")
        void testCreateWithBothTimestampsNull() {
            // Act & Assert
            InvalidDataException exception = assertThrows(
                    InvalidDataException.class,
                    () -> PasswordRecovery.createWithAttachedId(
                            VALID_ATTACHED_ID,
                            VALID_EMAIL,
                            true,
                            null,
                            null
                    ),
                    "Should throw InvalidDataException for both null timestamps"
            );
            assertEquals("requestedAt or expireAt cannot be null or empty", exception.getMessage());
        }
    }

    // ========== Nested Test Class: generateAttachedId() Static Method ==========
    @Nested
    @DisplayName("generateAttachedId() static method tests")
    class GenerateAttachedIdTests {

        @Test
        @DisplayName("Should generate 6-digit string")
        void testGenerateAttachedIdLength() {
            // Act
            String attachedId = PasswordRecovery.generateAttachedId();

            // Assert
            assertNotNull(attachedId, "Generated ID should not be null");
            assertEquals(6, attachedId.length(), "Generated ID should be exactly 6 characters");
        }

        @Test
        @DisplayName("Should generate only digits")
        void testGenerateAttachedIdFormat() {
            // Act
            String attachedId = PasswordRecovery.generateAttachedId();

            // Assert
            assertTrue(attachedId.matches("\\d{6}"), "Generated ID should contain only digits");
        }

        @Test
        @DisplayName("Should generate different IDs on multiple calls")
        void testGenerateAttachedIdVariety() {
            // Act
            Set<String> generatedIds = new HashSet<>();
            for (int i = 0; i < 1000; i++) {
                generatedIds.add(PasswordRecovery.generateAttachedId());
            }

            // Assert
            // With 1000 calls, we should have at least 900 unique IDs (statistically)
            assertTrue(generatedIds.size() >= 900, "Generated IDs should have good variety");
        }

        @Test
        @DisplayName("Should generate IDs in range 000000-999999")
        void testGenerateAttachedIdRange() {
            // Act
            for (int i = 0; i < 1000; i++) {
                String attachedId = PasswordRecovery.generateAttachedId();
                int numericValue = Integer.parseInt(attachedId);

                // Assert
                assertTrue(numericValue >= 0 && numericValue <= 999999,
                        "Generated ID should be in valid range");
            }
        }
    }

    // ========== Nested Test Class: Getters and Setters ==========
    @Nested
    @DisplayName("Getters and Setters tests")
    class GettersSettersTests {

        private PasswordRecovery recovery;

        @BeforeEach
        void setup() {
            recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );
        }

        @Test
        @DisplayName("Should get and set attachedId")
        void testAttachedIdGetterSetter() {
            // Arrange
            String newId = "654321";

            // Act
            recovery.setAttachedId(newId);

            // Assert
            assertEquals(newId, recovery.getAttachedId(), "AttachedId should be updated");
        }

        @Test
        @DisplayName("Should get and set requestBy")
        void testRequestByGetterSetter() {
            // Arrange
            String newEmail = "newemail@example.com";

            // Act
            recovery.setRequestBy(newEmail);

            // Assert
            assertEquals(newEmail, recovery.getRequestBy(), "RequestBy should be updated");
        }

        @Test
        @DisplayName("Should get and set isExpired")
        void testIsExpiredGetterSetter() {
            // Arrange
            boolean newValue = false;

            // Act
            recovery.setIsExpired(newValue);

            // Assert
            assertEquals(newValue, recovery.getIsExpired(), "isExpired should be updated");
        }

        @Test
        @DisplayName("Should toggle isExpired between true and false")
        void testIsExpiredToggle() {
            // Arrange
            assertTrue(recovery.getIsExpired(), "Initial state should be true");

            // Act
            recovery.setIsExpired(false);

            // Assert
            assertFalse(recovery.getIsExpired(), "Should be updated to false");

            // Act
            recovery.setIsExpired(true);

            // Assert
            assertTrue(recovery.getIsExpired(), "Should be updated back to true");
        }

        @Test
        @DisplayName("Should get and set requestedAt")
        void testRequestedAtGetterSetter() {
            // Arrange
            Timestamp newTime = Timestamp.valueOf(LocalDateTime.now().minusHours(1));

            // Act
            recovery.setRequestedAt(newTime);

            // Assert
            assertEquals(newTime, recovery.getRequestedAt(), "RequestedAt should be updated");
        }

        @Test
        @DisplayName("Should get and set expiredAt")
        void testExpiredAtGetterSetter() {
            // Arrange
            Timestamp newTime = Timestamp.valueOf(LocalDateTime.now().plusHours(24));

            // Act
            recovery.setExpiredAt(newTime);

            // Assert
            assertEquals(newTime, recovery.getExpiredAt(), "ExpiredAt should be updated");
        }

        @Test
        @DisplayName("Should allow expiredAt to be set from null to timestamp")
        void testExpiredAtNullToTimestamp() {
            // Arrange
            assertNull(recovery.getExpiredAt(), "Initial expiredAt should be null");
            Timestamp newTime = FUTURE_TIME;

            // Act
            recovery.setExpiredAt(newTime);

            // Assert
            assertEquals(newTime, recovery.getExpiredAt(), "ExpiredAt should be updated to timestamp");
        }

        @Test
        @DisplayName("Should allow expiredAt to be set from timestamp to null")
        void testExpiredAtTimestampToNull() {
            // Arrange
            recovery.setExpiredAt(FUTURE_TIME);
            assertNotNull(recovery.getExpiredAt(), "ExpiredAt should be set");

            // Act
            recovery.setExpiredAt(null);

            // Assert
            assertNull(recovery.getExpiredAt(), "ExpiredAt should be null");
        }
    }

    // ========== Nested Test Class: Integration Tests ==========
    @Nested
    @DisplayName("Integration tests - Real-world scenarios")
    class IntegrationTests {

        @Test
        @DisplayName("Scenario: Create recovery, mark as used")
        void testCreateAndMarkAsUsed() {
            // Arrange & Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            String codeGenerated = recovery.getAttachedId();
            assertTrue(recovery.getIsExpired(), "Code should be valid initially");
            assertNull(recovery.getExpiredAt(), "Expiration time should be null");

            // Act - Mark as used
            recovery.setIsExpired(false);
            recovery.setExpiredAt(FUTURE_TIME);

            // Assert
            assertFalse(recovery.getIsExpired(), "Code should be marked as used");
            assertEquals(FUTURE_TIME, recovery.getExpiredAt(), "Expiration time should be set");
        }

        @Test
        @DisplayName("Scenario: Multiple recovery requests for same user")
        void testMultipleRecoveriesForSameUser() {
            // Act
            PasswordRecovery recovery1 = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            PasswordRecovery recovery2 = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertEquals(VALID_EMAIL, recovery1.getRequestBy(), "Email should match");
            assertEquals(VALID_EMAIL, recovery2.getRequestBy(), "Email should match");
            assertNotEquals(recovery1.getAttachedId(), recovery2.getAttachedId(),
                    "Different recovery codes should be generated");
        }

        @Test
        @DisplayName("Scenario: Verify recovery code lifecycle")
        void testRecoveryCodeLifecycle() {
            // Phase 1: Creation
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            assertNotNull(recovery.getAttachedId(), "Code should be generated");
            assertTrue(recovery.getIsExpired(), "Code should be valid");
            assertEquals(CURRENT_TIME, recovery.getRequestedAt(), "Request time should be set");
            assertNull(recovery.getExpiredAt(), "Expiration time should be null");

            // Phase 2: Code is used and marked
            Timestamp usageTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(5));
            recovery.setIsExpired(false);
            recovery.setExpiredAt(usageTime);

            assertFalse(recovery.getIsExpired(), "Code should be marked as used");
            assertEquals(usageTime, recovery.getExpiredAt(), "Usage time should be recorded");
        }

        @Test
        @DisplayName("Scenario: Recovery with different timestamps")
        void testRecoveryWithDifferentTimestamps() {
            // Arrange
            Timestamp requestTime = Timestamp.valueOf(LocalDateTime.now().minusHours(1));
            Timestamp expiryTime = Timestamp.valueOf(LocalDateTime.now());

            // Act
            PasswordRecovery recovery = PasswordRecovery.createWithAttachedId(
                    VALID_ATTACHED_ID,
                    VALID_EMAIL,
                    false,
                    requestTime,
                    expiryTime
            );

            // Assert
            assertEquals(requestTime, recovery.getRequestedAt(), "Request time should match");
            assertEquals(expiryTime, recovery.getExpiredAt(), "Expiry time should match");
            assertTrue(expiryTime.getTime() >= requestTime.getTime(),
                    "Expiry time should be after or equal to request time");
        }
    }

    // ========== Nested Test Class: Edge Cases ==========
    @Nested
    @DisplayName("Edge case tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle special characters in email")
        void testEmailWithSpecialCharacters() {
            // Arrange
            String specialEmail = "user+tag@example.co.uk";

            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    specialEmail,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertEquals(specialEmail, recovery.getRequestBy(), "Email with special chars should be preserved");
        }

        @Test
        @DisplayName("Should handle very long email")
        void testVeryLongEmail() {
            // Arrange
            String longEmail = "very.long.email.address.with.many.characters@subdomain.example.com";

            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    longEmail,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertEquals(longEmail, recovery.getRequestBy(), "Long email should be preserved");
        }

        @Test
        @DisplayName("Should handle whitespace around email")
        void testEmailWithWhitespace() {
            // Arrange
            String emailWithSpace = "user@example.com ";

            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    emailWithSpace,
                    true,
                    CURRENT_TIME,
                    null
            );

            // Assert
            assertEquals(emailWithSpace, recovery.getRequestBy(), "Whitespace should be preserved");
        }

        @Test
        @DisplayName("Should allow timestamp in the past")
        void testTimestampInPast() {
            // Arrange
            Timestamp pastTime = Timestamp.valueOf(LocalDateTime.now().minusDays(1));

            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    pastTime,
                    null
            );

            // Assert
            assertEquals(pastTime, recovery.getRequestedAt(), "Past timestamp should be allowed");
        }

        @Test
        @DisplayName("Should allow timestamp in the future")
        void testTimestampInFuture() {
            // Arrange
            Timestamp futureTime = Timestamp.valueOf(LocalDateTime.now().plusDays(1));

            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    futureTime,
                    null
            );

            // Assert
            assertEquals(futureTime, recovery.getRequestedAt(), "Future timestamp should be allowed");
        }

        @Test
        @DisplayName("Should handle immediate creation and usage")
        void testImmediateCreationAndUsage() {
            // Act
            PasswordRecovery recovery = PasswordRecovery.create(
                    VALID_EMAIL,
                    true,
                    CURRENT_TIME,
                    null
            );

            Timestamp usageTime = Timestamp.valueOf(LocalDateTime.now());
            recovery.setIsExpired(false);
            recovery.setExpiredAt(usageTime);

            // Assert
            assertFalse(recovery.getIsExpired(), "Should mark as used immediately");
            assertNotNull(recovery.getExpiredAt(), "Usage time should be recorded");
        }
    }
}

