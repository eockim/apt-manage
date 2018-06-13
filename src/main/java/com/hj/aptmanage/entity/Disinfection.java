package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Disinfection {

    private String kaptCode;
    private String kaptName;
    private Long disinfCost = 0L;
    private Long total;

    public Long getTotal(){
        return this.disinfCost;
    }
}
