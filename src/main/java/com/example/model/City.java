
package com.example.model;
import javax.persistence.*;

@Entity
@Table(name="D_City", schema = "ETL")

public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "city")
    private String city;

    @Column(name="mp")
    private String mp;

    @Column(name="code_region")
    private String code_region;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getcode_region() {
        return code_region;
    }

    public void setcode_region(String code_region) {
        this.code_region = code_region;
    }

    public City() {

    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", city='" + city + '\'' +
                ", mp='" + mp + '\'' +
                ", code_region='" + code_region + '\'' +
                '}';
    }
}


