package br.rockethub.chessbackend.authentication.services;

import br.rockethub.chessbackend.authentication.entities.User;

import java.util.Date;

public interface VerificationTokenService {


    void createVerificationToken(User user, int token);
}
