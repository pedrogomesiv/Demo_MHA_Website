package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Address;

import java.util.Optional;

public interface AddressService {
    public Optional<Address> findByUserId(Long userid);
    public Integer createAddress(String address1, String address2, String city, String postcode, Long userid);
    public Integer updateAddress(Long userid, String address1, String address2, String city, String postcode);
}
