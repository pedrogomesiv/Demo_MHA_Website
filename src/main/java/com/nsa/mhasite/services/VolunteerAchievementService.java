package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.VolunteerAchievement;

import java.util.List;

public interface VolunteerAchievementService {
    List<VolunteerAchievement> findAll();
    List<VolunteerAchievement> findByUserId(Long userid);
}
