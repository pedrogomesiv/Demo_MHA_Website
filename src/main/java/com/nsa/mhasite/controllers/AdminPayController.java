package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.Achievement;
import com.nsa.mhasite.domain.CreditPayForm;
import com.nsa.mhasite.repositories.AchievementRepository;
import com.nsa.mhasite.repositories.AllocationRepositoryJdbc;
import com.nsa.mhasite.repositories.UserRepositoryJPA;
import com.nsa.mhasite.repositories.VolunteerRepositoryJdbc;
import com.nsa.mhasite.services.AchievementMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPayController {
    static final Logger LOG = LoggerFactory.getLogger(AdminPayController.class);

    private VolunteerRepositoryJdbc volunteerRepositoryJdbc;
    private AllocationRepositoryJdbc allocationRepositoryJdbc;
    private AchievementMonitorService achievementMonitorService;

    @Autowired
    public AdminPayController(VolunteerRepositoryJdbc vRepo, AllocationRepositoryJdbc aRepo, AchievementMonitorService aService){
        volunteerRepositoryJdbc = vRepo;
        allocationRepositoryJdbc = aRepo;
        achievementMonitorService = aService;
    }

    private Model addAttributes(Long userid, Model model){
        model.addAttribute("resultpaybad", "");
        model.addAttribute("resultpaygood", "");
        model.addAttribute("user_id", userid);
        return model;
    }

    @RequestMapping(value = "pay/{userid}", method = RequestMethod.GET)
    public String userPayPage(@PathVariable Long userid, Model model){
        model = addAttributes(userid, model);
        model.addAttribute("content", "pay_credits");
        return "adminMenu";
    }

    @RequestMapping(value = "pay/{userid}", method = RequestMethod.POST)
    public String userPayChange(@PathVariable Long userid, CreditPayForm creditPayForm, Model model){
        model = addAttributes(userid, model);
        if (creditPayForm.getGt().equals("give")){
            Integer rowschanged = volunteerRepositoryJdbc.giveCredits(userid, creditPayForm.getAmount());
            rowschanged += allocationRepositoryJdbc.createRecord(new Date(), creditPayForm.getAmount(), creditPayForm.getReason(), userid);
            if(rowschanged == 2){
                achievementMonitorService.processAchievements(userid);
                model.addAttribute("resultpaygood", "Credits given");
                model.addAttribute("content", "pay_credits");
                return "adminMenu";
            }
            else{
                model.addAttribute("resultpaybad", "Something went wrong");
                model.addAttribute("content", "pay_credits");
                return "adminMenu";
            }
        }
        else{
            Integer rowschanged = volunteerRepositoryJdbc.takeCredits(userid, creditPayForm.getAmount());
            if(rowschanged == 1){
                model.addAttribute("resultpaygood", "Credits removed");
                model.addAttribute("content", "pay_credits");
                return "adminMenu";
            }
            else{
                model.addAttribute("resultpaybad", "Something went wrong");
                model.addAttribute("content", "pay_credits");
                return "adminMenu";
            }
        }
    }
}
