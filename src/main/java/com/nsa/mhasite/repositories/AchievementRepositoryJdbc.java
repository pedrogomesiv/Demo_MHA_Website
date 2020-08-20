package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AchievementRepositoryJdbc implements AchievementRepository{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Achievement> achievementMapper;

    @Value("CALL getMissingAchievements(?)")
    String getMissingSQL;

    @Value("CALL giveAchievement(?, ?)")
    String giveSQL;

    @Value("select * from achievement where id = ?")
    String findById;

    @Autowired
    public AchievementRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        achievementMapper = (rs, i) -> new Achievement(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("credits"),
                rs.getString("file_name")
        );
    }


    @Override
    public List<Achievement> missingAchievements(Long userid){
        return jdbcTemplate.query(getMissingSQL, new Object[]{userid}, achievementMapper);
    }

    @Override
    public Integer awardAchievement(Long userid, Long achievementid){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(userid);
        params.add(achievementid);
        return jdbcTemplate.update(giveSQL, params.toArray());
    }

    @Override
    public Optional<Achievement> findById(Long achievementid){
        return Optional.of(
                jdbcTemplate.queryForObject(
                        findById,
                        new Object[]{achievementid},
                        achievementMapper)
        );
    }
}
