package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.User;
import com.nsa.mhasite.domain.UserRole;
import com.nsa.mhasite.domain.VolunteerApplicant;
import com.nsa.mhasite.domain.VolunteerUser;

import java.util.List;

public interface VolunteerApplicantsService {
    public List<VolunteerApplicant> findAllVolunteers();
    public VolunteerApplicant findByVolunteerAppId(Long id);
//    public User createNewUserFromApplicant(User newUser);
//    public UserRole createUserRoleForNewUserFromApplicant(UserRole newUser);
//    public VolunteerUser createUserVolunteerFromApplicant(VolunteerUser newUser);
    public Integer deleteByVolunteerID(Long id);
}
