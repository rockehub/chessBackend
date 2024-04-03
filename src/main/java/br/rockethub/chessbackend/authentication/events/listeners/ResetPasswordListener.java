package br.rockethub.chessbackend.authentication.events.listeners;

import br.rockethub.chessbackend.authentication.entities.PasswordResetToken;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.events.OnResetPasswordEvent;
import br.rockethub.chessbackend.mail.services.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordListener.class);


    private static final String EMAIL_TEMPLATE = "reset_password_token";

    private MailSenderService mailSenderService;

    @Override
    public void onApplicationEvent(@NonNull OnResetPasswordEvent event) {
        resetPasswordEvent(event);
    }

    private void resetPasswordEvent(OnResetPasswordEvent event) {
        User user = event.getUser();
        PasswordResetToken passwordResetToken = user.getPasswordResetToken();

        logger.info("Password reset token is " + passwordResetToken.getPasswordResetToken());

        mailSenderService.sendMail(user, "Password Reset Token", EMAIL_TEMPLATE, passwordResetToken);
        

    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }
}
