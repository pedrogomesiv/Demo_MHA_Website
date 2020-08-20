package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Address;

import java.util.Optional;

public interface AddressRepository {
    Optional<Address> findByUserId(Long userid);
    Integer createAddress(String address1, String address2, String city, String postcode, Long userid);
    Integer updateAddress(Long userid, String address1, String address2, String city, String postcode);
}
