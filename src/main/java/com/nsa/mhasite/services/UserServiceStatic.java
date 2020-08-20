package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceStatic implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceStatic(UserRepository uRepo){
        userRepository = uRepo;
    }

    @Override
    public Integer createUser(String username, String password) {
        return userRepository.createUser(username, password);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findUserByUserId(Long userid){
        return this.userRepository.findUserByUserId(userid);
    }

    @Override
    public Integer updateEmail(Long id, String username){
        return userRepository.updateEmail(id, username);
    }

    @Override
    public Integer updatePassword(Long id, String password){
        return userRepository.updatePassword(id, password);
    }

    @Override
    public Integer createUserAll(String username, String encoded_password, String first_name, String surname, String phone_number, String emergency_number, Boolean tenant, Boolean volunteer, String volunteer_experience, Boolean condition, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_Details, String address_line_1, String address_line_2, String City, String postCode){
        return userRepository.createUserAll(username, encoded_password, first_name, surname, phone_number, emergency_number, tenant, volunteer, volunteer_experience, condition, condition_details, medication, medication_details, allergies, allergy_Details, address_line_1, address_line_2, City, postCode);
    }

    @Override
    public Integer createAdministratorAccount(String username, String encoded_password, String first_name, String surname, String phone_number, String emergency_number, Boolean tenant, Boolean volunteer, String volunteer_experience, Boolean condition, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_Details, String address_line_1, String address_line_2, String City, String postCode) {
        return userRepository.createAdministratorAccount(username, encoded_password, first_name, surname, phone_number, emergency_number, tenant, volunteer, volunteer_experience, condition, condition_details, medication, medication_details, allergies, allergy_Details, address_line_1, address_line_2, City, postCode);
    }

    @Override
    public Integer deleteUser(Long userID){
        return userRepository.deleteUser(userID);
    }
}
