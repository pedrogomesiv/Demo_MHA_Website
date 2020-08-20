package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.UserRepositoryJdbc;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ChangePasswordService {
    private UserRepositoryJPA userRepositoryJPA;
    private UserRepositoryJdbc userRepositoryJdbc;
    private PasswordEncoder passwordEncoder;

    static final Logger LOG = LoggerFactory.getLogger(ChangePasswordService.class);

    @Autowired
    public ChangePasswordService(UserRepositoryJPA uRepo, UserRepositoryJdbc uRepoJdbc, PasswordEncoder pEncoder){
        userRepositoryJPA = uRepo;
        userRepositoryJdbc = uRepoJdbc;
        passwordEncoder = pEncoder;
    }

    public Integer changePassword(String password, String newPassword1, String newPassword2){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User currUser = userRepositoryJPA.findByUsername(currentUserName);
            if(newPassword1.equals(newPassword2)){
                if(passwordEncoder.matches(password, currUser.getPassword())){
                    return userRepositoryJdbc.updatePassword(currUser.getId(), passwordEncoder.encode(newPassword1));
                }
                else{
                    LOG.debug("Curr password incorrect");
                }
            }
        }
        return 0;
    }
}
