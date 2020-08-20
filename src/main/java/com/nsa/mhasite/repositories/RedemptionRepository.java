package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Redemption;

import java.util.List;
import java.util.Optional;

public interface RedemptionRepository {
    public List<Redemption> findByUser_id(Long user_id);
}
