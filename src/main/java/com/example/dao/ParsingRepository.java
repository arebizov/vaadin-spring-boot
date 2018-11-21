package com.example.dao;

import com.example.model.Parsing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParsingRepository extends JpaRepository<Parsing, Long> {
        //List<User> findByAddressPharmacyLikeIgnoreCase(String lastName);
        //List<User> findByCleanAddressLikeIgnoreCase(String lastName);
        //List<User> findByRegionLikeIgnoreCase(String lastName);
        //List<User> (String region, String cleanAddress, String addressPharmacy);
        //List<User> findByRegionLikeIgnoreCaseAndCleanAddressLikeIgnoreCaseAndAddressPharmacyLikeIgnoreCase( String addressPharmacy,String cleanAddress, String region);

}
