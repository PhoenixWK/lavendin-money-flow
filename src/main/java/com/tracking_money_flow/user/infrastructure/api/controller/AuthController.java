package com.tracking_money_flow.user.infrastructure.api.controller;

import com.tracking_money_flow.user.application.command.PasswordRecoveryCommand;
import com.tracking_money_flow.user.application.service.AuthService;
import com.tracking_money_flow.user.infrastructure.api.dto.GoogleUserInfo;
import com.tracking_money_flow.user.infrastructure.api.dto.LoginRequest;
import com.tracking_money_flow.user.infrastructure.api.dto.PasswordRecoveryRequest;
import com.tracking_money_flow.user.infrastructure.api.dto.RegisterUserRequest;
import com.tracking_money_flow.user.application.command.GoogleLoginCommand;
import com.tracking_money_flow.user.application.command.LoginCommand;
import com.tracking_money_flow.user.application.command.RegisterUserCommand;
import com.tracking_money_flow.user.application.usecase.GoogleLoginUseCase;
import com.tracking_money_flow.user.application.usecase.LoginUserUseCase;
import com.tracking_money_flow.user.application.usecase.RegisterUserUseCase;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.EmailSendingAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;


    public AuthController(
            AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterUserRequest request
    ) {
        RegisterUserCommand cmd =
                new RegisterUserCommand(
                        request.username(),
                        request.email(),
                        request.password(),
                        request.dateOfBirth()
                );

        authService.register(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        LoginCommand command = new LoginCommand(request.email(), request.password());
        String token = authService.login(command);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/google/login")
    public ResponseEntity<String> googleLogin(
            @RequestBody GoogleUserInfo googleUserInfo
    ) {
        GoogleLoginCommand command = new GoogleLoginCommand(googleUserInfo.email(), googleUserInfo.name());
        String token = authService.loginWithGoogle(command);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/me/password-recovery-request")
    public ResponseEntity<?> recoverPassword(
            @RequestBody PasswordRecoveryRequest request
    ) {
        PasswordRecoveryCommand cmd = new PasswordRecoveryCommand(request.email());
        authService.passwordRecovery(cmd);
        return ResponseEntity.ok().build();
    }
}
