package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Achievement;
import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.domain.VolunteerUser;
import com.nsa.mhasite.email.EmailSessionBean;
import com.nsa.mhasite.repositories.AchievementRepository;
import com.nsa.mhasite.repositories.UserRepository;
import com.nsa.mhasite.repositories.VolunteerRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class AchievementMonitorService {
    static final Logger LOG = LoggerFactory.getLogger(AchievementMonitorService.class);

    private AchievementRepository achievementRepository;
    private EmailSessionBean emailSessionBean;
    private UserRepository userRepository;
    private VolunteerRepository volunteerRepository;

    @Autowired
    public AchievementMonitorService(AchievementRepository aRepo, EmailSessionBean email, UserRepository uRepo, VolunteerRepository vRepo){
        achievementRepository = aRepo;
        emailSessionBean = email;
        userRepository = uRepo;
        volunteerRepository = vRepo;
    }

    public void processAchievements(Long userid){
        List<Achievement> missingAchievements = achievementRepository.missingAchievements(userid);
        LOG.debug(String.valueOf(missingAchievements.size()) + " new achievements to be awarded");

        User user = userRepository.findUserByUserId(userid).get();
        VolunteerUser volunteerUser = volunteerRepository.findVolunteerByUserId(userid).get();

        for(Achievement achievement : missingAchievements){
            achievementRepository.awardAchievement(userid, achievement.getId());
            emailSessionBean.achievementEmail(user, volunteerUser, achievement);
        }

        LOG.debug("Achievements processed");
    }
}
