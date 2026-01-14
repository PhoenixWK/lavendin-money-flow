package com.tracking_money_flow.user.application.usecase;

import com.tracking_money_flow.user.application.port.in.GetUserWithEmailUseCase;
import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.domain.AuthProvider;
import com.tracking_money_flow.user.domain.DateOfBirth;
import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.Password;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.UserId;
import com.tracking_money_flow.user.domain.UserName;
import com.tracking_money_flow.user.domain.UserStatus;
import com.tracking_money_flow.user.domain.exception.NoUserFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUserWithEmailUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private GetUserWithEmailUseCase getUserWithEmailUseCase;

    @BeforeEach
    void setUp() {
        getUserWithEmailUseCase = new GetUserWithEmailUseCase(userRepository);
    }

    @Test
    void shouldReturnUserWhenUserExists() {
        // Arrange
        String email = "user@example.com";
        Email emailValueObject = new Email(email);

        User expectedUser = User.reconstruct(
                UserId.create("uuid-1"),
                emailValueObject,
                Password.hashed("hashedPassword123"),
                new UserName("john_doe"),
                AuthProvider.EMAIL_AND_PASSWORD,
                new DateOfBirth("1990-01-15"),
                UserStatus.ACTIVE,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now()
        );

        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = getUserWithEmailUseCase.execute(email);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser.getEmail().value(), result.getEmail().value());
        assertEquals(expectedUser.getUsername().value(), result.getUsername().value());
        assertEquals(expectedUser.getId(), result.getId());
        verify(userRepository, times(1)).findByEmail(emailValueObject);
    }

    @Test
    void shouldThrowNoUserFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        Email emailValueObject = new Email(email);

        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.empty());

        // Act & Assert
        NoUserFoundException exception = assertThrows(NoUserFoundException.class, () -> {
            getUserWithEmailUseCase.execute(email);
        });

        assertEquals("No user found with the provided email", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(emailValueObject);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        // Arrange
        String invalidEmail = "invalid-email";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            getUserWithEmailUseCase.execute(invalidEmail);
        });

        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void shouldReturnUserWithCorrectProperties() {
        // Arrange
        String email = "john.doe@example.com";
        Email emailValueObject = new Email(email);
        UserId userId = UserId.create("uuid-42");
        UserName username = new UserName("john_doe");

        User expectedUser = User.reconstruct(
                userId,
                emailValueObject,
                Password.hashed("hashedPassword456"),
                username,
                AuthProvider.GOOGLE,
                new DateOfBirth("1995-05-20"),
                UserStatus.ACTIVE,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now()
        );

        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = getUserWithEmailUseCase.execute(email);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(emailValueObject.value(), result.getEmail().value());
        assertEquals(username.value(), result.getUsername().value());
        assertEquals(AuthProvider.GOOGLE, result.getAuthProvider());
        assertEquals(UserStatus.ACTIVE, result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldCallUserRepositoryFindByEmailWithCorrectEmail() {
        // Arrange
        String email = "test@example.com";
        Email emailValueObject = new Email(email);

        User user = User.registerWithGoogle(emailValueObject);
        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.of(user));

        // Act
        getUserWithEmailUseCase.execute(email);

        // Assert
        verify(userRepository, times(1)).findByEmail(any(Email.class));
    }

    @Test
    void shouldReturnInactiveUserIfExists() {
        // Arrange
        String email = "inactive@example.com";
        Email emailValueObject = new Email(email);

        User inactiveUser = User.reconstruct(
                UserId.create("uuid-99"),
                emailValueObject,
                Password.hashed("hashedPassword789"),
                new UserName("inactive_user"),
                AuthProvider.EMAIL_AND_PASSWORD,
                new DateOfBirth("1992-03-10"),
                UserStatus.INACTIVE,
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
        );

        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.of(inactiveUser));

        // Act
        User result = getUserWithEmailUseCase.execute(email);

        // Assert
        assertNotNull(result);
        assertEquals(UserStatus.INACTIVE, result.getStatus());
        assertEquals(emailValueObject.value(), result.getEmail().value());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            getUserWithEmailUseCase.execute(null);
        });

        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        // Arrange
        String emptyEmail = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            getUserWithEmailUseCase.execute(emptyEmail);
        });

        verify(userRepository, never()).findByEmail(any());
    }
}

