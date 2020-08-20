package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.RegisterForm;
import com.nsa.mhasite.domain.User;

import com.nsa.mhasite.repositories.ApplicationRepository;
import com.nsa.mhasite.repositories.ApplicationRepositoryJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class UserApplicationService {
    private ApplicationRepositoryJdbc applicationRepositoryJdbc;
    private ApplicationRepository applicationRepository;

    @Autowired
    public UserApplicationService(ApplicationRepositoryJdbc aRepoJDBC, ApplicationRepository aRepo){
        this.applicationRepositoryJdbc = aRepoJDBC;
        this.applicationRepository = aRepo;

    }

    public String registerApplicant(RegisterForm registerForm)
    {
        Integer rowsChanged = applicationRepositoryJdbc.createVolunteerApplicant(
                registerForm.getfName(),
                registerForm.getsName(),
                registerForm.getEmail(),
                registerForm.getPhoneNumber(),
                registerForm.getEmergencyNumber(),
                registerForm.getAddress1(),
                registerForm.getAddress2(),
                registerForm.getCity(),
                registerForm.getPostCode(),
                registerForm.getTenant(),
                registerForm.getVolunteered(),
                registerForm.getVolunteeredRole(),
                registerForm.getDisabilities(),
                registerForm.getDisabilityDetails(),
                registerForm.getMedications(),
                registerForm.getMedicationDetails(),
                registerForm.getAllergies(),
                registerForm.getAllergyDetails());

        if (rowsChanged == 0){
            return "";
        }
        else{
            return "success";
        }

    }
}
