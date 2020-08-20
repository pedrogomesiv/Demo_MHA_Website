package com.nsa.mhasite.repositories;

import java.util.Date;

public interface AllocationRepository {
    public Integer createRecord(Date date, Integer credits, String reason, Long userid);
}
