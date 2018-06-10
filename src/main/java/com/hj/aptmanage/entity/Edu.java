package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Edu {

    private String kaptCode;
    private String kaptName;
    private Long eduCost = 0L;
    private Long total;


    public Long getTotal(){
        return this.eduCost;
    }

}
