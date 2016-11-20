package com.app.ttowang.ttowang.ModeChange.Recent;

/**
 * Created by jangjieun on 2016. 8. 19..
 */

public class recent {
    String seq;
    String userName;
    String stampDate;
    String userId;
    String couponUsing;
    String stampNum;
    String stampTime;
    String userTel;

    recent(String seq, String userName, String stampDate,String stampTime,String userTel) {
        this.seq = seq;
        this.userName = userName;
        this.stampDate = stampDate;
        this.stampTime = stampTime;
        this.userTel = userTel;
    }

    public String getSeq() {return seq;}

    public String getUserName() {return userName;}

    public String getStampDate() {return stampDate;}

    public String getUserId() {return userId;}

    public String getCouponUsing() {return couponUsing;}

    public String getStampNum() {return stampNum;}

    public String getStampTime() {return stampTime; }

    public String getUserTel() { return userTel; }
}
