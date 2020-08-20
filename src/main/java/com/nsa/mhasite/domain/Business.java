package com.nsa.mhasite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "business_name")
    private String Bname;

    @Column(name = "email_address")
    private String BusinessEmail;

    @JsonIgnore
    @OneToMany(mappedBy = "businessInfo", cascade = CascadeType.ALL)
    private List<Rewards> rewardInfo;

    public Business(String bname, String businessEmail) {
        Bname = bname;
        BusinessEmail = businessEmail;
    }
}
