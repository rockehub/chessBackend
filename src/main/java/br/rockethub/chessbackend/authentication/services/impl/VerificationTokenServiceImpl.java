package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.entities.VerificationToken;
import br.rockethub.chessbackend.authentication.repositories.VerificationTokenRepository;
import br.rockethub.chessbackend.authentication.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static br.rockethub.chessbackend.authentication.services.impl.ServiceConstants.EXPIRATION;
import static br.rockethub.utils.commons.CommonsUtils.calculateExpiryDate;

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




    @Autowired
    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }
}
