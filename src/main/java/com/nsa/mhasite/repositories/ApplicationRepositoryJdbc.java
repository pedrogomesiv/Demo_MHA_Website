package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.VolunteerApplicant;
import com.nsa.mhasite.domain.VolunteerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationRepositoryJdbc implements ApplicationRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<VolunteerApplicant> volunteerMapper;

    @Value("${sql.create.applicant}")
    String createApplicantSQL;

    @Autowired
    public ApplicationRepositoryJdbc(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        volunteerMapper = (rs, i) -> new VolunteerApplicant(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("surname"),
                rs.getString("username"),
                rs.getString("phone_number"),
                rs.getString("emergency_number"),
                rs.getString("address_line_1"),
                rs.getString("address_line_2"),
                rs.getString("city"),
                rs.getString("postcode"),
                rs.getBoolean("mha_tennant"),
                rs.getBoolean("previous_volunteer"),
                rs.getString("volunteer_experience"),
                rs.getBoolean("conditions"),
                rs.getString("condition_details"),
                rs.getBoolean("medication"),
                rs.getString("medication_details"),
                rs.getBoolean("allergies"),
                rs.getString("allergy_details")
        );
    }

    @Override
    public Integer createVolunteerApplicant(String firstName, String lastName, String username, String phone, String emergency_number, String address_line_1, String address_line_2, String city, String postcode, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(firstName);
        params.add(lastName);
        params.add(username);
        params.add(phone);
        params.add(emergency_number);
        params.add(address_line_1);
        params.add(address_line_2);
        params.add(city);
        params.add(postcode);
        params.add(mha_tenant);
        params.add(previous_volunteer);
        params.add(volunteer_experience);
        params.add(conditions);
        params.add(condition_details);
        params.add(medication);
        params.add(medication_details);
        params.add(allergies);
        params.add(allergy_details);
        return jdbcTemplate.update(createApplicantSQL, params.toArray());
    }

}

