package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.RegisterForm;
import com.nsa.mhasite.services.RegistrationFormCheck;
import com.nsa.mhasite.services.UserApplicationService;
import com.nsa.mhasite.services.UserRegistrationService;
import com.nsa.mhasite.services.VolunteerApplicantsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserApplicationController {
    static final Logger LOG = LoggerFactory.getLogger(UserApplicationController.class);

    @Autowired
    RegistrationFormCheck registrationFormCheck;

    @Autowired
    UserApplicationService userApplicationService;


    @RequestMapping(path = "/application", method = RequestMethod.GET)
    public String serveUserApplication(Model model)
    {
        return "user_application";
    }

    @RequestMapping(path = "/application", method = RequestMethod.POST)
    public String getUserApplication(RegisterForm form, Model model){
        LOG.debug("handling registration form");
        LOG.debug(form.toString());
        List<String> formResult = registrationFormCheck.checkForm(form);
        LOG.debug(formResult.toString());

        if(formResult.isEmpty()){
            String userRegResult = userApplicationService.registerApplicant(form);
            if(userRegResult.equals("")){
                LOG.debug("user registration failed");
            }
            else{
                LOG.debug("Applicatiton Succesful");
                return "login";
            }
        }
        model.addAttribute("form_info", formResult);
        return "user_application";

    }













}