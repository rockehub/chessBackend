package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.authentication.data.PasswordChangeForm;
import br.rockethub.chessbackend.authentication.entities.PasswordResetToken;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.entities.VerificationToken;
import br.rockethub.chessbackend.authentication.events.OnRegistrationCompleteEvent;
import br.rockethub.chessbackend.authentication.events.OnResetPasswordEvent;
import br.rockethub.chessbackend.authentication.exceptions.*;
import br.rockethub.chessbackend.authentication.repositories.PasswordResetTokenRepository;
import br.rockethub.chessbackend.authentication.repositories.RoleRepository;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import br.rockethub.chessbackend.authentication.repositories.VerificationTokenRepository;
import br.rockethub.chessbackend.authentication.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Objects;

import static br.rockethub.chessbackend.authentication.services.impl.ServiceConstants.EXPIRATION_IN_MINUTES;
import static br.rockethub.utils.commons.CommonsUtils.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    UserRepository userRepository;
    RoleRepository roleRepository;

    VerificationTokenRepository verificationTokenRepository;

    PasswordResetTokenRepository passwordResetTokenRepository;
    ApplicationEventPublisher eventPublisher;

    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public void register(User user) throws EmailExistsException, UsernameExistsException {

        doesEmailOrUsernameExists(user);

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setIsActive(false);
        user.setActivatedAt(null);

        user.setRoles(List.of(roleRepository.findByName("USER_ROLE")));

        userRepository.save(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));


        logger.info("User registered successfully");


    }

    @Override
    public void verifyToken(int token) throws InvalidTokenException, ExpiredTokenException {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidTokenException("Invalid token");
        }

        User user = verificationToken.getUser();

        if (isNotValidToken(verificationToken.getExpiryDate())) {
            verificationTokenRepository.delete(verificationToken);
            throw new ExpiredTokenException("Token expired");
        }

        if (user.isActive()) {
            logger.warn("User already activated");
        }

        user.setActivatedAt(new Date());
        user.setIsActive(true);
        user.setToken(null);

        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

    }

    @Override
    public void resendVerificationToken(String email) throws EmailNotExistException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EmailNotExistException("Email does not exist");
        }

        if (user.isActive()) {
            logger.warn("User already activated");
            return;
        }

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

    }

    @Override
    public void resetPasswordByEmail(String email) throws EmailNotExistException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EmailNotExistException("Email does not exist");
        }

        createResetPasswordToken(user);


    }

    @Override
    public void verifyResetPasswordToken(int token, PasswordChangeForm passwordChangeForm) throws InvalidTokenException, ExpiredTokenException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByPasswordResetToken(token);

        if (Objects.isNull(passwordResetToken)) {
            throw new InvalidTokenException("Invalid token");
        }

        User user = passwordResetToken.getUser();

        if (isNotValidToken(passwordResetToken.getExpiryDate())) {
            throw new ExpiredTokenException("Token expired");
        }

        user.setPassword(bcryptEncoder.encode(passwordChangeForm.getNewPassword()));
        user.setPasswordResetToken(null);

        passwordResetTokenRepository.delete(passwordResetToken);
        userRepository.save(user);
    }


    private void createResetPasswordToken(User user) {

        int token = generateTokenNumbers();
        Date expiryDate = calculateExpiryDate(EXPIRATION_IN_MINUTES);

        PasswordResetToken whetherTokenExists = user.getPasswordResetToken();

        if (Objects.isNull(whetherTokenExists)) {
            whetherTokenExists = new PasswordResetToken(user, token, expiryDate);
            user.setPasswordResetToken(whetherTokenExists);
            passwordResetTokenRepository.save(whetherTokenExists);
        } else {
            whetherTokenExists.updateToken(token, expiryDate);
            passwordResetTokenRepository.save(whetherTokenExists);
        }

        eventPublisher.publishEvent(new OnResetPasswordEvent(user));

    }


    private void doesEmailOrUsernameExists(User newUser) throws EmailExistsException, UsernameExistsException {
        User user;
        user = userRepository.findByEmail(newUser.getEmail());
        if (user != null) {
            throw new EmailExistsException("Email already exists");
        }

        user = userRepository.findByUsername(newUser.getUsername());
        if (user != null) {
            throw new UsernameExistsException("Username already exists");
        }

    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Autowired
    public void setPasswordResetTokenRepository(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
}
