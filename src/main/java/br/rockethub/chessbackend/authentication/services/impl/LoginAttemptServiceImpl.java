package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.ChessBackendApplication;
import br.rockethub.chessbackend.authentication.services.LoginAttemptService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static br.rockethub.chessbackend.authentication.security.SecurityConstants.MAX_ATTEMPTS;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
    private static final Logger logger = LoggerFactory.getLogger(ChessBackendApplication.class);

    private final LoadingCache<String, Integer> attemptCache;

    public LoginAttemptServiceImpl() {
        super();
        attemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<>() {
            @NonNull
            @Override
            public Integer load(@NonNull String key) {
                return 0;
            }
        });
    }


    @Override
    public void loginFailed(String key) {
        int attempts = 0;

        try {
            attempts = attemptCache.get(key);

        } catch (final ExecutionException e) {
            logger.error("Error while loading attempts from cache", e);
        }

        attempts++;

        attemptCache.put(key, attempts);
    }

    @Override
    public void loginSucceeded(String key) {
        attemptCache.invalidate(key);

    }

    @Override
    public boolean isBlocked(String ip) {

        try {
            return attemptCache.get(ip) >= MAX_ATTEMPTS;
        } catch (final ExecutionException e) {
            logger.error("Error while loading attempts from cache", e);
            return false;
        }

    }
}
