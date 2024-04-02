package br.rockethub.chessbackend.authentication.services;

public interface LoginAttemptService {


    public void loginFailed(final  String key);

    public void loginSucceeded(final String key);

    boolean isBlocked(String ip);
}
