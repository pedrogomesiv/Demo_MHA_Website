package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.Redemption;
import com.nsa.mhasite.repositories.RedemptionRepository;
import com.nsa.mhasite.services.RedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedemptionStatic implements RedemptionService {

    private RedemptionRepository rRepo;

    @Autowired
    public RedemptionStatic(RedemptionRepository rRepo) {
        this.rRepo = rRepo;
    }

    @Override
    public List<Redemption> findByUser_id(Long user_id) {
        return this.rRepo.findByUser_id(user_id);
    }
}
