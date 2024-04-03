package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.exceptions.UserNotActivatedException;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import br.rockethub.chessbackend.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public User getAuthenticatedUser() throws UsernameNotFoundException, UserNotActivatedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser;
        try {
            currentUser = userRepository.findByUsername(authentication.getName());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }

        if (Objects.isNull(currentUser)) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!currentUser.isActive()){
            throw new UserNotActivatedException("User not activated");
        }

        return currentUser;

    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
