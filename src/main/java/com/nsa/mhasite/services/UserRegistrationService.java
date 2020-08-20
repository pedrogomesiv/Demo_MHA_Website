package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.RegisterForm;
import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.email.EmailSessionBean;
import com.nsa.mhasite.repositories.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@NoArgsConstructor
public class UserRegistrationService {
    static final Logger LOG = LoggerFactory.getLogger(UserRegistrationService.class);

    private UserRepositoryJdbc userRepositoryJdbc;
    private PasswordEncoder passwordEncoder;
    private EmailSessionBean emailSessionBean;

    @Autowired
    public UserRegistrationService(UserRepositoryJdbc uRepo, PasswordEncoder pEncoder, EmailSessionBean eSession){
        this.userRepositoryJdbc = uRepo;
        this.passwordEncoder = pEncoder;
        this.emailSessionBean = eSession;
    }

    public String registerUser(RegisterForm registerForm)
    {
        Random rand = new Random();
        int n = rand.nextInt(999999) + 100000;
        String tempPassword = String.valueOf(n);
        int rowsChanged = userRepositoryJdbc.createUserAll(
                registerForm.getEmail(),
                passwordEncoder.encode(String.valueOf(n)),
                registerForm.getfName(),
                registerForm.getsName(),
                registerForm.getPhoneNumber(),
                registerForm.getEmergencyNumber(),
                registerForm.getTenant(),
                registerForm.getVolunteered(),
                registerForm.getVolunteeredRole(),
                registerForm.getDisabilities(),
                registerForm.getDisabilityDetails(),
                registerForm.getMedications(),
                registerForm.getMedicationDetails(),
                registerForm.getAllergies(),
                registerForm.getAllergyDetails(),
                registerForm.getAddress1(),
                registerForm.getAddress2(),
                registerForm.getCity(),
                registerForm.getPostCode());
        LOG.debug(String.valueOf(rowsChanged) + " rows changed registering new user");
        if (rowsChanged == 5){
            emailSessionBean.welcomeEmail(registerForm.getEmail(), tempPassword, registerForm.getfName());
            return tempPassword;
        }
        return "";
    }

    public String createAdministratorAccount(RegisterForm cAdminForm)
    {
        Random rand = new Random();
        int n = rand.nextInt(999999) + 100000;
        String tempPassword = String.valueOf(n);
        int rowsChanged = userRepositoryJdbc.createAdministratorAccount(
                cAdminForm.getEmail(),
                passwordEncoder.encode(String.valueOf(n)),
                cAdminForm.getfName(),
                cAdminForm.getsName(),
                cAdminForm.getPhoneNumber(),
                cAdminForm.getEmergencyNumber(),
                cAdminForm.getTenant(),
                cAdminForm.getVolunteered(),
                cAdminForm.getVolunteeredRole(),
                cAdminForm.getDisabilities(),
                cAdminForm.getDisabilityDetails(),
                cAdminForm.getMedications(),
                cAdminForm.getMedicationDetails(),
                cAdminForm.getAllergies(),
                cAdminForm.getAllergyDetails(),
                cAdminForm.getAddress1(),
                cAdminForm.getAddress2(),
                cAdminForm.getCity(),
                cAdminForm.getPostCode());
        LOG.debug(String.valueOf(rowsChanged) + " rows changed creating new Administrator.");
        if (rowsChanged == 5){
            emailSessionBean.welcomeEmail(cAdminForm.getEmail(), tempPassword, cAdminForm.getfName());
            return tempPassword;
        }
        return "";
    }

}
