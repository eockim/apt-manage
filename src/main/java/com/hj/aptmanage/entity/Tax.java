package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Tax {

    private String kaptCode;
    private String kaptName;

    private Long elecCost = 0L;
    private Long telCost = 0L;
    private Long postageCost = 0L;
    private Long taxrestCost = 0L;

    private Long total;

    public Long getTotal(){
        return this.elecCost + this.telCost + this.postageCost + this.taxrestCost;
    }
}
