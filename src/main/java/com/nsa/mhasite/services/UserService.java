package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.User;

import java.util.Optional;

public interface UserService {
    public Integer createUser(String username, String password);
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserByUserId(Long userid);
    public Integer updateEmail(Long id, String username);
    public Integer updatePassword(Long id, String password);
    public Integer createUserAll(String username, String encoded_password, String first_name, String surname, String phone_number, String emergency_number, Boolean tenant, Boolean volunteer, String volunteer_experience, Boolean condition, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_Details, String address_line_1, String address_line_2, String City, String postCode);
    public Integer createAdministratorAccount(String username, String encoded_password, String first_name, String surname, String phone_number, String emergency_number, Boolean tenant, Boolean volunteer, String volunteer_experience, Boolean condition, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_Details, String address_line_1, String address_line_2, String City, String postCode);
    public Integer deleteUser(Long userID);
}
