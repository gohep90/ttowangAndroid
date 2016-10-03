package com.app.ttowang.ttowang.Main.Home.HomeList;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class HomeListItem {

    private String BusinessName,BusinessMap,BusinessPhoto;
    private int BusinessNum;


    public void setBusinessName(String businessName) {
        BusinessName = businessName ;
    }
    public void setBusinessMap(String businessMap) {
        BusinessMap = businessMap ;
    }
    public void setBusinessPhoto(String businessPhoto) {
        BusinessPhoto = businessPhoto ;
    }
    public void setBusinessNum(int businessNum) {
        BusinessNum = businessNum ;
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
    public String getBusinessMap(){
        return this.BusinessMap;
    }
    public String getBusinessPhoto() {
        return this.BusinessPhoto;
    }
    public int getBusinessNum() {
        return this.BusinessNum;
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
