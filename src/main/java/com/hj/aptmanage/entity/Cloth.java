package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Cloth {

    private String kaptCode;
    private String kaptName;
    private Long clothesCost = 0L;

    private Long total;

    public Long getTotal(){
        return this.clothesCost;
    }
}
