package com.tracking_money_flow.user.infrastructure.security;

import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.exception.NoUserFoundException;
import com.tracking_money_flow.user.infrastructure.persistence.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StringBuilder email = new StringBuilder(username);
        email.append("@gmail.com"); // Append domain to form complete email

        User user = repo.findByEmail(new Email(email.toString()))
                .orElseThrow(() -> new NoUserFoundException("User not found with username: " + username));

        return CustomUserDetails.builder()
                .user(UserMapper.toEntity(user))
                .build();

    }

}
