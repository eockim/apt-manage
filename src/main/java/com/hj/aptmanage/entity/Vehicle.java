package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Vehicle {

    private String kaptCode;
    private String kaptName;

    private Long fuelCost = 0L;
    private Long refairCost = 0L;
    private Long carInsurance = 0L;
    private Long carEtc = 0L;

    private Long total;

    public Long getTotal(){
        return this.fuelCost + this.refairCost + this.carInsurance + this.carEtc;
    }
}
