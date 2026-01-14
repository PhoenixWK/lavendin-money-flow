package com.tracking_money_flow.user.application.usecase;



import com.tracking_money_flow.user.application.command.GoogleLoginCommand;
import com.tracking_money_flow.user.application.port.in.GoogleLoginUseCase;
import com.tracking_money_flow.user.application.port.out.TokenProvider;
import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GoogleLoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenProvider tokenProvider;

    private GoogleLoginUseCase googleLoginUseCase;

    @BeforeEach
    void setUp() {
        googleLoginUseCase = new GoogleLoginUseCase(userRepository, tokenProvider);
    }

    @Test
    void shouldReturnTokenWhenUserExists() {
        // Arrange
        String email = "user@example.com";
        String name = "John Doe";
        GoogleLoginCommand command = new GoogleLoginCommand(email, name);

        Email emailValueObject = new Email(email);
        User existingUser = User.registerWithGoogle(emailValueObject);

        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.of(existingUser));
        when(tokenProvider.generateToken(existingUser)).thenReturn("jwt-token");

        // Act
        String token = googleLoginUseCase.execute(command);

        // Assert
        assertNotNull(token);
        assertEquals("jwt-token", token);
        verify(userRepository, times(1)).findByEmail(emailValueObject);
        verify(tokenProvider, times(1)).generateToken(existingUser);
        verify(userRepository, never()).saveUserWithReturnValue(any());
    }

    @Test
    void shouldCreateNewUserAndReturnTokenWhenUserDoesNotExist() {
        // Arrange
        String email = "newuser@example.com";
        String name = "New User";
        GoogleLoginCommand command = new GoogleLoginCommand(email, name);

        Email emailValueObject = new Email(email);

        when(userRepository.findByEmail(emailValueObject)).thenReturn(Optional.empty());

        User newUser = User.registerWithGoogle(emailValueObject);
        when(userRepository.saveUserWithReturnValue(any(User.class))).thenReturn(newUser);
        when(tokenProvider.generateToken(newUser)).thenReturn("jwt-token");

        // Act
        String token = googleLoginUseCase.execute(command);

        // Assert
        assertNotNull(token);
        assertEquals("jwt-token", token);
        verify(userRepository, times(1)).findByEmail(emailValueObject);
        verify(userRepository, times(1)).saveUserWithReturnValue(any(User.class));
        verify(tokenProvider, times(1)).generateToken(newUser);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        // Arrange
        GoogleLoginCommand command = new GoogleLoginCommand("invalid-email", "John");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> googleLoginUseCase.execute(command));
    }
}

