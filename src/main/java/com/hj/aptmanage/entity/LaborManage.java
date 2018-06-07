package com.hj.aptmanage.entity;

import lombok.Data;

@Data
public class LaborManage{

    private  Long accidentPremium = 0L;
    private  Long bonus = 0L;
    private  Long employPremium = 0L;
    private  Long healthPremium = 0L;
    private  String kaptCode = "";
    private  String kaptName = "";
    private  Long nationalPension = 0L;
    private  Long pay = 0L;
    private  Long pension = 0L;
    private  Long sundryCost = 0L;
    private  Long welfareBenefit = 0L;

    private Long costTotal;

    public Long getCostTotal(){
//        return Long.parseLong(this.accidentPremium) + Long.parseLong(this.bonus) + Long.parseLong(this.employPremium)
//                + Long.parseLong(this.healthPremium)+ Long.parseLong(this.nationalPension)+ Long.parseLong(this.pay)
//                + Long.parseLong(this.pension)+ Long.parseLong(this.sundryCost)+ Long.parseLong(this.welfareBenefit);
        return this.accidentPremium + this.bonus + this.employPremium + this.healthPremium
                + this.nationalPension + this.pay + this.pension + this.sundryCost + this.welfareBenefit;
    }
}
