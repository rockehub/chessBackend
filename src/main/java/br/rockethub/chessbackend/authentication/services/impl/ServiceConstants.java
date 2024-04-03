package br.rockethub.chessbackend.authentication.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConstants {
    public static int EXPIRATION;

    public static int EXPIRATION_IN_MINUTES;


    @Value("${token.expiration.in.minutes}")
    public void setExpirationInMinutes(int expirationInMinutes) {
        EXPIRATION_IN_MINUTES = expirationInMinutes;
    }


    @Value("${token.expiration}")
    public void setExpiration(int expiration) {
        EXPIRATION = expiration;
    }
}
