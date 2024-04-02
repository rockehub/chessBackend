package br.rockethub.chessbackend.authentication.security;


import br.rockethub.chessbackend.authentication.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static br.rockethub.chessbackend.authentication.security.SecurityConstants.*;
import static br.rockethub.utils.security.SecurityUtils.generalKey;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    final private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credential = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        ClaimsBuilder claims = Jwts.claims().subject(user.getUsername());
        claims.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));


        String token = Jwts.builder()
                .claims(claims.build())
                .signWith(generalKey())
                .compact();

        JSONObject json = new JSONObject();
        json.put("access_token", token);
        response.setContentType("application/json");


        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);

        response.getWriter().write(json.toString());

        response.flushBuffer();





        logger.info("User " + user.getUsername() + " has been authenticated successfully");
        logger.info("Token: " + token);

    }


}
