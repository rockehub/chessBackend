package br.rockethub.chessbackend.authentication.exceptions;

public class UserNotActivatedException extends Throwable{
    public UserNotActivatedException(String message) {
        super(message);
    }
}
