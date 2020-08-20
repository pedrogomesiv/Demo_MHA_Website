package com.nsa.mhasite.services;

import com.nsa.mhasite.config.MyUserPrincipal;
import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.UserRolesRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private UserRolesRepositoryJPA userRolesRepositoryJPA;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepositoryJPA.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            List<String> userRoles = userRolesRepositoryJPA.findRoleByUsername(username);
            return new MyUserPrincipal(user, userRoles);
        }
    }
}
