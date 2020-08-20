package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.*;
import com.nsa.mhasite.repositories.AddressRepositoryJdbc;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.UserRepositoryJdbc;
import com.nsa.mhasite.repositories.VolunteerRepositoryJdbc;
import com.nsa.mhasite.services.ChangeEmailService;
import com.nsa.mhasite.services.RegistrationFormCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminUserChangeController {
    static final Logger LOG = LoggerFactory.getLogger(AdminUserChangeController.class);

    private UserRepositoryJPA userRepositoryJPA;
    private VolunteerRepositoryJdbc volunteerRepositoryJdbc;
    private AddressRepositoryJdbc addressRepositoryJdbc;
    private ChangeEmailService changeEmailService;
    private RegistrationFormCheck registrationFormCheck;
    private UserRepositoryJdbc userRepositoryJdbc;

    @Autowired
    public AdminUserChangeController(UserRepositoryJPA uRepo, VolunteerRepositoryJdbc vRepo, AddressRepositoryJdbc aRepo, ChangeEmailService cEmail, RegistrationFormCheck regCheck, UserRepositoryJdbc urRepo){
        userRepositoryJPA = uRepo;
        volunteerRepositoryJdbc = vRepo;
        addressRepositoryJdbc = aRepo;
        changeEmailService = cEmail;
        registrationFormCheck = regCheck;
        userRepositoryJdbc = urRepo;
    }

    private Model addAttributes(Long userid, Model model){
        Optional<VolunteerUser> volunteerUser = volunteerRepositoryJdbc.findVolunteerByUserId(userid);
        Address address = addressRepositoryJdbc.findByUserId(userid).get();
        VolunteerUser vUser = volunteerUser.get();
        User user = userRepositoryJPA.findById(userid).get();
        List<String> errors = new ArrayList<>();
        model.addAttribute("user_id", userid);
        model.addAttribute("user_email", user.getUsername());
        model.addAttribute("fName", vUser.getFirstName());
        model.addAttribute("sName", vUser.getLastName());
        model.addAttribute("address1", address.getAddress1());
        model.addAttribute("address2", address.getAddress2());
        model.addAttribute("city", address.getCity());
        model.addAttribute("postCode", address.getPostcode());
        model.addAttribute("phone", vUser.getPhone());
        model.addAttribute("emergencyPhone", vUser.getEmergency_number());
        model.addAttribute("tenant", vUser.getMha_tennant());
        model.addAttribute("volunteered", vUser.getPrevious_volunteer());
        model.addAttribute("volunteeredRole", vUser.getVolunteer_experience());
        model.addAttribute("disability", vUser.getConditions());
        model.addAttribute("disabilityDetails", vUser.getCondition_details());
        model.addAttribute("medication", vUser.getMedication());
        model.addAttribute("medicationDetails", vUser.getMedication_details());
        model.addAttribute("allergy", vUser.getAllergies());
        model.addAttribute("allergyDetails", vUser.getAllergy_details());
        model.addAttribute("resultemailgood", "");
        model.addAttribute("resultemailbad", "");
        model.addAttribute("resultdetailsgood", "");
        model.addAttribute("resultdetailsbad", "");
        model.addAttribute("form_info", errors);
        return model;
    }


    @RequestMapping(value = "details/{userid}", method = RequestMethod.GET)
    public String userDetailsChange(@PathVariable Long userid, Model model){
        Optional<VolunteerUser> volunteerUser = volunteerRepositoryJdbc.findVolunteerByUserId(userid);
        if(volunteerUser.isPresent()){
            model = addAttributes(userid, model);
            model.addAttribute("content", "admin_volunteer_details");
            return "adminMenu";
        }
        return "404";
    }

    @RequestMapping(path = "/details/{userid}/email", method = RequestMethod.POST)
    public String changeEmail(@PathVariable Long userid, EmailChangeForm emailChangeForm, Model model){
        LOG.debug("attempting to change email");
        LOG.debug(emailChangeForm.toString());
        if(emailChangeForm.getEmail().equals(emailChangeForm.getEmail2())){
            try{
                Integer rowsChanged = changeEmailService.changeEmail(userid, emailChangeForm.getEmail());
                if (rowsChanged == 1){
                    LOG.debug("email changed");
                    model = addAttributes(userid, model);
                    model.addAttribute("resultemailgood", "Email changed");
                    model.addAttribute("content", "admin_volunteer_details");
                    return "adminMenu";
                }
                else{
                    LOG.debug("Something went wrong changing email");
                    model = addAttributes(userid, model);
                    model.addAttribute("resultemailbad", "something went wrong");
                    model.addAttribute("content", "admin_volunteer_details");
                    return "adminMenu";
                }
            }catch(DataIntegrityViolationException e) {
                LOG.debug(e.toString());
                model = addAttributes(userid, model);
                model.addAttribute("resultemailbad", "email already registered");
                model.addAttribute("content", "admin_volunteer_details");
                return "adminMenu";
            }
        }
        else{
            LOG.debug("email address not the same");
            model = addAttributes(userid, model);
            model.addAttribute("resultemailbad", "email mismatch");
            model.addAttribute("content", "admin_volunteer_details");
            return "adminMenu";
        }
    }

    @RequestMapping(path = "/details/{userid}", method = RequestMethod.POST)
    public String changeDetails(@PathVariable Long userid, RegisterForm form, Model model){
        LOG.debug("attempting to change user details");
        LOG.debug(form.toString());
        List<String> errors = registrationFormCheck.checkForm(form);
        if (errors.isEmpty()){
            Integer rowsChanged = volunteerRepositoryJdbc.changeUserDetails(userid, form.getfName(), form.getsName(), form.getPhoneNumber(), form.getEmergencyNumber(), form.getTenant(), form.getVolunteered(), form.getVolunteeredRole(), form.getDisabilities(), form.getDisabilityDetails(), form.getMedications(), form.getMedicationDetails(), form.getAllergies(), form.getAllergyDetails());
            model = addAttributes(userid, model);
            if (rowsChanged == 1){
                rowsChanged = addressRepositoryJdbc.updateAddress(userid, form.getAddress1(), form.getAddress2(), form.getCity(), form.getPostCode());
                if (rowsChanged == 1){
                    model.addAttribute("resultdetailsgood", "Details changed successfully");
                    model.addAttribute("content", "admin_volunteer_details");
                    return "adminMenu";
                }
                else{
                    model = addAttributes(userid, model);
                    model.addAttribute("resultdetailsbad", "Address change failed");
                    model.addAttribute("content", "admin_volunteer_details");
                    return "adminMenu";
                }
            }
            else{
                model.addAttribute("resultdetailsbad", "Something went wrong");
                model.addAttribute("content", "admin_volunteer_details");
                return "adminMenu";
            }
        }
        else{
            model = addAttributes(userid, model);
            model.addAttribute("form_info", errors);
            model.addAttribute("content", "admin_volunteer_details");
            return "adminMenu";
        }
    }

    @RequestMapping(path= "/details/removal/{userid}", method = RequestMethod.GET)
    public String userDetailsRemoval(@PathVariable Long userid, Model model){
        Optional<VolunteerUser> volunteerUser = volunteerRepositoryJdbc.findVolunteerByUserId(userid);
        if(volunteerUser.isPresent()){
            model = addAttributes(userid, model);
            model.addAttribute("content", "userDeletionConfirm");
            return "adminMenu";
        }
        return "404";
    }


    @RequestMapping(path= "/details/removal/{userid}", method = RequestMethod.POST)
    public RedirectView deleteUser(@PathVariable Long userid){
        LOG.debug("Attempting to remove user");
        Integer report = userRepositoryJdbc.deleteUser(userid);
        return new RedirectView("/admin");

    }

    @RequestMapping(path="/details/removal/return", method=RequestMethod.GET)
    public RedirectView cancelDeletion(){
        LOG.debug("Cancelling deletion");
        return new RedirectView("/admin");
    }
}
