package com.example.dao;

import com.example.model.Client;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
        //List<User> findByAddressPharmacyLikeIgnoreCase(String lastName);
        //List<User> findByCleanAddressLikeIgnoreCase(String lastName);
        //List<User> findByRegionLikeIgnoreCase(String lastName);
        //List<User> (String region, String cleanAddress, String addressPharmacy);
        //List<User> findByRegionLikeIgnoreCaseAndCleanAddressLikeIgnoreCaseAndAddressPharmacyLikeIgnoreCase( String addressPharmacy,String cleanAddress, String region);
}
