package com.tracking_money_flow.user.infrastructure.persistence.adapter;

import com.tracking_money_flow.user.application.port.out.EmailSending;
import com.tracking_money_flow.user.domain.PasswordRecovery;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class EmailSendingAdapter implements EmailSending {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    public EmailSendingAdapter(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String to) {

        String attatchedId = PasswordRecovery.generateAttachedId();

        Context context = new Context();

        context.setVariable("username", to.split("@")[0]);
        context.setVariable("resetLink", "http://localhost:3000/password-recovery?token=" + attatchedId);

        String body = templateEngine.process("password-recovery", context);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Password Recovery");
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
