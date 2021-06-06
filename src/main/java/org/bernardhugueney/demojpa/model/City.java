package org.bernardhugueney.demojpa.model;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "\"City\"", schema = "public")
public class City {
    @Id
    @Column(name = "index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "\"Name\"")
    String name;
    double longitude;
    double latitude;

    public City(){id=null; name=null;}

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
    public City(String name, double longitude, double latitude){
        this.id=null;
        this.name= name;
        this.latitude=latitude;
        this.longitude= longitude;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(city.longitude, longitude) == 0 &&
                Double.compare(city.latitude, latitude) == 0 &&
                Objects.equals(getName(), city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), longitude, latitude);
    }

    @Override
    public String toString() {
        return "City{" +
                "id="+id+
                "name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String getName() {
        return name;
    }
}

