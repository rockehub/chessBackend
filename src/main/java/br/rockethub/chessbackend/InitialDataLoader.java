package br.rockethub.chessbackend;

import java.util.*;


import br.rockethub.chessbackend.authentication.entities.Privilege;
import br.rockethub.chessbackend.authentication.entities.Role;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.repositories.PrivilegeRepository;
import br.rockethub.chessbackend.authentication.repositories.RoleRepository;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ADMIN_ROLE", adminPrivileges);
        createRoleIfNotFound("USER_ROLE", Collections.singletonList(readPrivilege));

        Role adminRole = roleRepository.findByName("ADMIN_ROLE");
        User user = new User();
        user.setName("test");
        user.setSurname("Test");
        user.setUsername("test");
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Collections.singletonList(adminRole));
        //user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public void createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}