package br.rockethub.chessbackend.authentication.repositories;

import br.rockethub.chessbackend.authentication.entities.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, String> {
	
	Privilege findByName(String name);

}