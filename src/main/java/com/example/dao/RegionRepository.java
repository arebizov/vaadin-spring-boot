package com.example.dao;

import com.example.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
        //List<User> findByAddressPharmacyLikeIgnoreCase(String lastName);
        //List<User> findByCleanAddressLikeIgnoreCase(String lastName);
        //List<User> findByRegionLikeIgnoreCase(String lastName);
        //List<User> (String region, String cleanAddress, String addressPharmacy);
        //List<User> findByRegionLikeIgnoreCaseAndCleanAddressLikeIgnoreCaseAndAddressPharmacyLikeIgnoreCase( String addressPharmacy,String cleanAddress, String region);

}
