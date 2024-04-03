package br.rockethub.chessbackend.authentication.exceptions;

public class EmailNotExistException extends Throwable{
    public EmailNotExistException(final String email) {
        super("There is no account with that email address: " + email);
    }
}
