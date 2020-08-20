package com.nsa.mhasite.repositories;

import com.nsa.mhasite.domain.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedemptionRepositoryJPA extends JpaRepository<Redemption, Long>, RedemptionRepository {

    @Query(value = "select * from redemption where user_id =?", nativeQuery = true)
    public List<Redemption> findByUser_id(Long user_id);

}
