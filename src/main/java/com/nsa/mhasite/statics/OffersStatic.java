package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.Offer;
import com.nsa.mhasite.domain.Rewards;
import com.nsa.mhasite.repositories.OffersRepository;
import com.nsa.mhasite.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffersStatic implements OffersService {
    private OffersRepository oRepo;

    @Autowired
    public OffersStatic(OffersRepository oRepo)
    {
        this.oRepo = oRepo;
    }

    @Override
    public List<Offer> findAll()
    {
        return oRepo.findAll();
    }

    @Override
    public Integer createNewOffer(Offer offer, String creator) {
        return this.oRepo.createNewOffer(offer, creator);
    }

    @Override
    public Offer findById(Long id) {
        return this.oRepo.findById(id);
    }

    @Override
    public Integer redeemReward(Long userId, Long rewardId){return this.oRepo.redeemReward(userId, rewardId);}

    @Override
    public List<Offer> findRewardsBySearch(String searchTerm) {
        return this.oRepo.findRewardsBySearch(searchTerm);
    }

    @Override
    public List<Offer> findAllByTotal() {
        return this.oRepo.findAllByTotal();
    }

    @Override
    public List<Offer> findAllAlphabetically() {
        return this.oRepo.findAllAlphabetically();
    }

    @Override
    public Integer deleteByRewardID(Long id) {
        return this.oRepo.deleteByRewardID(id);
    }

    @Override
    public Integer increaseNumTimesRedeemed(Long id) {
        return this.oRepo.increaseNumTimesRedeemed(id);
    }
}
