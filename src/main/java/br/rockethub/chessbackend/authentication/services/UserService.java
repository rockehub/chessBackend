package br.rockethub.chessbackend.authentication.services;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.utils.exceptions.EmailExistsException;
import br.rockethub.utils.exceptions.UsernameExistsException;

public interface UserService {

    void register(User user) throws EmailExistsException, UsernameExistsException;

}
