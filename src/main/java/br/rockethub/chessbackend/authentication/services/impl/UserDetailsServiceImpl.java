package br.rockethub.chessbackend.authentication.services.impl;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import br.rockethub.chessbackend.authentication.services.LoginAttemptService;
import br.rockethub.chessbackend.authentication.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

import static br.rockethub.utils.security.SecurityUtils.getClientIP;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private LoginAttemptService loginAttemptService;

    private HttpServletRequest request;

    private final UserRepository applicationUserRepository;

    private final RoleService roleService;


    public UserDetailsServiceImpl(UserRepository applicationUserRepository, RoleService roleService) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleService = roleService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        final String ip = getClientIP(request);

        if(loginAttemptService.isBlocked(ip)){
            throw new RuntimeException("blocked");
        }

        User applicationUser = applicationUserRepository.findByUsername(username);
        if(applicationUser == null){
            throw new UsernameNotFoundException(username);
        }

        Collection<? extends GrantedAuthority> authorityCollectionWildCard = roleService.getAuthorities(applicationUser.getRoles());

        Collection<GrantedAuthority> authorityCollection = new ArrayList<>(authorityCollectionWildCard.size());

        authorityCollection.addAll(authorityCollectionWildCard);

        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(), applicationUser.getPassword(), authorityCollection);


    }



    @Autowired
    public void setLoginAttemptService(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
