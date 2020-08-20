package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.RegisterForm;
import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class RegistrationFormCheck {

    private UserRepositoryJPA userRepositoryJPA;

    @Autowired
    public RegistrationFormCheck(UserRepositoryJPA uRepo) {
        this.userRepositoryJPA = uRepo;
    }

    public List<String> checkForm(RegisterForm registerForm)
    {
        List<String> returnMessage = new ArrayList<>();
        if(registerForm.getfName().isEmpty())
        {
            returnMessage.add("No first name given");
        }
        if(registerForm.getsName().isEmpty())
        {
            returnMessage.add("No last name given");
        }
        if(registerForm.getAddress1().isEmpty())
        {
            returnMessage.add("No first address line given");
        }
        if(registerForm.getCity().isEmpty())
        {
            returnMessage.add("No town/city given");
        }
        if(registerForm.getPostCode().isEmpty())
        {
            returnMessage.add("No post code given");
        }
        if(registerForm.getPostCode().length()>10)
        {
            returnMessage.add("Post code too long");
        }
        if(registerForm.getPhoneNumber().length()>13)
        {
            returnMessage.add("Phone Number too long");
        }
        if(registerForm.getEmergencyNumber().length()>13)
        {
            returnMessage.add("Emergency Phone Number too long");
        }
        if(registerForm.getPhoneNumber().isEmpty())
        {
            returnMessage.add("No phone number given");
        }
        if(registerForm.getEmail().isEmpty())
        {
            returnMessage.add("No email address given");
        }
        else{
            User user = userRepositoryJPA.findByUsername(registerForm.getEmail());
            if(user!=null){
                returnMessage.add("Email already in use");
            }
        }
        if(registerForm.getTenant()==null){
            returnMessage.add("Select tenant status");
        }
        if(registerForm.getVolunteered()!=null)
        {
            if(registerForm.getVolunteered()){
                if(registerForm.getVolunteeredRole().isEmpty())
                {
                    returnMessage.add("Please enter previous volunteering experience");
                }
            }

        }
        else{
            returnMessage.add("Select if volunteered");
        }
        if(registerForm.getDisabilities()!=null)
        {
            if(registerForm.getDisabilities()) {
                if (registerForm.getDisabilityDetails().isEmpty()) {
                    returnMessage.add("Please enter disability information");
                }
            }
        }
        else{
            returnMessage.add("Select if any disabilities");
        }
        if(registerForm.getMedications()!=null)
        {
            if(registerForm.getMedications()) {
                if (registerForm.getMedicationDetails().isEmpty()) {
                    returnMessage.add("Please enter medication information");
                }
            }
        }
        else{
            returnMessage.add("Select if requiring medication");
        }
        if(registerForm.getAllergies()!=null)
        {
            if(registerForm.getAllergies()) {
                if (registerForm.getAllergyDetails().isEmpty()) {
                    returnMessage.add("Please enter allergy information");
                }
            }
        }
        else{
            returnMessage.add("Select if any allergies");
        }
        if(registerForm.getCheckedStatement() == null){
            returnMessage.add("Please accept the terms and conditions");
        }
        return returnMessage;
    }
}
