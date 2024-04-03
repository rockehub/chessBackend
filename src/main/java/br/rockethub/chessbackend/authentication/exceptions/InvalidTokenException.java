package br.rockethub.chessbackend.authentication.exceptions;

public class InvalidTokenException  extends Throwable {

    public InvalidTokenException(final String message) {
        super("Invalid Token: " + message);
    }
}
