package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Achievement;
import java.util.List;
import java.util.Optional;

public interface AchievementService {
    List<Achievement> missingAchievements(Long userid);
    Integer awardAchievement(Long userid, Long achievementid);
    Optional<Achievement> findById(Long achievementid);
}
