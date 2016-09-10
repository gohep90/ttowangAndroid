package com.app.ttowang.ttowang.Main.Home.HomeList;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class HomeListItem {

    private String BusinessName,BusinessGroup;


    public void setBusinessName(String businessName) {
        BusinessName = businessName ;
    }
    public void setBusinessGroup(String businessGroup) {
        BusinessGroup = businessGroup ;
    }
    /*
    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }
    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }
    public void setStampNeed(String stampNeed) {
        StampNeed = stampNeed;
    }
*/


    public String getBusinessName() {
        return this.BusinessName;
    }
    public String getBusinessGroup() {
        return this.BusinessGroup;
    }
    /*
    public String getCouponCode() {
        return this.CouponCode ;
    }
    public String getBusinessId() {
        return this.BusinessId ;
    }
    public String getStampNeed() {
        return this.StampNeed ;
    }
*/

}
