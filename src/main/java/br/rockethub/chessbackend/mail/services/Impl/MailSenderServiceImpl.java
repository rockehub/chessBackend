package br.rockethub.chessbackend.mail.services.Impl;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.mail.data.MailExchangeData;
import br.rockethub.chessbackend.mail.services.MailSenderService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private RabbitTemplate rabbitTemplate;

    private Queue mailQueue;


    @Override
    public void sendMail(User to, String subject, @NonNull String template, Object data) {

        rabbitTemplate.convertAndSend(mailQueue.getName(), new MailExchangeData.MailExchangeDataBuilder()
                .to(to).subject(subject).template(template).data(data).build());
    }



    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setMailQueue(Queue mailQueue) {
        this.mailQueue = mailQueue;
    }
}
