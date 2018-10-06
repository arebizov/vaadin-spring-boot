package com.example.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Client  {


    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "correct_address")
    private String addressPharmacy;

    @Column(name="clean_address")
    private String cleanAddress;

    @Column(name="region")
    private String region;


    @Column(name="pharmacy_net_name")
    private String pharmacyNet;

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public String getPharmacyNet() { return pharmacyNet; }

    public void setPharmacyNet(String pharmacyNet) { this.pharmacyNet = pharmacyNet; }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getaddressPharmacy() {
        return addressPharmacy;
    }

    public void setaddressPharmacy(String addressPharmacy) {
        this.addressPharmacy = addressPharmacy;
    }

    public String getCleanAddress() {
        return cleanAddress;
    }

    public void setCleanAddress(String cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public Client(Long id, String addressPharmacy, String cleanAddress, String region, String pharmacyNet) {
        Id = id;
        this.addressPharmacy = addressPharmacy;
        this.cleanAddress = cleanAddress;
        this.region = region;
        this.pharmacyNet = pharmacyNet;


    }

    public Client() {

    }

    @Override
    public String toString() {
        return "Client{" +
                "Id=" + Id +
                ", addressPharmacy='" + addressPharmacy + '\'' +
                ", cleanAddress='" + cleanAddress + '\'' +
                ", region='" + region + '\'' +
                ", pharmacyNet='" + pharmacyNet + '\'' +
                '}';
    }


}
