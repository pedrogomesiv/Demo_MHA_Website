package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.VolunteerUser;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    List<VolunteerUser> findVolunteerBySearch(String searchTerm);
    Optional<VolunteerUser> findVolunteerByUsername(String username);
    Optional<VolunteerUser> findVolunteerByUserId(Long userid);
    Integer createVolunteerUser(Long userid, String firstName, String lastName, String phone, String emergency_number, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details);
    Integer changeNumber(Long userid, String number);
    Integer changeEmergencyNumber(Long userid, String number);
    Integer changeUserDetails(Long userid, String firstName, String lastName, String phone, String emergency_number, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details);
    Integer giveCredits(Long userid, Integer amount);
    Integer takeCredits(Long userid, Integer amount);
}
