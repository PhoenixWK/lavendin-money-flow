package com.tracking_money_flow.user.infrastructure.api.controller;

import com.tracking_money_flow.common.response.Response;
import com.tracking_money_flow.user.application.command.*;
import com.tracking_money_flow.user.application.service.AuthService;
import com.tracking_money_flow.user.infrastructure.api.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;


    public AuthController(
            AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v1/auth/register")
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

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        LoginCommand command = new LoginCommand(request.email(), request.password());
        String token = authService.login(command);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/api/v1/auth/google/login")
    public ResponseEntity<String> googleLogin(
            @RequestBody GoogleUserInfo googleUserInfo
    ) {
        GoogleLoginCommand command = new GoogleLoginCommand(googleUserInfo.email(), googleUserInfo.name());
        String token = authService.loginWithGoogle(command);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/api/v1/auth/password-recovery-request")
    public ResponseEntity<?> recoverPasswordRequest(
            @RequestBody PasswordRecoveryRequest request
    ) {
        PasswordRecoveryRequestCommand cmd = new PasswordRecoveryRequestCommand(request.email());
        authService.passwordRecoveryRequest(cmd);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/auth/me/password-recovery")
    public ResponseEntity<Response<Object>> recoveryPassword(
            @RequestBody PasswordRecovery passwordRecovery
    ) {
        PasswordRecoveryCommand cmd = new PasswordRecoveryCommand(passwordRecovery.attachedId(), passwordRecovery.newPassword());
        authService.passwordRecovery(cmd);

        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .status(HttpStatus.OK.value())
                        .message("Password is successfully recovered")
                        .build()
        );

    }

    @GetMapping("/api/v1/users/me")
    public ResponseEntity<Response<Object>> getUserByEmail(
            @RequestParam String email
    ) {
        var user = authService.getUserWithEmail(email);


        return ResponseEntity.status(HttpStatus.OK).body(
                Response.builder()
                        .status(HttpStatus.OK.value())
                        .message("User fetched successfully")
                        .data(user)
                        .build()
        );
    }
}
