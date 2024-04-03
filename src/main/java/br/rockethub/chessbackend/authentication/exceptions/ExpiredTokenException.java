package br.rockethub.chessbackend.authentication.exceptions;

public class ExpiredTokenException  extends Throwable{

        public ExpiredTokenException(final String message) {
            super("Expired Token: " + message);
        }
}
