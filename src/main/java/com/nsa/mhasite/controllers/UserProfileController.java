package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.*;
import com.nsa.mhasite.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"user"})
@Controller
public class UserProfileController {

    static final Logger LOG = LoggerFactory.getLogger(UserProfileController.class);
    private VolunteerService vService;
    private RedemptionService rService;
    private OffersService oService;
    private AchievementDetailsService aService;
    private BusinessService bService;

    @Autowired
    public UserProfileController(OffersService oService, RedemptionService rService, VolunteerService vService, AchievementDetailsService aService, BusinessService bService) {
        this.vService = vService;
        this.rService = rService;
        this.oService = oService;
        this.aService = aService;
        this.bService = bService;
    }

    private Model addAttributes(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<VolunteerUser> userFromSearch = this.vService.findVolunteerByUsername(currentPrincipalName);
        model.addAttribute("user", userFromSearch.get());
        return model;
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String findVolunteerByUsername(Model model) {
        LOG.debug("Handling user profile");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            LOG.debug(currentUserName);
            Optional<VolunteerUser> userFromSearch = this.vService.findVolunteerByUsername(currentUserName);

            if (userFromSearch.isPresent()) {
                LOG.debug("User is present: " + userFromSearch.toString());
                model.addAttribute("user", userFromSearch.get());
                model.addAttribute("achievements", aService.getAchievements(userFromSearch.get().getUserid()));
                model.addAttribute("content", "userProfile");
                return "userMenu";
            }
        }
        return "404";
    }

    @RequestMapping(path = "/profile/credithistory", method = RequestMethod.GET)
    private String serveCreditHistoryForUser(@SessionAttribute("user") VolunteerUser user, Model model){
        LOG.debug("Handling /profile/credithistory");

        //Mock id to test logging.
        List<Redemption> usersCreditHistory = this.rService.findByUser_id(user.getUserid());
        LOG.debug(usersCreditHistory.toString());

        //Gather reward name via reward id given in redemption object

        List<RedeemReward> redeemRewards = new ArrayList<>();
        for (Redemption r : usersCreditHistory) {
            try{
            redeemRewards.add(new RedeemReward(r, this.oService.findById(r.getReward_id())));}catch (Exception e){
                LOG.debug(e.toString());
            }
        }
        model = addAttributes(model);
        model.addAttribute("redeemed", redeemRewards);
        model.addAttribute("content", "creditHistory");
        return "userMenu";
    }

    @RequestMapping(value = {"/profile/offers"}, method = RequestMethod.GET)
    public String viewOffers(Model model) {
        LOG.debug("handling offers");
        List<Offer> listOfRewards = oService.findAll();
        LOG.debug(listOfRewards.toString());
        List<RewardAndOffer> rewardAndOffers = new ArrayList<>();
        for (Offer offer : listOfRewards) {
            try{
            rewardAndOffers.add(new RewardAndOffer(this.bService.findBusinessNameById(offer.getBusinessID()), offer));
            }catch (Exception e){
                LOG.debug(e.toString());
            }
        }
        model = addAttributes(model);
        model.addAttribute("Offers", rewardAndOffers);
        model.addAttribute("content", "offers");
        return "userMenu";
    }
}
