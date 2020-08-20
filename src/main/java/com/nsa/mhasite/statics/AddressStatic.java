package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.Address;
import com.nsa.mhasite.repositories.AddressRepository;
import com.nsa.mhasite.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressStatic implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressStatic(AddressRepository aRepo){addressRepository = aRepo;}

    @Override
    public Optional<Address> findByUserId(Long userid){
        return addressRepository.findByUserId(userid);
    }

    @Override
    public Integer createAddress(String address1, String address2, String city, String postcode, Long userid){
        return addressRepository.createAddress(address1, address2, city, postcode, userid);
    }

    @Override
    public Integer updateAddress(Long userid, String address1, String address2, String city, String postcode){
        return addressRepository.updateAddress(userid, address1, address2, city, postcode);
    }
}
