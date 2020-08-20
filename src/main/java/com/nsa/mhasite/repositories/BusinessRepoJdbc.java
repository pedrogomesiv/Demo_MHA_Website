package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Business;

public interface BusinessRepoJdbc {
    public Integer createnewBusiness(Business business);
    public Integer updateBusinessEmail(Long id, String email_address);
    public Integer updateBusinessName(Long id, String business_name);
    public Integer deleteBusiness(Long id);
}
