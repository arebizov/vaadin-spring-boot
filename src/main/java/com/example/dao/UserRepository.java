package com.example.dao;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
        //List<User> findByAddressPharmacyLikeIgnoreCase(String lastName);
        //List<User> findByCleanAddressLikeIgnoreCase(String lastName);
        //List<User> findByRegionLikeIgnoreCase(String lastName);
        //List<User> (String region, String cleanAddress, String addressPharmacy);
        //List<User> findByRegionLikeIgnoreCaseAndCleanAddressLikeIgnoreCaseAndAddressPharmacyLikeIgnoreCase( String addressPharmacy,String cleanAddress, String region);
}
