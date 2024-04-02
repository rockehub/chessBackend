package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.ChessBackendApplication;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.events.OnRegistrationCompleteEvent;
import br.rockethub.chessbackend.authentication.repositories.RoleRepository;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import br.rockethub.chessbackend.authentication.services.UserService;
import br.rockethub.utils.exceptions.EmailExistsException;
import br.rockethub.utils.exceptions.UsernameExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

import static br.rockethub.chessbackend.authentication.services.impl.ServiceConstants.EXPIRATION;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    UserRepository userRepository;
    RoleRepository roleRepository;
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
}
