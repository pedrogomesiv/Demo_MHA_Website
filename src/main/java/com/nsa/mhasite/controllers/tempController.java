package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.VolunteerUser;
import com.nsa.mhasite.email.EmailSessionBean;
import com.nsa.mhasite.repositories.UserRepositoryJdbc;
import com.nsa.mhasite.repositories.VolunteerAchievementRepository;
import com.nsa.mhasite.repositories.VolunteerRepositoryJdbc;
import com.nsa.mhasite.services.RedeemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class tempController {
    private UserRepositoryJdbc userRepositoryJdbc;
    private VolunteerRepositoryJdbc volunteerRepositoryJdbc;
    static final Logger LOG = LoggerFactory.getLogger(tempController.class);

    @Autowired
    public tempController(UserRepositoryJdbc uRepo, VolunteerRepositoryJdbc vRepo){
        userRepositoryJdbc = uRepo;
        volunteerRepositoryJdbc = vRepo;
    }

    @RequestMapping(value={"profile/temp"}, method = RequestMethod.GET)
    public String returnLoginPage(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        VolunteerUser volunteer = volunteerRepositoryJdbc.findVolunteerByUserId(userRepositoryJdbc.findUserByUsername(currentPrincipalName).get().getId()).get();
        model.addAttribute("user", volunteer);
        model.addAttribute("content", "404");
        return "userMenu";
    }
}
