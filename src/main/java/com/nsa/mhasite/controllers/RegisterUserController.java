package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.RegisterForm;
import com.nsa.mhasite.services.RegistrationFormCheck;
import com.nsa.mhasite.services.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class RegisterUserController {
    static final Logger LOG = LoggerFactory.getLogger(RegisterUserController.class);

    @Autowired
    RegistrationFormCheck registrationFormCheck;

    @Autowired
    UserRegistrationService userRegistrationService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String serveUserForm(Model model)
    {
        model.addAttribute("content", "user_registration");
        return "adminMenu";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(RegisterForm registerForm, Model model){
        LOG.debug("handling user registration form");
        LOG.debug(registerForm.toString());
        LOG.debug(registerForm.getCheckedStatement().toString());

//        if (!registerForm.getCheckedStatement().equals("checked")) {
//            LOG.debug("User did not check statement box - cannot register them");
//            return new ModelAndView("failedRegistration");
//        }

        List<String> formResult = registrationFormCheck.checkForm(registerForm);

        if(formResult.isEmpty()){
            String userRegResult = userRegistrationService.registerUser(registerForm);
            if(userRegResult.isEmpty()){
                LOG.debug("user registration failed");
            }
            else{
                LOG.debug("user registration success, temp password is: "+userRegResult);
            }
            return new ModelAndView(new RedirectView("/admin"));
        }
        model.addAttribute("form_info", formResult);
        model.addAttribute("content", "user_registration");
        return new ModelAndView("adminMenu");
    }
}
