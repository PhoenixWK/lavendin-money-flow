package com.tracking_money_flow.user.application.port.in;

import com.tracking_money_flow.user.application.command.PasswordRecoveryRequestCommand;
import com.tracking_money_flow.user.application.port.out.EmailSending;
import com.tracking_money_flow.user.application.port.out.PasswordRecoveryRepository;
import com.tracking_money_flow.user.domain.PasswordRecovery;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PasswordRecoveryRequestUseCase {
    private final EmailSending emailSending;
    private final PasswordRecoveryRepository repo;

    public PasswordRecoveryRequestUseCase(
            EmailSending emailSending,
            PasswordRecoveryRepository repo
    ) {
        this.emailSending = emailSending;
        this.repo = repo;
    }

    public void execute(PasswordRecoveryRequestCommand cmd) {
        repo.save(PasswordRecovery.create(cmd.email(), false, Timestamp.valueOf(LocalDateTime.now()), null));
        emailSending.sendEmail(cmd.email());
    }
}
