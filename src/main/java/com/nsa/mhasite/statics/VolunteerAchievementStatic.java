package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.VolunteerAchievement;
import com.nsa.mhasite.repositories.VolunteerAchievementRepository;
import com.nsa.mhasite.services.VolunteerAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerAchievementStatic implements VolunteerAchievementService{
    private VolunteerAchievementRepository volunteerAchievementRepository;

    @Autowired
    public VolunteerAchievementStatic(VolunteerAchievementRepository vRepo){
        volunteerAchievementRepository = vRepo;
    }

    @Override
    public List<VolunteerAchievement> findAll(){
        return volunteerAchievementRepository.findAll();
    }

    @Override
    public List<VolunteerAchievement> findByUserId(Long userid){
        return volunteerAchievementRepository.findByUserId(userid);
    }
}
