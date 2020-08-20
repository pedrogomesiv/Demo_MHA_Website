package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Business;
import com.nsa.mhasite.domain.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepositoryJPA extends JpaRepository<Business, Long>, BusinessRepository {

    @Query(value = "Select id from business where business_name =?1 or " +
            "business_name like ?1", nativeQuery = true)
    public Long findBusinessIdByName(String name);

    @Query(value = "Select business_name from business where id =?", nativeQuery = true)
    public String findBusinessNameById(Long id);

    @Query(value = "select * from business", nativeQuery = true)
    public List<Business> findAll();

    @Query(value = "select * from business where id = ?", nativeQuery = true)
    public Business findBusinessById(Long id);
}
