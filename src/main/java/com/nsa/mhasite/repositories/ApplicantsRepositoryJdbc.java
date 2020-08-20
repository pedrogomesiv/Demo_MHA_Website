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
public class ApplicantsRepositoryJdbc implements ApplicantsRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<VolunteerApplicant> applicantMapper;

    @Value("Select * from volunteer_application where id =?")
    String findApplicantByIdSQL;

    @Value("Select * from volunteer_application")
    String findAllSQL;

    @Value("Delete from volunteer_application where id=?")
    String deleteApplicantByIdSQL;

    @Autowired
    public ApplicantsRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        applicantMapper = (rs, i) -> new VolunteerApplicant(
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
    public List<VolunteerApplicant> findAll() {
        return jdbcTemplate.query(
                findAllSQL,
                new Object[]{},
                applicantMapper);
    }

    @Override
    public VolunteerApplicant findByVolunteerID(Long id) {
        return jdbcTemplate.queryForObject(
                        findApplicantByIdSQL,
                        new Object[]{id},
                        applicantMapper);
    }

    @Override
    public Integer deleteByVolunteerID(Long id) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(id);

        return jdbcTemplate.update(deleteApplicantByIdSQL, params.toArray());
    }
}
