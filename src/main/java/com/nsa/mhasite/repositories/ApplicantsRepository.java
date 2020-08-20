package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.domain.UserRole;
import com.nsa.mhasite.domain.VolunteerApplicant;
import com.nsa.mhasite.domain.VolunteerUser;

import java.util.List;

public interface ApplicantsRepository {
    public List<VolunteerApplicant> findAll();
    public VolunteerApplicant findByVolunteerID(Long id);
//    public User saveAndFlush(User newUser);
//    public UserRole saveAndFlush(UserRole newUser);
//    public VolunteerUser saveAndFlush(VolunteerUser newUser);
    public Integer deleteByVolunteerID(Long id);
}


