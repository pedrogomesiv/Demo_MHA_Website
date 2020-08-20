package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Redemption;

import java.util.List;

public interface RedemptionService {
    public List<Redemption> findByUser_id(Long user_id);
}
