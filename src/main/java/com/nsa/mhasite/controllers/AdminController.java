package com.nsa.mhasite.controllers;

import com.nsa.mhasite.domain.*;
import com.nsa.mhasite.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"result"})
@Controller
@RequestMapping("/admin")
public class AdminController {

    static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    private VolunteerService vService;
    private VolunteerApplicantsService vAppsService;
    private BusinessService bService;
    private OffersService oService;
    private BusinessServiceJdbc bServiceJdbc;
    private List<String> selectOptions;
    private RegistrationFormCheck rFormCheck;
    private UserService uService;
    private UserRegistrationService uRegService;

    @Autowired
    public AdminController(UserRegistrationService uRegService, UserService uService, RegistrationFormCheck rFormCheck, VolunteerService vService, OffersService oService, BusinessService bService, VolunteerApplicantsService vAppsService, BusinessServiceJdbc bServiceJ) {
        this.vService = vService;
        this.vAppsService = vAppsService;
        this.bService = bService;
        this.oService = oService;
        this.bServiceJdbc = bServiceJ;
        this.rFormCheck = rFormCheck;
        this.uService = uService;
        this.uRegService = uRegService;

        this.selectOptions = new ArrayList<>();
    }

    private Model addAttributes(Model model) {
        List<Business> businesses = bService.findAll();
        model.addAttribute("attribute1", "");
        model.addAttribute("attribute2", "");
        model.addAttribute("attribute3", "");
        model.addAttribute("attribute4", "");
        model.addAttribute("attribute5", "");
        model.addAttribute("attribute6", "");
        model.addAttribute("businesses", businesses);
        return model;
    }

    @RequestMapping("")
    public String serveAdminHomePage(Model model) {
        LOG.debug("Handling /admin serving homepage");
        model.addAttribute("content", "adminReportPage");
        return "adminMenu";
    }

    @RequestMapping(path = "/findVolunteer", method = RequestMethod.GET)
    public String findVolunteerByUsername(VolunteerSearchForm vForm, Model model) {
        LOG.debug("Handling /findVolunteer");
        LOG.debug(vForm.toString());

        List<VolunteerUser> usersFromSearch = this.vService.findVolunteerBySearch(vForm.getSearchTerm());
        LOG.debug("Users: " + usersFromSearch.toString());
        model.addAttribute("result", usersFromSearch);
        model.addAttribute("content", "volunteerSearchResults");
        return "adminMenu";
    }

    @RequestMapping(value = "/createAdmins", method = RequestMethod.GET)
    public String serveCreateAdminsPage(Model model) {
        LOG.debug("Handling /admin/createAdmins");
        model.addAttribute("content", "adminCreateAdmin");
        return "adminMenu";
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
    public RedirectView createNewAdministratorAccount(RegisterForm cAdminForm) {
        LOG.debug("Handling /admin/createAdmin");
        LOG.debug(cAdminForm.toString());
        cAdminForm.setCheckedStatement("checked");

        List<String> formResult = this.rFormCheck.checkForm(cAdminForm);
        if (formResult.isEmpty()) {
            //Passed validation.

            String adminCreationResult = this.uRegService.createAdministratorAccount(cAdminForm);
            if (adminCreationResult.isEmpty()) {
                LOG.debug("Admin account creation failed.");
            } else {
                LOG.debug("Admin account successfully made.");
            }
            return new RedirectView("/admin");
        } else {
            return new RedirectView("/admin/createAdmins");
        }
    }

    @RequestMapping("/rewards")
    public String serveAdminCreateRewardsPage(Model model) {
        LOG.debug("Handling /admin/rewards");
        List<Business> businesses = bService.findAll();
        model.addAttribute("businesses", businesses);
        model.addAttribute("content", "adminCreateRewards");
        return "adminMenu";
    }

    @RequestMapping(path = "/searchForRewards", method = RequestMethod.GET)
    private String serveSearchRewardPage(Model model) {
        model.addAttribute("content", "adminSearchRewards");
        return "adminMenu";
    }

    @RequestMapping(path = "/searchReward", method = RequestMethod.GET)
    private String searchForReward(Model model, OfferSearchForm oForm) {
        LOG.debug("Handling /searchReward POST form");
        LOG.debug(oForm.toString());

        List<Offer> matchingOffers = this.oService.findRewardsBySearch(oForm.getSearchNameTerm());
        LOG.debug(matchingOffers.toString());
        model.addAttribute("offers", matchingOffers);

        List<String> businesses = new ArrayList<String>();
        for (Offer o : matchingOffers) {
            businesses.add(this.bService.findBusinessNameById(o.getBusinessID()));
        }
        model.addAttribute("bness", businesses);
        model.addAttribute("content", "rewardSearchResults");
        return "adminMenu";
    }

    @RequestMapping(path = "/deleteReward/{id}", method = RequestMethod.GET)
    public String deleteReward(@PathVariable Long id, Model model) {
        LOG.debug("Handling /deleteReward/{id}");
        LOG.debug(id.toString());

        try {
            Offer offerToDelete = this.oService.findById(id);
            LOG.debug(offerToDelete.toString());
        } catch (EmptyResultDataAccessException e) {
            //0 rows returned - offer doesn't exist in database table.
            String msg = "The offer you're looking for does not exist.";
            model.addAttribute("issue", msg);
            return "404";
        }

        LOG.debug("Removing reward.");
        Integer resultOfRemoval = this.oService.deleteByRewardID(id);
        LOG.debug("Rows affected by removal: " + resultOfRemoval);

        if (resultOfRemoval == 0) {
            String msg = "Offer could not be deleted.";
            model.addAttribute("issue", msg);
            return "404";
        } else {
            model.addAttribute("content", "adminSearchRewards");
            return "adminMenu";
        }

        //Could serve confirmation of deletion page?
    }

    private void orderSelectOptions(String selected) {
        this.selectOptions.clear();
        if (selected.equals("total")) {
            selectOptions.add("Highest Total First");
            selectOptions.add("total");
            selectOptions.add("Alphabetical [Reward Name]");
            selectOptions.add("alphabetical");
        } else if (selected.equals("alphabetical")) {
            selectOptions.add("Alphabetical [Reward Name]");
            selectOptions.add("alphabetical");
            selectOptions.add("Highest Total First");
            selectOptions.add("total");
        }
    }

    @RequestMapping(value = "/rewards/statistics", method = RequestMethod.GET)
    public String serveRewardStatisticsPage(Model model) {
        LOG.debug("Handling /rewards/statistics");

        //Select option defaulted to Total selected.
        orderSelectOptions("total");
        model.addAttribute("options", this.selectOptions);
        model.addAttribute("content", "adminRewardStatistics");
        return "adminMenu";
    }

    @RequestMapping(value = "/rewards/statistics/populate", method = RequestMethod.GET)
    public String orderRewardsByTotal(RewardStatisticsForm rStatForm, Model model) {
        LOG.debug("Handling /rewards/statistics/populate");
        LOG.debug(rStatForm.toString());

        if (rStatForm.getOrderingChoice().equals("total")) {
            orderSelectOptions("total");

            List<Offer> rewards = this.oService.findAllByTotal();
            LOG.debug(rewards.toString());

            model.addAttribute("rewardsOrdered", rewards);
        } else if (rStatForm.getOrderingChoice().equals("alphabetical")) {
            orderSelectOptions("alphabetical");

            List<Offer> rewards = this.oService.findAllAlphabetically();
            LOG.debug(rewards.toString());

            model.addAttribute("rewardsOrdered", rewards);
        }

        model.addAttribute("options", this.selectOptions);

        model.addAttribute("content", "adminRewardStatistics");
        return "adminMenu";
    }

    @RequestMapping(path = "/rewards/create", method = RequestMethod.GET)
    public String createNewReward(CreateRewardForm rForm, Model model) {
        LOG.debug("Handling /admin/rewards/create");
        LOG.debug(rForm.toString());

        Long businessID = this.bService.findBusinessIdByName(rForm.getBusiness());
        LOG.debug("business's id: " + businessID);

        Offer offer = new Offer(rForm.getName(), rForm.getDescription(), rForm.getCost(), businessID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            Integer rowsAffected = this.oService.createNewOffer(offer, currentUserName);
            LOG.debug("Rows affected by insertion: " + rowsAffected);

            model.addAttribute("rwrd", offer);
            model.addAttribute("content", "rewardCreatedSuccessfully");
            return "adminMenu";
        }
        return "error";
    }

    @RequestMapping("/applicants")
    public String serveAdminApplicantsPage(Model model) {
        LOG.debug("Handling /admin/applicants");

        List<VolunteerApplicant> applicants = vAppsService.findAllVolunteers();
        LOG.debug(applicants.toString());
        model.addAttribute("apps", applicants);
        model.addAttribute("content", "adminApplicants");
        return "adminMenu";
    }

    @RequestMapping("/business")
    public String serveAdminCreateBusinessPage(Model model) {
        LOG.debug("Handling /admin/business");
        model.addAttribute("content", "adminCreateBusiness");
        return "adminMenu";
    }

    @RequestMapping("/businesses")
    public String serveAllBusinesses(Model model){
        model.addAttribute("businesses", bService.findAll());
        model.addAttribute("content", "businesses");
        return "adminMenu";
    }

    @RequestMapping(path = "/business/create", method = RequestMethod.GET)
    public String createnewBusiness(NewBusinessForm bForm, Model model){
        LOG.debug("Handling /admin/business/create");
        LOG.debug(bForm.toString());
        Business business = new Business(bForm.getName(), bForm.getEmail());
        Integer rowsAffected = this.bServiceJdbc.createnewBusiness(business);
        LOG.debug("Rows Affected by insertion:" + rowsAffected);
        model.addAttribute("bsnss", business);
        model.addAttribute("content", "businesscreated");
        return "adminMenu";
    }

    @RequestMapping(path = "/change_business_info")
    public String serveAsminChangeBusinessInfoPage(Model model){
        LOG.debug("Handling /admin/change_business_info");
        model = addAttributes(model);
        model.addAttribute("content", "changebusinessinfo");
        return "adminMenu";
    }

    @RequestMapping(path = "/change_business_info/change_email", method = RequestMethod.GET)
    public String changeEmail(BusinessChangeEmailForm businessChangeEmailForm, Model model) {
        LOG.debug("attempting to change email");
        LOG.debug(businessChangeEmailForm.toString());
        Long businessID = this.bService.findBusinessIdByName(businessChangeEmailForm.getBusiness());
        model = addAttributes(model);
        if (businessChangeEmailForm.getEmail().equals(businessChangeEmailForm.getEmail2())) {
            try {
                Integer rowsChanged = this.bServiceJdbc.updateBusinessEmail(businessID, businessChangeEmailForm.getEmail());
                if (rowsChanged == 1) {
                    model.addAttribute("attribute1", "The information has been updated successfuly");
                    model.addAttribute("content", "changebusinessinfo");
                    return "adminMenu";
                } else {
                    model.addAttribute("attribute2", "something went wrong");
                    model.addAttribute("content", "changebusinessinfo");
                    return "adminMenu";
                }
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("attribute2", "Email already exists");
                model.addAttribute("content", "changebusinessinfo");
                return "adminMenu";
            }
        } else {
            model.addAttribute("attribute2", "Not the same email  ");
            model.addAttribute("content", "changebusinessinfo");
            return "adminMenu";
        }

    }

    @RequestMapping(path = "/change_business_info/change_name", method = RequestMethod.GET)
    public String changeName(BusinessChangeEmailForm businessChangeEmailForm, Model model) {
        LOG.debug("attempting to change name");
        LOG.debug(businessChangeEmailForm.toString());
        Long businessID = this.bService.findBusinessIdByName(businessChangeEmailForm.getBusiness());
        model = addAttributes(model);
        if (businessChangeEmailForm.getName().equals(businessChangeEmailForm.getName2())) {
            try {
                Integer rowsChanged = this.bServiceJdbc.updateBusinessName(businessID, businessChangeEmailForm.getName());
                if (rowsChanged == 1) {
                    model.addAttribute("attribute3", "The Name has been updated successfuly");
                    model.addAttribute("content", "changebusinessinfo");
                    return "adminMenu";
                } else {
                    model.addAttribute("attribute4", "something went wrong");
                    model.addAttribute("content", "changebusinessinfo");
                    return "adminMenu";
                }
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("attribute4", "Name already exists");
                model.addAttribute("content", "changebusinessinfo");
                return "adminMenu";
            }
        } else {
            model.addAttribute("attribute4", "Not the same email");
            model.addAttribute("content", "changebusinessinfo");
            return "adminMenu";
        }

    }

    @RequestMapping(path = "/deletebusiness")
    public String serveadmindeletebusiness(Model model) {
        LOG.debug("Handling /admin/deletebusiness");
        model = addAttributes(model);
        model.addAttribute("content", "adminDeletebusiness");
        return "adminMenu";
    }

    @RequestMapping(path = "/deletebusiness/deletingbusiness")
    public String deletebusiness(BusinessChangeEmailForm businessChangeEmailForm, Model model) {
        LOG.debug("Handling /admin/deletebusiness/delelingbusiness");
        Long businessID = this.bService.findBusinessIdByName(businessChangeEmailForm.getBusiness());
        model = addAttributes(model);
        if (businessChangeEmailForm.getDelete().equals("delete")) {
            try {
                Integer rowsChanged = this.bServiceJdbc.deleteBusiness(businessID);
                if (rowsChanged == 1) {
                    model.addAttribute("attribute5", "Business has been deleted");
                    model.addAttribute("content", "adminDeletebusiness");
                    return "adminMenu";
                } else {
                    model.addAttribute("attribute6", "error");
                    model.addAttribute("content", "adminDeletebusiness");
                    return "adminMenu";
                }
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("attribute6", "error");
                model.addAttribute("content", "adminDeletebusiness");
                return "adminMenu";
            }
        } else {
            model.addAttribute("attribute6", "Type 'delete' ");
            model.addAttribute("content", "adminDeletebusiness");
            return "adminMenu";
        }

    }
}
