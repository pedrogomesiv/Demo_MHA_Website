package com.nsa.mhasite.services;

import com.nsa.mhasite.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceStatic implements UserRoleService{
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceStatic(UserRoleRepository uRepo){
        userRoleRepository = uRepo;
    }

    @Override
    public Integer createUserRole(Long userid, String role){
        return userRoleRepository.createUserRole(userid, role);
    }
}
