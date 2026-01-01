package com.tracking_money_flow.user.infrastructure.config;

import com.tracking_money_flow.user.application.port.PasswordHasher;
import com.tracking_money_flow.user.application.port.TokenProvider;
import com.tracking_money_flow.user.application.usecase.LoginUserUseCase;
import com.tracking_money_flow.user.application.usecase.RegisterUserUseCase;
import com.tracking_money_flow.user.application.port.UserRepository;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.JpaUserRepositoryAdapter;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserJpaRepository;
import com.tracking_money_flow.user.infrastructure.security.BCryptPasswordHasher;
import com.tracking_money_flow.user.infrastructure.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
