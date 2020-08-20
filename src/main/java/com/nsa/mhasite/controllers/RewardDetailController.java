package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.*;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.VolunteerRepositoryJdbc;
import com.nsa.mhasite.services.GiftService;
import com.nsa.mhasite.services.OffersService;
import com.nsa.mhasite.services.RedeemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.MissingResourceException;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class RewardDetailController {

    static final Logger LOG = LoggerFactory.getLogger(RewardDetailController.class);
    private OffersService offersService;
    private RedeemService redeemService;
    private GiftService giftService;
    private VolunteerRepositoryJdbc volunteerRepositoryJdbc;

    @Autowired
    public RewardDetailController(OffersService oService, RedeemService rService, GiftService gService, VolunteerRepositoryJdbc vRepo) {
        offersService = oService;
        redeemService = rService;
        giftService = gService;
        volunteerRepositoryJdbc = vRepo;
    }

    private VolunteerUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return volunteerRepositoryJdbc.findVolunteerByUsername(currentPrincipalName).get();
    }
    @RequestMapping(path = "/offers/{id}", method = RequestMethod.GET)
    public String getCharityProfile(@PathVariable Long id, Model model) {

        Offer offer = offersService.findById(id);
        if (offer != null) {
            model.addAttribute("offer", offer);
            model.addAttribute("content", "rewardDetail");
            model.addAttribute("user", getUser());
            return "userMenu";
        } else {
            return "404";
        }

    }

    @RequestMapping(path = "/reward_redeem/{id}", method = RequestMethod.POST)
    public String redeemReward(@PathVariable Long id, Model model){
        String report = redeemService.redeemReward(id);
        System.out.println(report);
        Offer offer = offersService.findById(id);
        if (offer != null) {
            model.addAttribute("offer", offer);
        } else {
            return "404";
        }
        if (report.equals("Success")){
            Integer result = this.offersService.increaseNumTimesRedeemed(offer.getId());
            LOG.debug("Result of increasing num times redeemed: " + result);
            model.addAttribute("success", "Successfully redeemed!");
            model.addAttribute("content", "rewardDetail");
            model.addAttribute("user", getUser());
            return "userMenu";
        }
        else{
            model.addAttribute("success", report);
            model.addAttribute("content", "rewardDetail");
            model.addAttribute("user", getUser());
            return "userMenu";
        }
    }

    @RequestMapping(path = "/gift/{id}", method = RequestMethod.GET)
    public String giftRewardForm(@PathVariable Long id, Model model){
        Offer offer = offersService.findById(id);
        if (offer != null) {
            model.addAttribute("id", id);
            model.addAttribute("content", "gifting");
            model.addAttribute("user", getUser());
            return "userMenu";
        }
        else{
            return "404";
        }
    }

    @RequestMapping(path = "/gift/{id}", method = RequestMethod.POST)
    public String giftReward(@PathVariable Long id, GiftForm form, Model model){
        Offer offer = offersService.findById(id);
        if (offer != null) {
            model.addAttribute("id", id);
            model.addAttribute("success", giftService.giftReward(form, id));
            model.addAttribute("content", "gifting");
            model.addAttribute("user", getUser());
            return "userMenu";
        }
        else{
            return "404";
        }
    }
}