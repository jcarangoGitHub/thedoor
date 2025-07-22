package com.jca.thedoor.security.service;

import com.jca.thedoor.entity.mongodb.Authentication;
import com.jca.thedoor.entity.mongodb.Role;
import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.entity.mongodb.UserRole;
import com.jca.thedoor.repository.mongodb.RoleRepository;
import com.jca.thedoor.repository.mongodb.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Autentica un usuario de la base de datos
 *
 * Authentication Manager llama al método loadUserByUsername de esta clase
 * para obtener los detalles del usuario de la base de datos cuando
 * se intente autenticar un usuario
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication authentication = authenticationRepository.findByUsername(username);

        if (authentication == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Set<UserRole> userRoles = new HashSet<>();
        authentication.getRoles().forEach(rol -> {
            Optional<Role> res = roleRepository.findById(rol);
            if (res.isPresent()) {
                Role role = res.get();
                UserRole userRole = new UserRole();
                userRole.setRole(role);
                userRoles.add(userRole);
            }

        });
        authentication.setUserRoles(userRoles);

        return new org.springframework.security.core.userdetails.User(authentication.getUsername(),
                authentication.getPassword(), getAuthority(authentication));
    }

    private Set<GrantedAuthority> getAuthority(Authentication authentication) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        authentication.getAuthorities()
            .forEach(role -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()));
            });

        return grantedAuthorities;
    }
}

