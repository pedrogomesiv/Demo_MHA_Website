package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class AddressRepositoryJdbc implements AddressRepository{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Address> addressMapper;

    @Value("${sql.find.address.by.user.id}")
    String findAddressByUserIdSQL;

    @Value("${sql.create.address}")
    String createAddressSQL;

    @Value("${sql.update.volunteer.address}")
    String updateAddressSQL;

    @Autowired
    public AddressRepositoryJdbc(JdbcTemplate aTemplate){
        jdbcTemplate = aTemplate;

        addressMapper = (rs, i) -> new Address(
                rs.getLong("id"),
                rs.getString("address_line_1"),
                rs.getString("address_line_2"),
                rs.getString("city"),
                rs.getString("postcode"),
                rs.getLong("user_id")
        );
    }

    @Override
    public Optional<Address> findByUserId(Long userid){
        return Optional.of(
                jdbcTemplate.queryForObject(
                        findAddressByUserIdSQL,
                        new Object[]{userid},
                        addressMapper)
        );
    }

    @Override
    public Integer createAddress(String address1, String address2, String city, String postcode, Long userid)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(address1);
        params.add(address2);
        params.add(city);
        params.add(postcode);
        params.add(userid);
        return jdbcTemplate.update(createAddressSQL, params.toArray());
    }

    @Override
    public Integer updateAddress(Long userid, String address1, String address2, String city, String postcode) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(address1);
        params.add(address2);
        params.add(city);
        params.add(postcode);
        params.add(userid);
        return jdbcTemplate.update(updateAddressSQL, params.toArray());
    }
}
