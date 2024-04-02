package br.rockethub.chessbackend.authentication.security.listeners;

import br.rockethub.chessbackend.ChessBackendApplication;
import br.rockethub.chessbackend.authentication.services.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.rockethub.utils.security.SecurityUtils.getForwarded;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    private HttpServletRequest request;

    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(@NonNull final AuthenticationFailureBadCredentialsEvent event) {

        final String forwarded = getForwarded(request);

        if (Objects.isNull(forwarded)) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
            logger.warn("AuthenticationFailureListener null");
        } else {
            loginAttemptService.loginFailed(forwarded.split("")[0]);
            logger.warn("Authentication failure forwarded");
        }

    }


    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setLoginAttemptService(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }
}
