package com.tracking_money_flow.user.infrastructure.security;

import com.tracking_money_flow.user.application.command.GoogleLoginCommand;
import com.tracking_money_flow.user.application.usecase.GoogleLoginUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final GoogleLoginUseCase googleLoginUseCase;

    public OAuth2AuthenticationSuccessHandler(GoogleLoginUseCase googleLoginUseCase) {
        this.googleLoginUseCase = googleLoginUseCase;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        assert oAuth2User != null;
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("/login?error=email_not_found");
            return;
        }

        try {
            GoogleLoginCommand command = new GoogleLoginCommand(email, Objects.requireNonNullElse(name, ""));
            String token = googleLoginUseCase.execute(command);

            // Redirect to frontend with token
            response.sendRedirect("http://localhost:3000/auth/callback?token=" + token);
        } catch (Exception e) {
            response.sendRedirect("/login?error=authentication_failed");
        }
    }
}

