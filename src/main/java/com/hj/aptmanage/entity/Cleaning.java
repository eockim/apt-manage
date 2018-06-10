package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Cleaning {

    private String kaptCode;
    private String kaptName;
    private Long cleanCost = 0L;

    private Long total;

    public Long getTotal(){
        return this.cleanCost;
    }
}
