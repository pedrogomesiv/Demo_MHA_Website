package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.VolunteerAchievement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerAchievementRepository {
    List<VolunteerAchievement> findAll();
    List<VolunteerAchievement> findByUserId(Long userid);
}
