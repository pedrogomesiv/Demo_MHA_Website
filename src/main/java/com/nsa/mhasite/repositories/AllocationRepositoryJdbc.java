package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Allocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public class AllocationRepositoryJdbc implements AllocationRepository{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Allocation> allocationMapper;

    @Value("${sql.create.allocation}")
    String createAllocationSQL;

    @Autowired
    public AllocationRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        allocationMapper = (rs, i) -> new Allocation(
                rs.getLong("id"),
                rs.getDate("date_allocated"),
                rs.getInt("credits_allocated"),
                rs.getString("reason"),
                rs.getLong("user_id")
        );
    }

    @Override
    public Integer createRecord(Date date, Integer credits, String reason, Long userid){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(date);
        params.add(credits);
        params.add(reason);
        params.add(userid);
        return jdbcTemplate.update(createAllocationSQL, params.toArray());
    }
}
