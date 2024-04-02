package br.rockethub.chessbackend.authentication.repositories;

import br.rockethub.chessbackend.authentication.entities.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, String> {

    VerificationToken findByToken(int token);
}
