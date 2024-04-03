package br.rockethub.chessbackend.authentication.services;

import br.rockethub.chessbackend.authentication.entities.User;

public interface VerificationTokenService {


    void createVerificationToken(User user, int token);
}
