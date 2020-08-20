package com.nsa.mhasite.domain;

import lombok.Data;

@Data
public class RewardAndOffer {
    private String businessName;
    private Offer offer;

    public RewardAndOffer(String businessName, Offer offer) {
        this.businessName = businessName;
        this.offer = offer;
    }
}
