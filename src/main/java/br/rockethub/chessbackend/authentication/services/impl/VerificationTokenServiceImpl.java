package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.entities.VerificationToken;
import br.rockethub.chessbackend.authentication.repositories.VerificationTokenRepository;
import br.rockethub.chessbackend.authentication.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static br.rockethub.chessbackend.authentication.services.impl.ServiceConstants.EXPIRATION;

@Service

public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;



    @Override
    public void createVerificationToken(User user, int token) {

        Date expiryDate = calculateExpiryDate(EXPIRATION);
        VerificationToken verificationToken = new VerificationToken(user, token, expiryDate);
        user.setToken(verificationToken);

        verificationTokenRepository.save(verificationToken);


    }


    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);

        return new Date(calendar.getTime().getTime());
    }

    @Autowired
    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }
}
