
package com.example.model;
import javax.persistence.*;

@Entity
@Table(name="D_Region", schema = "ETL")

public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "region")
    private String region;

    @Column(name="mp")
    private String mp;

    @Column(name="id_fo")
    private String id_fo;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getId_fo() {
        return id_fo;
    }

    public void setId_fo(String id_fo) {
        this.id_fo = id_fo;
    }

    @Override
    public String toString() {
        return "Region{" +
                "Id=" + Id +
                ", region='" + region + '\'' +
                ", mp='" + mp + '\'' +
                ", id_fo='" + id_fo + '\'' +
                '}';
    }
}


