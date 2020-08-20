package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Achievement;
import com.nsa.mhasite.domain.AchievementDetails;
import com.nsa.mhasite.domain.VolunteerAchievement;
import com.nsa.mhasite.repositories.AchievementRepository;
import com.nsa.mhasite.repositories.VolunteerAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementDetailsService {
    private AchievementRepository achievementRepository;
    private VolunteerAchievementRepository volunteerAchievementRepository;

    @Autowired
    public AchievementDetailsService(AchievementRepository aRepo, VolunteerAchievementRepository vRepo){
        achievementRepository = aRepo;
        volunteerAchievementRepository = vRepo;
    }

    public List<AchievementDetails> getAchievements(Long userid){
        List<AchievementDetails> achievementDetails = new ArrayList<>();
        for(VolunteerAchievement volunteerAchievement : volunteerAchievementRepository.findByUserId(userid)){
            Achievement achievement = achievementRepository.findById(volunteerAchievement.getAchievementid()).get();
            achievementDetails.add(new AchievementDetails(achievement.getName(), achievement.getDescription(), volunteerAchievement.getDate(), achievement.getFileName()));
        }
        return achievementDetails;
    }
}
