package br.rockethub.utils.security;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static br.rockethub.chessbackend.authentication.security.SecurityConstants.SECRET;

public class SecurityUtils {

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(encodeKey);
    }


    public static String getForwarded(HttpServletRequest request){
        return request.getHeader("X-Forwarded-For");
    }


    public static String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xfHeader.split(",")[0];
        }
    }
}
