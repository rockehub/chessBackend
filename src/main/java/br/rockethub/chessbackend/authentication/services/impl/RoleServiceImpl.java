package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.authentication.entities.Privilege;
import br.rockethub.chessbackend.authentication.entities.Role;
import br.rockethub.chessbackend.authentication.services.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(final Collection<Role> roles) {

        return roles.stream()
                .flatMap(role -> role.getPrivileges().stream())
                .map(Privilege::getName)
                .collect(Collectors.toList());
    }

    private List<GrantedAuthority> getGrantAuthorities(List<String> privileges) {
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
