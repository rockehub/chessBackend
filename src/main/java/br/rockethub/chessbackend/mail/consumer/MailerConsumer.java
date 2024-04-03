package br.rockethub.chessbackend.mail.consumer;


import br.rockethub.chessbackend.mail.data.MailExchangeData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Date;


@Component
public class MailerConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MailerConsumer.class);


    private VelocityEngine velocityEngine;

    private JavaMailSender mailSender;

    @RabbitListener(queues = "mailQueue")
    public void sendMail(MailExchangeData mail) throws MessagingException {
        VelocityContext context = new VelocityContext();

        context.put("data", mail.getData());
        context.put("dateTool", new DateTool());
        context.put("to", mail.getTo());

        StringWriter string = new StringWriter();
        velocityEngine.mergeTemplate(mail.getTemplate() + ".vm", "UTF-8", context, string);
        String message = string.toString();
        logger.info("Sending mail to " + mail.getTo().getEmail());

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mailMessage.setTo(mail.getTo().getEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(message, true);

        mailSender.send(mimeMessage);


    }

    @Autowired
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
