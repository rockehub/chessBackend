package br.rockethub.chessbackend.authentication.security;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import br.rockethub.chessbackend.authentication.services.RoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static br.rockethub.chessbackend.authentication.security.SecurityConstants.HEADER_STRING;
import static br.rockethub.chessbackend.authentication.security.SecurityConstants.TOKEN_PREFIX;
import static br.rockethub.utils.security.SecurityUtils.generalKey;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final UserRepository userRepository;

    private final RoleService roleService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository, RoleService roleService) {
        super(authManager);
        this.userRepository = userRepository;
        this.roleService = roleService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            logger.error("Token not found");
            chain.doFilter(request, response);

        }else {

            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser().verifyWith(generalKey())
                    .build()
                    .parseSignedClaims(token.replace(TOKEN_PREFIX, ""))
                    .getPayload();

            String user = claims.getSubject();

            try {
                User currentUser = userRepository.findByUsername(user);

                Collection<? extends GrantedAuthority> authorityCollectionWildCard = roleService.getAuthorities(currentUser.getRoles());

                Collection<GrantedAuthority> authorityCollection = new ArrayList<>(authorityCollectionWildCard.size());

                authorityCollection.addAll(authorityCollectionWildCard);


                return new UsernamePasswordAuthenticationToken(user, null, authorityCollection);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            return null;
        }

        return null;

    }
}
