package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.Business;
import com.nsa.mhasite.repositories.BusinessRepository;
import com.nsa.mhasite.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceStatic implements BusinessService {

    private BusinessRepository bRepo;

    @Autowired
    public BusinessServiceStatic(BusinessRepository bRepo) {
        this.bRepo = bRepo;
    }

    @Override
    public Long findBusinessIdByName(String name) {
        return this.bRepo.findBusinessIdByName(name);
    }

    @Override
    public String findBusinessNameById(Long id) {
        return this.bRepo.findBusinessNameById(id);
    }

    @Override
    public List<Business> findAll(){return this.bRepo.findAll();}

    @Override
    public Business findBusinessById(Long id){return this.bRepo.findBusinessById(id);}
}
