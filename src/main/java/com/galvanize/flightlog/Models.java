package com.galvanize.flightlog;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class Models {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tail_number;
    private String model;
    private Boolean maint_good;

    public Long getTail_number() {
        return tail_number;
    }

    public void setTail_number(Long tail_number) {
        this.tail_number = tail_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getMaint_good() {
        return maint_good;
    }

    public void setMaint_good(Boolean maint_good) {
        this.maint_good = maint_good;
    }
}
