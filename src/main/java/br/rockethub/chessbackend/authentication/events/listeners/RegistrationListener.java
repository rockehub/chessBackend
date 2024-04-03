package br.rockethub.chessbackend.authentication.events.listeners;

import br.rockethub.chessbackend.authentication.data.RegistrationTokenData;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.events.OnRegistrationCompleteEvent;
import br.rockethub.chessbackend.authentication.services.UserService;
import br.rockethub.chessbackend.authentication.services.VerificationTokenService;
import br.rockethub.chessbackend.authentication.services.impl.UserServiceImpl;
import br.rockethub.chessbackend.mail.data.MailExchangeData;
import br.rockethub.chessbackend.mail.services.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;

import static br.rockethub.utils.commons.CommonsUtils.generateTokenNumbers;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationListener.class);
    private VerificationTokenService verificationTokenService;

    private MailSenderService mailSenderService;


    private static final String EMAIL_TEMPLATE = "registration_token";


    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistrationEvent(event);
    }

    private void confirmRegistrationEvent(@NonNull OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        int token = generateTokenNumbers();

        verificationTokenService.createVerificationToken(user, token);

        RegistrationTokenData registrationTokenData = new RegistrationTokenData(event, user, token);

        logger.info("Token generated is " + token);

        mailSenderService.sendMail(user, "Registration Token", EMAIL_TEMPLATE, registrationTokenData);


    }


    @Autowired
    public void setVerificationTokenService(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }
}
