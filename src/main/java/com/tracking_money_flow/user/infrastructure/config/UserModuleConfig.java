package com.tracking_money_flow.user.infrastructure.config;

import com.tracking_money_flow.user.application.port.*;
import com.tracking_money_flow.user.application.service.AuthService;
import com.tracking_money_flow.user.application.usecase.*;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.EmailSendingAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.JpaUserRepositoryAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.adapter.PasswordRecoveryRepositoryAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.repository.PasswordRecoveryJpaRepository;
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
    UserRepository userRepository(UserJpaRepository jpa) {
        return new JpaUserRepositoryAdapter(jpa);
    }

    @Bean("passwordRecoveryRepository")
    PasswordRecoveryRepository passwordRecoveryRepository(PasswordRecoveryRepositoryAdapter adapter) {
        return adapter;
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
    PasswordRecoveryUseCase passwordRecoveryUseCase(
            UserRepository userRepo,
            PasswordHasher hasher,
            @Qualifier("passwordRecoveryRepository") PasswordRecoveryRepository passwordRecoveryRepo
    ) {
        return new PasswordRecoveryUseCase(hasher, userRepo, passwordRecoveryRepo);
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
            @Qualifier("passwordRecoveryRepository") PasswordRecoveryRepository repo
    ) {
        return new PasswordRecoveryRequestUseCase(emailSending, repo);
    }

    @Bean
    AuthService authService(
            LoginUserUseCase loginUserUseCase,
            RegisterUserUseCase registerUserUseCase,
            PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase,
            GoogleLoginUseCase googleLoginUseCase,
            PasswordRecoveryUseCase passwordRecoveryUseCase
    ) {
        return new AuthService(
                loginUserUseCase,
                registerUserUseCase,
                passwordRecoveryRequestUseCase,
                googleLoginUseCase,
                passwordRecoveryUseCase
        );
    }
}
