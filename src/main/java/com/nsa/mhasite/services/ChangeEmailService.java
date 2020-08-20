package com.nsa.mhasite.services;

import com.nsa.mhasite.repositories.UserRepositoryJdbc;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ChangeEmailService {
    private UserRepositoryJdbc userRepositoryJdbc;

    @Autowired
    public ChangeEmailService(UserRepositoryJdbc uRepo){
        userRepositoryJdbc = uRepo;
    }

    public Integer changeEmail(Long userid, String email){

        return userRepositoryJdbc.updateEmail(userid, email);

    }
}
