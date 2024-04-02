package br.rockethub.chessbackend.authentication.services.impl;

import org.springframework.beans.factory.annotation.Value;

public class ServiceConstants {
    public static int EXPIRATION;


    @Value("${token.expiration}")
    public void setExpiration(int expiration) {
        EXPIRATION = expiration;
    }
}
