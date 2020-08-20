package com.nsa.mhasite.statics;

import com.nsa.mhasite.domain.Business;
import com.nsa.mhasite.repositories.BusinessRepoJdbc;
import com.nsa.mhasite.repositories.BusinessRepositoryJdbc;
import com.nsa.mhasite.services.BusinessServiceJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceStaticJdbc implements BusinessServiceJdbc {

    private BusinessRepoJdbc bRepo;


    @Autowired
    public BusinessServiceStaticJdbc (BusinessRepoJdbc bRepo ) {this.bRepo = bRepo; }


    @Override
    public Integer createnewBusiness(Business business)
    {return this.bRepo.createnewBusiness(business); }

    @Override
    public Integer updateBusinessEmail(Long id, String email_address)
    {return this.bRepo.updateBusinessEmail(id, email_address);}

    @Override
    public Integer updateBusinessName(Long id, String business_name)
    {return this.bRepo.updateBusinessName(id, business_name);}
    
    @Override
    public Integer deleteBusiness(Long id)
    {return this.bRepo.deleteBusiness(id);}

}
