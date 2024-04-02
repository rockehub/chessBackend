package br.rockethub.chessbackend.authentication.repositories;

import br.rockethub.chessbackend.authentication.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    User findByEmail(String email);
    User findByUsername(String username);
}
