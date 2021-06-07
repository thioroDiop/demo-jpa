package org.bernardhugueney.demojpa.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Measure {

    @Id
    @GeneratedValue
    private Long id;

    private String type;


    private String unit;

    @Column(precision = 6, scale = 2)
    private double value;

    private LocalDateTime measureDate;

    public Long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getMeasureDate() {
        return measureDate;
    }

}