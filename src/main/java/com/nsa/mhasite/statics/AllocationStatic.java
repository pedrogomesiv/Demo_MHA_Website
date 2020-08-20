package com.nsa.mhasite.statics;

import com.nsa.mhasite.repositories.AllocationRepository;
import com.nsa.mhasite.services.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AllocationStatic implements AllocationService {
    private AllocationRepository allocationRepository;

    @Autowired
    public AllocationStatic(AllocationRepository aRepo){allocationRepository = aRepo;}

    @Override
    public Integer createRecord(Date date, Integer credits, String reason, Long userid){
        return allocationRepository.createRecord(date, credits, reason, userid);
    }
}
