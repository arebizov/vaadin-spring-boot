package com.example.model;
import javax.persistence.*;

@Entity
@Table(name="PARSING", schema = "ETL")

public class Parsing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "correct_address")
    private String correctAddress;

    @Column(name="clean_address")
    private String cleanAddress;

    @Column(name="region")
    private String region;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCorrectAddress() {
        return correctAddress;
    }

    public void setCorrectAddress(String correctAddress) {
        this.correctAddress = correctAddress;
    }

    public String getCleanAddress() {
        return cleanAddress;
    }

    public void setCleanAddress(String cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    @Override
    public String toString() {
        return "Parsing{" +
                "Id=" + Id +
                ", correctAddress='" + correctAddress + '\'' +
                ", cleanAddress='" + cleanAddress + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}


