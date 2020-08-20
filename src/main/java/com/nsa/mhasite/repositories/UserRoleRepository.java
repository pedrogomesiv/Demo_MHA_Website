package com.nsa.mhasite.repositories;

import org.springframework.stereotype.Component;

@Component
public interface UserRoleRepository {
    public Integer createUserRole(Long userid, String role);
}
