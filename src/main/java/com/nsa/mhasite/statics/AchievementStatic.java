package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.Achievement;
import com.nsa.mhasite.repositories.AchievementRepository;
import com.nsa.mhasite.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementStatic implements AchievementService {
    private AchievementRepository achievementRepository;

    @Autowired
    public AchievementStatic(AchievementRepository aRepo){
        achievementRepository = aRepo;
    }

    @Override
    public List<Achievement> missingAchievements(Long userid){
        return achievementRepository.missingAchievements(userid);
    }

    @Override
    public Integer awardAchievement(Long userid, Long achievementid){
        return achievementRepository.awardAchievement(userid, achievementid);
    }

    @Override
    public Optional<Achievement> findById(Long achievementid){
        return achievementRepository.findById(achievementid);
    };
}
