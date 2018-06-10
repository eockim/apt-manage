package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Etc {

    private String kaptCode;
    private String kaptName;
    private Long careItemCost = 0L;
    private Long accountingCost = 0L;
    private Long hiddenCost = 0L;

    private Long total;

    public Long getTotal(){
        return this.careItemCost + this.accountingCost + this.hiddenCost;
    }
}
