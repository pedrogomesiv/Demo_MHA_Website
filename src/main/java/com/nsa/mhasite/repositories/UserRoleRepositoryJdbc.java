package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRoleRepositoryJdbc implements UserRoleRepository{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<UserRole> userRoleMapper;

    @Value("${sql.create.user.role}")
    String userRoleInsertSQL;

    @Autowired
    public UserRoleRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        userRoleMapper = (rs, i) -> new UserRole(
                rs.getLong("id"),
                rs.getLong("userid"),
                rs.getString("role")
        );
    }

    @Override
    public Integer createUserRole(Long userid, String role){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(userid);
        params.add(role);
        return jdbcTemplate.update(userRoleInsertSQL, params.toArray());
    }
}
