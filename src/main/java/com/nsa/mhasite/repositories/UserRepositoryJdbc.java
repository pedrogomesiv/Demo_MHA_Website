package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJdbc implements UserRepository{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper;

    @Value("${sql.create.user}")
    String userInsertSQL;

    @Value("${sql.user.by.username}")
    String userByUsernameSQL;

    @Value("${sql.update.user.email}")
    String userEmailUpdateSQL;

    @Value("${sql.update.user.password}")
    String userPasswordUpdateSQL;

    @Value("${sql.create.user.all}")
    String userCreateAllSQL;

    @Value("select * from user where id = ?")
    String userByUserIdSQL;

    @Value("CALL createSystemAdministrator (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
    String createAdminSQL;

    @Value("CALL deleteUser(?)")
    String userDeleteSQL;

    @Autowired
    public UserRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        userMapper = (rs, i) -> new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password")
        );
    }

    @Override
    public Integer createUser(String username, String password){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(username);
        params.add(password);
        return jdbcTemplate.update(userInsertSQL, params.toArray());
    }

    @Override
    public Integer deleteUser(Long userID){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(userID);
        return jdbcTemplate.update(userDeleteSQL, params.toArray());
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.of(
                jdbcTemplate.queryForObject(
                        userByUsernameSQL,
                        new Object[]{username},
                        userMapper)
        );
    }

    @Override
    public Optional<User> findUserByUserId(Long userid){
        return Optional.of(jdbcTemplate.queryForObject(userByUserIdSQL,
                new Object[]{userid}, userMapper));
    }

    @Override
    public Integer updateEmail(Long id, String username){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(username);
        params.add(id);
        return jdbcTemplate.update(userEmailUpdateSQL, params.toArray());
    }

    @Override
    public Integer updatePassword(Long id, String password){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(password);
        params.add(id);
        return jdbcTemplate.update(userPasswordUpdateSQL, params.toArray());
    }

    @Override
    public Integer createUserAll(String username, String encoded_password, String first_name, String surname, String phone_number, String emergency_number, Boolean tenant, Boolean volunteer, String volunteer_experience, Boolean condition, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_Details, String address_line_1, String address_line_2, String City, String postCode){
        ArrayList<Object> params = new ArrayList<>();
        params.add(username);
        params.add(encoded_password);
        params.add(first_name);
        params.add(surname);
        params.add(phone_number);
        params.add(emergency_number);
        params.add(tenant);
        params.add(volunteer);
        params.add(volunteer_experience);
        params.add(condition);
        params.add(condition_details);
        params.add(medication);
        params.add(medication_details);
        params.add(allergies);
        params.add(allergy_Details);
        params.add(address_line_1);
        params.add(address_line_2);
        params.add(City);
        params.add(postCode);
        return jdbcTemplate.update(userCreateAllSQL, params.toArray());
    }

    @Override
    public Integer createAdministratorAccount(String username, String encoded_password, String first_name, String surname, String phone_number, String emergency_number, Boolean tenant, Boolean volunteer, String volunteer_experience, Boolean condition, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_Details, String address_line_1, String address_line_2, String City, String postCode) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(username);
        params.add(encoded_password);
        params.add(first_name);
        params.add(surname);
        params.add(phone_number);
        params.add(emergency_number);
        params.add(tenant);
        params.add(volunteer);
        params.add(volunteer_experience);
        params.add(condition);
        params.add(condition_details);
        params.add(medication);
        params.add(medication_details);
        params.add(allergies);
        params.add(allergy_Details);
        params.add(address_line_1);
        params.add(address_line_2);
        params.add(City);
        params.add(postCode);
        return jdbcTemplate.update(createAdminSQL, params.toArray());
    }
}
