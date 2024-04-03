package br.rockethub.chessbackend.authentication.services;

import br.rockethub.chessbackend.authentication.data.PasswordChangeForm;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.exceptions.*;

public interface AuthenticationService {

    void register(User user) throws EmailExistsException, UsernameExistsException;

    void verifyToken(int token) throws InvalidTokenException, ExpiredTokenException;

    void resendVerificationToken(String email) throws EmailNotExistException;

    void resetPasswordByEmail(String email) throws EmailNotExistException;

    void verifyResetPasswordToken(int token, PasswordChangeForm passwordChangeForm) throws InvalidTokenException, ExpiredTokenException;
}
