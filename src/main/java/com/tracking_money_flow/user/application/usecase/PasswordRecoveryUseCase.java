package com.tracking_money_flow.user.application.usecase;

import com.tracking_money_flow.user.application.command.PasswordRecoveryCommand;
import com.tracking_money_flow.user.application.port.EmailSending;

public class PasswordRecoveryUseCase {
    private EmailSending emailSending;

    public PasswordRecoveryUseCase(EmailSending emailSending) {
        this.emailSending = emailSending;
    }

    public void execute(PasswordRecoveryCommand cmd) {
        emailSending.sendEmail(cmd.email());
    }
}
