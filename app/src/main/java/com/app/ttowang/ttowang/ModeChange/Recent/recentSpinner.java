package com.app.ttowang.ttowang.ModeChange.Recent;

/**
 * Created by jangjieun on 2016. 9. 12..
 */
public class recentSpinner {
    public String businessId = "";
    public String businessName = "";

    recentSpinner(String businessId,String businessName) {
        this.businessId = businessId;
        this.businessName = businessName;
    }

    public String getBusinessId() {return businessId;}

    public String getBusinessName() {return businessName;}

}
