package com.nsa.mhasite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reward")
public class Rewards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "reward_name")
    private String Rewardname;

    @Column(name = "reward_description")
    private String RewardDescription;

    @Column(name = "cost")
    private String RewardCost;

    @ManyToOne
    @JoinColumn(name = "business_id")
    @JsonIgnore
    private Business businessInfo;
}
