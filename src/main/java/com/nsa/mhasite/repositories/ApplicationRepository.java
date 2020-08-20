package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.VolunteerUser;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {
    Integer createVolunteerApplicant(String firstName, String lastName, String phone, String emergency_number, String email, String address_line_1, String address_line_2, String city, String postcode, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details);

}
