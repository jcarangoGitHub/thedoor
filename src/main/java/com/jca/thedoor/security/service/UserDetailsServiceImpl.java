package com.jca.thedoor.security.service;

import com.jca.thedoor.entity.mongodb.Role;
import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.entity.mongodb.UserRole;
import com.jca.thedoor.repository.mongodb.RoleRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUserNameOrEmailOrCellPhoneNumber(username, username, username);

        //TODO exception
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Set<UserRole> userRoles = new HashSet<>();
        user.getRoles().forEach(rol -> {
            Optional<Role> res = roleRepository.findById(rol);
            if (res.isPresent()) {
                Role role = res.get();
                UserRole userRole = new UserRole();
                userRole.setRole(role);
                userRoles.add(userRole);
            }

        });
        user.setUserRoles(userRoles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), getAuthority(user));
    }

    private Set<GrantedAuthority> getAuthority(User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getAuthorities()
            .forEach(role -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()));
            });

        return grantedAuthorities;
    }
}

