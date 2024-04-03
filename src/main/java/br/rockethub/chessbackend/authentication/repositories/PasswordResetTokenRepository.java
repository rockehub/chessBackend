package br.rockethub.chessbackend.authentication.repositories;

import br.rockethub.chessbackend.authentication.entities.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, String> {

    PasswordResetToken findByPasswordResetToken(int passwordResetToken);
}
