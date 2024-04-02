package br.rockethub.chessbackend.authentication.repositories;

import br.rockethub.chessbackend.authentication.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

	Role findByName(String name);

}