package com.nsa.mhasite.services;

import java.util.Date;

public interface AllocationService {
    public Integer createRecord(Date date, Integer credits, String reason, Long userid);
}
