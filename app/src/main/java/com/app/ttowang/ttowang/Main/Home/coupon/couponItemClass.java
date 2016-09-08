package com.app.ttowang.ttowang.Main.Home.coupon;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class couponItemClass {


    private String CouponNum, CouponUse, CouponName ,BusinessId , CouponCode;

    public void setCouponNum(String couponNum) {
        CouponNum = couponNum ;
    }
    public void setCouponUse(String couponUse) {
        CouponUse = couponUse;
    }
    public void setCouponName(String couponName) {
        CouponName = couponName ;
    }
    /*
    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }
    public void setCouponCode(String couponCode) {
        CouponCode = couponCode ;
    }
*/

    public String getCouponNum() {
        return this.CouponNum;
    }
    public String getCouponUse() {
        return this.CouponUse ;
    }
    public String getCouponName() {
        return this.CouponName;
    }
    /*
    public String getBusinessId() {
        return this.BusinessId ;
    }
    public String getCouponCode() {
        return this.CouponCode;
    }
*/

}
