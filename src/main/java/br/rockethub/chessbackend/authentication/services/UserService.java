package br.rockethub.chessbackend.authentication.services;

import br.rockethub.chessbackend.authentication.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

     User getAuthenticatedUser() throws UsernameNotFoundException;
}
