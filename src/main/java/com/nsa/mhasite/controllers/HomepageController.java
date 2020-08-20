package com.nsa.mhasite.controllers;

import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.UserRolesRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomepageController {
    static final Logger LOG = LoggerFactory.getLogger(HomepageController.class);
    private UserRolesRepositoryJPA uRepo;

    @Autowired
    public HomepageController(UserRolesRepositoryJPA uRepo) {
        this.uRepo = uRepo;
    }

    @RequestMapping("")
    public String servePortal(Model model) {
        LOG.debug("Handling empty route after login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            LOG.debug("User authenticated");
            String currentUserName = authentication.getName();
            LOG.debug(currentUserName);
            List<String> userRoles = uRepo.findRoleByUsername(currentUserName);
            model.addAttribute("roles", userRoles);
            LOG.debug("Serving portal page");
            return "portal";
        } else {
            return "login";
        }
    }


}
//
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//                String currentUserName = authentication.getName();
//                LOG.debug(currentUserName);
//                Optional<VolunteerUser> userFromSearch = this.vService.findVolunteerByUsername(currentUserName);
//
//        if (userFromSearch.isPresent()) {
//        LOG.debug("User is present: " + userFromSearch.toString());
//        model.addAttribute("user", userFromSearch.get());
//        return "userProfile";
//        }
//        }
