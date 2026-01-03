package com.tracking_money_flow.user.infrastructure.config;

import com.tracking_money_flow.user.application.port.EmailSending;
import com.tracking_money_flow.user.application.port.PasswordHasher;
import com.tracking_money_flow.user.application.port.TokenProvider;
import com.tracking_money_flow.user.application.service.AuthService;
import com.tracking_money_flow.user.application.usecase.GoogleLoginUseCase;
import com.tracking_money_flow.user.application.usecase.LoginUserUseCase;
import com.tracking_money_flow.user.application.usecase.PasswordRecoveryUseCase;
import com.tracking_money_flow.user.application.usecase.RegisterUserUseCase;
import com.tracking_money_flow.user.application.port.UserRepository;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.EmailSendingAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.JpaUserRepositoryAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserJpaRepository;
import com.tracking_money_flow.user.infrastructure.security.BCryptPasswordHasher;
import com.tracking_money_flow.user.infrastructure.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Configuration
public class UserModuleConfig {
    @Bean
    UserRepository userRepository(UserJpaRepository jpa) {
        return new JpaUserRepositoryAdapter(jpa);
    }

    @Bean
    PasswordHasher passwordHasher() {
        return new BCryptPasswordHasher();
    }

    @Bean
    TokenProvider tokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    RegisterUserUseCase registerUserUseCase(
            UserRepository repo,
            PasswordHasher hasher
    ) {
        return new RegisterUserUseCase(repo, hasher);
    }

    @Bean
    LoginUserUseCase loginUserUseCase(
            UserRepository repo,
            PasswordHasher hasher,
            TokenProvider token
    ) {
        return new LoginUserUseCase(repo, hasher, token);
    }

    @Bean
    GoogleLoginUseCase googleLoginUseCase(
            UserRepository repo,
            TokenProvider token
    ) {
        return new GoogleLoginUseCase(repo, token);
    }

    @Bean
    EmailSending emailSending(
            JavaMailSender mailSender,
            TemplateEngine templateEngine
    ) {
        return new EmailSendingAdapter(mailSender, templateEngine);
    }

    @Bean
    PasswordRecoveryUseCase passwordRecoveryUseCase(
            EmailSending emailSending
    ) {
        return new PasswordRecoveryUseCase(emailSending);
    }

    @Bean
    AuthService authService(
            LoginUserUseCase loginUserUseCase,
            RegisterUserUseCase registerUserUseCase,
            PasswordRecoveryUseCase passwordRecoveryUseCase,
            GoogleLoginUseCase googleLoginUseCase
    ) {
        return new AuthService(
                loginUserUseCase,
                registerUserUseCase,
                passwordRecoveryUseCase,
                googleLoginUseCase
        );
    }
}
