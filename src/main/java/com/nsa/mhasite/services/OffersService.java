package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Offer;
import com.nsa.mhasite.domain.Rewards;

import javax.persistence.Table;
import java.util.List;

public interface OffersService {
    List<Offer> findAll();
    public Integer createNewOffer(Offer offer, String creator);
    public Offer findById(Long id);
    public Integer redeemReward(Long userId, Long rewardId);
    public List<Offer> findRewardsBySearch(String searchTerm);
    public List<Offer> findAllByTotal();
    public List<Offer> findAllAlphabetically();
    public Integer deleteByRewardID(Long id);
    public Integer increaseNumTimesRedeemed(Long id);
}
