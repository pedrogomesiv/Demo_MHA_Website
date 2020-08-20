package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public  class BusinessRepositoryJdbc implements BusinessRepoJdbc {
    private JdbcTemplate jdbcTemplate;

    @Value("insert into business (business_name, email_address) values (?,?)")
    String insetNewbusinessSQL;

    @Value("update business set email_address = ? where id = ?")
    String updateBusinessEmailSQL;

    @Value("update business set business_name = ? where id = ?")
    String updateBusinessNameSQL;

    @Value("call deleteBusiness(?)")
    String deleteBusinessSQL;

    @Autowired
    public BusinessRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;
    }

    @Override
    public Integer createnewBusiness(Business business){
        ArrayList<Object> params =  new ArrayList<>();
        params.add(business.getBname());
        params.add(business.getBusinessEmail());

        return jdbcTemplate.update(insetNewbusinessSQL, params.toArray());

    }

    @Override
    public Integer updateBusinessEmail(Long id, String email_address){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(email_address);
        params.add(id);
        return jdbcTemplate.update(updateBusinessEmailSQL, params.toArray());
    }

    @Override
    public Integer updateBusinessName(Long id, String business_name){
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(business_name);
        params.add(id);
        return jdbcTemplate.update(updateBusinessNameSQL, params.toArray());
    }

    @Override
    public Integer deleteBusiness(Long id) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(id);

        return jdbcTemplate.update(deleteBusinessSQL, params.toArray());
    }


}
