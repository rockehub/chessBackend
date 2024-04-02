package br.rockethub.chessbackend.mail.services;

import br.rockethub.chessbackend.authentication.entities.User;
import org.springframework.lang.NonNull;

public interface MailSenderService {
     void  sendMail(User to, String subject, @NonNull String template, Object data);

}
