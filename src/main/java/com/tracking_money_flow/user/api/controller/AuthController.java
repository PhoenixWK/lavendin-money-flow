package com.tracking_money_flow.user.api.controller;

import com.tracking_money_flow.user.api.dto.GoogleUserInfo;
import com.tracking_money_flow.user.api.dto.LoginRequest;
import com.tracking_money_flow.user.api.dto.RegisterUserRequest;
import com.tracking_money_flow.user.application.command.GoogleLoginCommand;
import com.tracking_money_flow.user.application.command.LoginCommand;
import com.tracking_money_flow.user.application.command.RegisterUserCommand;
import com.tracking_money_flow.user.application.usecase.GoogleLoginUseCase;
import com.tracking_money_flow.user.application.usecase.LoginUserUseCase;
import com.tracking_money_flow.user.application.usecase.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final GoogleLoginUseCase googleLoginUseCase;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            LoginUserUseCase loginUserUseCase,
            GoogleLoginUseCase googleLoginUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.googleLoginUseCase = googleLoginUseCase;
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

        registerUserUseCase.execute(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        LoginCommand command = new LoginCommand(request.email(), request.password());
        String token = loginUserUseCase.execute(command);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/google/login")
    public ResponseEntity<String> googleLogin(
            @RequestBody GoogleUserInfo googleUserInfo
    ) {
        GoogleLoginCommand command = new GoogleLoginCommand(googleUserInfo.email(), googleUserInfo.name());
        String token = googleLoginUseCase.execute(command);
        return ResponseEntity.ok(token);
    }
}
