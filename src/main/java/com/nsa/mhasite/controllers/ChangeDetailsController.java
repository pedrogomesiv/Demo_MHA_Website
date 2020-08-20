package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.*;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.VolunteerRepositoryJdbc;
import com.nsa.mhasite.services.ChangeEmailService;
import com.nsa.mhasite.services.ChangePasswordService;
import com.nsa.mhasite.services.ChangePhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ChangeDetailsController {
    static final Logger LOG = LoggerFactory.getLogger(ChangeDetailsController.class);

    private ChangeEmailService changeEmailService;
    private ChangePasswordService changePasswordService;
    private ChangePhoneService changePhoneService;
    private VolunteerRepositoryJdbc volunteerRepositoryJdbc;
    private UserRepositoryJPA userRepositoryJPA;

    @Autowired
    public ChangeDetailsController(ChangeEmailService changeES, ChangePasswordService changePS, ChangePhoneService changePhS, VolunteerRepositoryJdbc vRepo, UserRepositoryJPA uRepo){
        changeEmailService = changeES;
        changePasswordService = changePS;
        changePhoneService = changePhS;
        volunteerRepositoryJdbc = vRepo;
        userRepositoryJPA = uRepo;
    }

    private Model addAttributes(Model model){
        model.addAttribute("result1good", "");
        model.addAttribute("result1bad", "");
        model.addAttribute("result2good", "");
        model.addAttribute("result2bad", "");
        model.addAttribute("result3good", "");
        model.addAttribute("result3bad", "");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<VolunteerUser> volunteerUser = volunteerRepositoryJdbc.findVolunteerByUsername(authentication.getName());
        model.addAttribute("user_email", authentication.getName());
        model.addAttribute("user_phone", volunteerUser.get().getPhone());
        model.addAttribute("user_emergency_phone", volunteerUser.get().getEmergency_number());
        return model;
    }

    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    private String returnDetailsPage(Model model){
        model.addAttribute("content", "change_details");
        if(hasRole("ROLE_ADMIN")){
            return "adminMenu";
        }
        else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            VolunteerUser volunteerUser = volunteerRepositoryJdbc.findVolunteerByUsername(currentPrincipalName).get();
            model.addAttribute("user", volunteerUser);
            return "userMenu";
        }
    }

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public String serveDetailsChange(Model model){
        model = addAttributes(model);
        return returnDetailsPage(model);
    }

    @RequestMapping(path = "/email_update", method = RequestMethod.POST)
    public String changeEmail(EmailChangeForm emailChangeForm, Model model){
        LOG.debug("attempting to change email");
        LOG.debug(emailChangeForm.toString());
        model = addAttributes(model);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                Long userid = userRepositoryJPA.findByUsername(currentUserName).getId();

            if(emailChangeForm.getEmail().equals(emailChangeForm.getEmail2())){
                    try{
                    Integer rowsChanged = changeEmailService.changeEmail(userid, emailChangeForm.getEmail());
                    if (rowsChanged == 1){
                        model.addAttribute("result1good", "Email changed");
                        return returnDetailsPage(model);
                    }
                    else{
                        model.addAttribute("result1bad", "Something went wrong changing email");
                        return returnDetailsPage(model);
                    }
                }catch(DataIntegrityViolationException e) {
                    LOG.debug(e.toString());
                    model.addAttribute("result1bad", "That email is already registered");
                        return returnDetailsPage(model);
                }
            }
            else{
                model.addAttribute("result1bad", "the two addresses given do not match");
                return returnDetailsPage(model);
            }
        }
        return "404";
    }

    @RequestMapping(path = "/password_update", method = RequestMethod.POST)
    public String changePassword(NewPasswordForm newPasswordForm, Model model){
        LOG.debug("attempting to change password");
        LOG.debug(newPasswordForm.toString());

        Integer rowsChanged = changePasswordService.changePassword(
                newPasswordForm.getPassword(),
                newPasswordForm.getNew_password1(),
                newPasswordForm.getNew_password2());
        model = addAttributes(model);
        if (rowsChanged == 1){

            model.addAttribute("result2good", "Password changed");
        }
        else{
            model.addAttribute("result2bad","Something went wrong");
        }
        return returnDetailsPage(model);
    }

    @RequestMapping(path="/phone_update", method = RequestMethod.POST)
    public String changeNumber(NewPhoneForm newPhoneForm, Model model){
        LOG.debug("attempting to change phone numbers");
        LOG.debug(newPhoneForm.toString());
        Integer rowsChanged = changePhoneService.changePhones(newPhoneForm.getPhone(), newPhoneForm.getEmergencyPhone());
        model = addAttributes(model);
        if (rowsChanged == 2){
            model.addAttribute("result3good", "Phone number changed");
        }
        else{
            model.addAttribute("result3bad", "Something went wrong");
        }
        return returnDetailsPage(model);
    }
}
