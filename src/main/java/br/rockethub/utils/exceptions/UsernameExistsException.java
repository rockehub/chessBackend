package br.rockethub.utils.exceptions;

public class UsernameExistsException extends Throwable {
    public UsernameExistsException(final String username) {
        super("There is an account with that username: " + username);
    }
}
