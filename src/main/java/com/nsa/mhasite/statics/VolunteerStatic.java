package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.VolunteerUser;
import com.nsa.mhasite.repositories.VolunteerRepository;
import com.nsa.mhasite.services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerStatic implements VolunteerService{

    private VolunteerRepository vRepo;

    @Autowired
    public VolunteerStatic(VolunteerRepository vRepo) {
        this.vRepo = vRepo;
    }

    @Override
    public List<VolunteerUser> findVolunteerBySearch(String searchTerm) {
        return vRepo.findVolunteerBySearch(searchTerm);
    }

    @Override
    public Optional<VolunteerUser> findVolunteerByUsername(String username) {
        return vRepo.findVolunteerByUsername(username);
    }

    @Override
    public Optional<VolunteerUser> findVolunteerByUserId(Long userid) {
        return vRepo.findVolunteerByUserId(userid);
    }

    @Override
    public Integer createVolunteerUser(Long userid, String firstName, String lastName, String phone, String emergency_number, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details){
        return vRepo.createVolunteerUser(userid, firstName, lastName, phone, emergency_number, mha_tenant, previous_volunteer, volunteer_experience, conditions, condition_details, medication, medication_details, allergies, allergy_details);
    }

    @Override
    public Integer changeNumber(Long userid, String number){
        return vRepo.changeNumber(userid, number);
    }

    @Override
    public Integer changeEmergencyNumber(Long userid, String number){
        return vRepo.changeEmergencyNumber(userid, number);
    }

    @Override
    public Integer changeUserDetails(Long userid, String firstName, String lastName, String phone, String emergency_number, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details){
        return vRepo.changeUserDetails(userid, firstName, lastName, phone, emergency_number, mha_tenant, previous_volunteer, volunteer_experience, conditions, condition_details, medication, medication_details, allergies, allergy_details);
    }

    @Override
    public Integer giveCredits(Long userid, Integer amount){return vRepo.giveCredits(userid, amount);}

    @Override
    public Integer takeCredits(Long userid, Integer amount){return vRepo.takeCredits(userid, amount);}
}
