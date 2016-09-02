package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessMember;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessMemberItem {

    private String BusinessId, UserGender, UserTel ,UserName , UserId;

    public void setBusinessId(String businessId) {
        BusinessId = businessId ;
    }
    public void setUserGender(String userGender) {
        UserGender = userGender;
    }
    public void setUserTel(String userTel) {
        UserTel = userTel ;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public void setUserId(String userId) {
        UserId = userId ;
    }


    public String getBusinessId() {
        return this.BusinessId;
    }
    public String getUserGender() {
        return this.UserGender ;
    }
    public String getUserTel() {
        return this.UserTel;
    }
    public String getUserName() {
        return this.UserName ;
    }
    public String getUserId() {
        return this.UserId;
    }

}
