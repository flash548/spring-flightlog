package com.galvanize.flightlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "flights")
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String depart_time;
    private String arrive_time;
    private String depart_from;
    private String arrive_to;
    private Integer tail_number;
    private Integer pilot;
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepart_time() {
        return depart_time;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getDepart_from() {
        return depart_from;
    }

    public void setDepart_from(String depart_from) {
        this.depart_from = depart_from;
    }

    public String getArrive_to() {
        return arrive_to;
    }

    public void setArrive_to(String arrive_to) {
        this.arrive_to = arrive_to;
    }

    public Integer getTail_number() {
        return tail_number;
    }

    public void setTail_number(Integer tail_number) {
        this.tail_number = tail_number;
    }

    public Integer getPilot() {
        return pilot;
    }

    public void setPilot(Integer pilot) {
        this.pilot = pilot;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
