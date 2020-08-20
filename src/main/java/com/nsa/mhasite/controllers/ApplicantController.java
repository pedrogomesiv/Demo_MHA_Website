package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.RegisterForm;
import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.domain.VolunteerApplicant;
import com.nsa.mhasite.domain.VolunteerUser;
import com.nsa.mhasite.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {

    static final Logger LOG = LoggerFactory.getLogger(ApplicantController.class);
    private VolunteerService vService;
    private VolunteerApplicantsService vAppsService;
    private UserRegistrationService uRegService;
    private UserService uService;
    private UserRoleService uRoleService;

    @Autowired
    public ApplicantController(VolunteerService vService, UserRegistrationService uRegService, VolunteerApplicantsService vAppsService, UserService uService, UserRoleService uRoleService) {
        this.vService = vService;
        this.uRegService = uRegService;
        this.vAppsService = vAppsService;
        this.uService = uService;
        this.uRoleService = uRoleService;
    }

    @RequestMapping(path = "/accept/{id}", method = RequestMethod.GET)
    public String acceptApplicantMakeNewVolunteerUser(@PathVariable Long id, Model model) {
        LOG.debug("Handling /applicant/accept");
        LOG.debug(id.toString());

        VolunteerApplicant vol = this.vAppsService.findByVolunteerAppId(id);
        LOG.debug(vol.toString());

        String mhaT = setBasedOnIfTrueOrFalse(vol.getMha_tennant());
        String prevV = setBasedOnIfTrueOrFalse(vol.getPrevious_volunteer());
        String conds = setBasedOnIfTrueOrFalse(vol.getConditions());
        String meds = setBasedOnIfTrueOrFalse(vol.getMedication());
        String allers = setBasedOnIfTrueOrFalse(vol.getAllergies());
        Date todaysDate = new Date();

        RegisterForm newVolunteer = new RegisterForm(todaysDate, vol.getFirstName(), vol.getLastName(), vol.getAddress_line_one(), vol.getAddress_line_two(),
                vol.getCity(), vol.getPostcode(), vol.getPhone(), vol.getEmergency_number(), vol.getUsername(), mhaT, prevV,
                vol.getVolunteer_experience(), conds, vol.getCondition_details(), meds, vol.getMedication_details(),
                allers,  vol.getAllergy_details());

        String userRegResult = uRegService.registerUser(newVolunteer);
        if(userRegResult.isEmpty()){
            LOG.debug("user registration failed");
        }
        else{
            LOG.debug("user registration success, temp password is: "+userRegResult);
        }

        //Finally, remove applicant's details.
        Integer resultOfDeletion = this.vAppsService.deleteByVolunteerID(id);
        LOG.debug("Rows affected by deletion: " + resultOfDeletion);

        model.addAttribute("volunteer", vol);
        model.addAttribute("content", "volunteerCreatedSuccessfully");
        return "adminMenu";
    }


    public String setBasedOnIfTrueOrFalse(Boolean item) {
        if (item == true) {
            return "yes";
        } else {
            return "false";
        }
    }

    @RequestMapping(path = "/decline/{id}", method = RequestMethod.GET)
    public String declineApplicant(@PathVariable Long id, Model model) {
        LOG.debug("Handling /applicant/decline");
        LOG.debug(id.toString());

        VolunteerApplicant vol = this.vAppsService.findByVolunteerAppId(id);
        LOG.debug(vol.toString());

        LOG.debug("Removing applicant.");
        Integer resultOfDeletion = this.vAppsService.deleteByVolunteerID(id);
        LOG.debug("Rows affected by deletion: " + resultOfDeletion);
        model.addAttribute("content", "adminApplicants");
        return "adminMenu";
    }

}
