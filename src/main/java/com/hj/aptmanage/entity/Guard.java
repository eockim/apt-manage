package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Guard {

    private String kaptCode;
    private String kaptName;

    private Long guardCost =0L;
    private Long total;

    public Long getTotal(){
        return this.guardCost;
    }
}
