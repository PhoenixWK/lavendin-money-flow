package com.tracking_money_flow.user.infrastructure.config;

import com.tracking_money_flow.user.application.port.in.*;
import com.tracking_money_flow.user.application.port.out.*;
import com.tracking_money_flow.user.application.service.AuthService;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.EmailSendingAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.UserRepositoryAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.PasswordRecoveryRepositoryAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.repository.UserJpaRepository;
import com.tracking_money_flow.user.infrastructure.security.BCryptPasswordHasher;
import com.tracking_money_flow.user.infrastructure.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Configuration
public class UserModuleConfig {

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
            UserRepositoryAdapter adapter,
            PasswordHasher hasher
    ) {
        return new RegisterUserUseCase(adapter, hasher);
    }

    @Bean
    LoginUserUseCase loginUserUseCase(
            UserRepositoryAdapter adapter,
            PasswordHasher hasher,
            TokenProvider token
    ) {
        return new LoginUserUseCase(adapter, hasher, token);
    }

    @Bean
    GoogleLoginUseCase googleLoginUseCase(
            UserRepositoryAdapter adapter,
            TokenProvider token
    ) {
        return new GoogleLoginUseCase(adapter, token);
    }

    @Bean
    PasswordRecoveryUseCase passwordRecoveryUseCase(
            UserRepositoryAdapter userRepositoryAdapter,
            PasswordHasher hasher,
            PasswordRecoveryRepositoryAdapter passwordRecoveryRepositoryAdapter
    ) {
        return new PasswordRecoveryUseCase(hasher, userRepositoryAdapter, passwordRecoveryRepositoryAdapter);
    }

    @Bean
    EmailSending emailSending(
            JavaMailSender mailSender,
            TemplateEngine templateEngine
    ) {
        return new EmailSendingAdapter(mailSender, templateEngine);
    }

    @Bean
    PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase(
            EmailSending emailSending,
            PasswordRecoveryRepositoryAdapter adapter
    ) {
        return new PasswordRecoveryRequestUseCase(emailSending, adapter);
    }

    @Bean
    GetUserWithEmailUseCase getUserWithEmailUseCase(
            UserRepositoryAdapter userRepositoryAdapter
    ) {
        return new GetUserWithEmailUseCase(userRepositoryAdapter);
    }

    @Bean
    AuthService authService(
            LoginUserUseCase loginUserUseCase,
            RegisterUserUseCase registerUserUseCase,
            PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase,
            GoogleLoginUseCase googleLoginUseCase,
            PasswordRecoveryUseCase passwordRecoveryUseCase,
            GetUserWithEmailUseCase getUserWithEmailUseCase
    ) {
        return new AuthService(
                loginUserUseCase,
                registerUserUseCase,
                passwordRecoveryRequestUseCase,
                googleLoginUseCase,
                passwordRecoveryUseCase,
                getUserWithEmailUseCase
        );
    }
}
