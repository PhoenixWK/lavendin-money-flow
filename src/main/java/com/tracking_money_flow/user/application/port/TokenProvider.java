package com.tracking_money_flow.user.application.port;

import com.tracking_money_flow.user.domain.User;

public interface TokenProvider {
    String generateToken(User user);
}

