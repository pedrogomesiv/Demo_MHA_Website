package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Business;

import java.util.List;

public interface BusinessService {
    public Long findBusinessIdByName(String name);
    public String findBusinessNameById(Long id);
    public List<Business> findAll();
    public Business findBusinessById(Long id);
}
