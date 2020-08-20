package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.VolunteerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VolunteerAchievementRepositoryJPA extends JpaRepository<VolunteerAchievement, Long>, VolunteerAchievementRepository {
    @Query(value="select * from volunteer_achievement", nativeQuery = true)
    public List<VolunteerAchievement> findAll();

    @Query(value="select * from volunteer_achievement where user_id = ?1", nativeQuery = true)
    public List<VolunteerAchievement> findByUserId(Long userid);
}
