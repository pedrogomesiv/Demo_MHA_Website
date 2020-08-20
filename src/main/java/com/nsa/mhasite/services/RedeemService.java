package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.*;
import com.nsa.mhasite.email.EmailSessionBean;
import com.nsa.mhasite.repositories.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class RedeemService {
    static final Logger LOG = LoggerFactory.getLogger(RedeemService.class);
    private VolunteerRepository volunteerRepository;
    private UserRepositoryJdbc userRepositoryJdbc;
    private OffersRepository offersRepository;
    private BusinessRepository businessRepository;
    private RedemptionRepository redemptionRepository;
    private EmailSessionBean emailSessionBean;

    @Autowired
    public RedeemService(VolunteerRepository vRepo, UserRepositoryJdbc uRepo, OffersRepository oRepo, BusinessRepository bRepo, RedemptionRepository rRepo, EmailSessionBean eMail){
        this.volunteerRepository = vRepo;
        this.userRepositoryJdbc = uRepo;
        this.offersRepository = oRepo;
        this.businessRepository = bRepo;
        this.redemptionRepository = rRepo;
        this.emailSessionBean = eMail;
    }


    public String redeemReward(Long rewardId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepositoryJdbc.findUserByUsername(currentUserName).get();
            VolunteerUser volunteer = volunteerRepository.findVolunteerByUserId(user.getId()).get();
            Offer offer = offersRepository.findById(rewardId);
            Business business = businessRepository.findBusinessById(offer.getBusinessID());
            if(volunteer.getCurrent_balance() - offer.getCost() >= 0){
                Integer rowsChanged = offersRepository.redeemReward(user.getId(), offer.getId());
                if (rowsChanged == 2){
                    LOG.debug("redemption successful in database");
                    List<Redemption> redemptions = redemptionRepository.findByUser_id(user.getId());
                    Long tempMax = 0L;
                    for(Redemption red : redemptions){
                        if (red.getId() > tempMax){
                            tempMax = red.getId();
                        }
                    }
                    String code = String.format("%06d", tempMax);
                    LOG.debug("offer code is: "+code);
                    emailSessionBean.redemptionEmailUser(user, offer, volunteer, code);
                    emailSessionBean.redemptionEmailBusiness(business, offer, volunteer, code);
                    return "Success";
                }
                return "Database error";
            }
            return "Not enough credits error";
        }
        return "User not logged in";
    }

}
