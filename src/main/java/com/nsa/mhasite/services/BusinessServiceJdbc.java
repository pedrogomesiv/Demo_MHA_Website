package com.nsa.mhasite.services;

import com.nsa.mhasite.domain.Business;
import org.springframework.stereotype.Service;

public interface BusinessServiceJdbc {
    public Integer createnewBusiness(Business business);
    public Integer updateBusinessEmail(Long id, String email_address);
    public Integer updateBusinessName(Long id, String business_name);
    public Integer deleteBusiness(Long id);

}
