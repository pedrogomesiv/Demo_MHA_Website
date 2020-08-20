package com.nsa.mhasite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "allocation")
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date_allocated")
    private Date date;

    @Column(name = "credits_allocated")
    private Integer credits_allocated;

    @Column(name = "reason")
    private String reason;

    @Column(name = "user_id")
    private Long userid;
}
