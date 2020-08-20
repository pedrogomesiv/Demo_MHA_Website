package com.nsa.mhasite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AchievementDetails {
    private String name;
    private String description;
    private Date date;
    private String image;
}
