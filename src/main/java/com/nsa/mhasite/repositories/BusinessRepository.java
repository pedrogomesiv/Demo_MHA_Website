package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Business;

import java.util.List;

public interface BusinessRepository {
    public Long findBusinessIdByName(String name);
    public String findBusinessNameById(Long id);
    public Business findBusinessById(Long id);
    public List<Business> findAll();
}
