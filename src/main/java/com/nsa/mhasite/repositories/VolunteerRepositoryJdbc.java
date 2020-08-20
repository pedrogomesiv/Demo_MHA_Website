package com.nsa.mhasite.repositories;

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
public class VolunteerRepositoryJdbc implements VolunteerRepository{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<VolunteerUser> volunteerMapper;

    @Value("${sql.create.volunteer}")
    String createVolunteerSQL;

    @Value("${sql.find.volunteer.by.search}")
    String findVolunteerBySearchSQL;

    @Value("${sql.find.volunteer.by.username}")
    String findVolunteerByUsernameSQL;

    @Value("${sql.find.volunteer.by.user.id}")
    String findVolunteerByUserIdSQL;

    @Value("${sql.update.user.phone}")
    String updatePhoneSQL;

    @Value("${sql.update.user.emergency.phone}")
    String updateEmergencyPhoneSQL;

    @Value("${sql.update.volunteer.info}")
    String updateVolunteerInfoSQL;

    @Value("${sql.give.user}")
    String giveCreditsSQL;

    @Value("${sql.take.user}")
    String takeCreditsSQL;

    @Autowired
    public VolunteerRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        volunteerMapper = (rs, i) -> new VolunteerUser(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("surname"),
                rs.getString("phone_number"),
                rs.getString("emergency_number"),
                rs.getBoolean("mha_tennant"),
                rs.getBoolean("previous_volunteer"),
                rs.getString("volunteer_experience"),
                rs.getBoolean("conditions"),
                rs.getString("condition_details"),
                rs.getBoolean("medication"),
                rs.getString("medication_details"),
                rs.getBoolean("allergies"),
                rs.getString("allergy_details"),
                rs.getInt("current_balance"),
                rs.getInt("total_amount")
        );
    }

    @Override
    public Integer createVolunteerUser(Long userid, String firstName, String lastName, String phone, String emergency_number, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(userid);
        params.add(firstName);
        params.add(lastName);
        params.add(phone);
        params.add(emergency_number);
        params.add(mha_tenant);
        params.add(previous_volunteer);
        params.add(volunteer_experience);
        params.add(conditions);
        params.add(condition_details);
        params.add(medication);
        params.add(medication_details);
        params.add(allergies);
        params.add(allergy_details);
        return jdbcTemplate.update(createVolunteerSQL, params.toArray());
    }

    @Override
    public List<VolunteerUser> findVolunteerBySearch(String search){
        return jdbcTemplate.query(
                findVolunteerBySearchSQL,
                new Object[]{search, "%" + search + "%", search},
                volunteerMapper);
    }

    @Override
    public Optional<VolunteerUser> findVolunteerByUsername(String username){
        return Optional.of(
                jdbcTemplate.queryForObject(
                        findVolunteerByUsernameSQL,
                        new Object[]{username},
                        volunteerMapper)
        );
    }

    @Override
    public Optional<VolunteerUser> findVolunteerByUserId(Long userid){
        return Optional.of(
                jdbcTemplate.queryForObject(
                        findVolunteerByUserIdSQL,
                        new Object[]{userid},
                        volunteerMapper)
        );
    }

    @Override
    public Integer changeNumber(Long id, String phone){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(phone);
        params.add(id);
        return jdbcTemplate.update(updatePhoneSQL, params.toArray());
    }

    @Override
    public Integer changeEmergencyNumber(Long id, String phone){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(phone);
        params.add(id);
        return jdbcTemplate.update(updateEmergencyPhoneSQL, params.toArray());
    }

    @Override
    public Integer changeUserDetails(Long userid, String firstName, String lastName, String phone, String emergency_number, Boolean mha_tenant, Boolean previous_volunteer, String volunteer_experience, Boolean conditions, String condition_details, Boolean medication, String medication_details, Boolean allergies, String allergy_details){
        ArrayList<Object> params = new ArrayList<>();
        params.add(firstName);
        params.add(lastName);
        params.add(phone);
        params.add(emergency_number);
        params.add(mha_tenant);
        params.add(previous_volunteer);
        params.add(volunteer_experience);
        params.add(conditions);
        params.add(condition_details);
        params.add(medication);
        params.add(medication_details);
        params.add(allergies);
        params.add(allergy_details);
        params.add(userid);
        return jdbcTemplate.update(updateVolunteerInfoSQL, params.toArray());
    }

    @Override
    public Integer giveCredits(Long userid, Integer amount){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(amount);
        params.add(amount);
        params.add(userid);
        return jdbcTemplate.update(giveCreditsSQL, params.toArray());
    }

    @Override
    public Integer takeCredits(Long userid, Integer amount){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(amount);
        params.add(amount);
        params.add(userid);
        return jdbcTemplate.update(takeCreditsSQL, params.toArray());
    }
}
