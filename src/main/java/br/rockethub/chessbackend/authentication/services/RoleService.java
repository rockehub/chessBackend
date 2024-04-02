package br.rockethub.chessbackend.authentication.services;

import br.rockethub.chessbackend.authentication.entities.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface RoleService {

    Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles);
}
