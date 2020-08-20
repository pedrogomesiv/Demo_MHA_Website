package com.nsa.mhasite.domain;

import lombok.Data;

@Data
public class CreditPayForm {
    private Integer amount;
    private String gt;
    private String reason;
}
