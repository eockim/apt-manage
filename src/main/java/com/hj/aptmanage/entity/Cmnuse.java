package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class Cmnuse {

    private String kaptCode;
    private String kaptName;
    private Long officeSupply = 0L;
    private Long bookSupply = 0L;
    private Long transportcost = 0L;

    private Long totalCost;

    public Long getTotalCost(){
        return this.officeSupply + this.bookSupply + this.transportcost;
    }

}
