package com.nsa.mhasite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//AKA, A history credit
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "redemption")
public class Redemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "reward_id")
    private Long reward_id;

    @Column(name = "redemption_date")
    private Date redeption_date;
}
