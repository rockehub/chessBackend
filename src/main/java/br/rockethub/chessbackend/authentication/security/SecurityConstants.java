package br.rockethub.chessbackend.authentication.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    public static long EXPIRATION_TIME;

    public static String SECRET;

    public static int MAX_ATTEMPTS;

    public static final String HEADER_STRING = "Authorization";


    public static final String TOKEN_PREFIX = "Bearer ";


    @Value("${custom.jwt-token.expired.time}")
    public void setExp(Long exp) {
        SecurityConstants.EXPIRATION_TIME = exp;
    }

    @Value("${custom.jwt-token.secret}")
    public void setSecret(String secret) {
        SecurityConstants.SECRET = secret;
    }

    @Value("${custom.jwt-token.max-attempts}")
    public void setMaxAttempts(int maxAttempts) {
        SecurityConstants.MAX_ATTEMPTS = maxAttempts;
    }

}
