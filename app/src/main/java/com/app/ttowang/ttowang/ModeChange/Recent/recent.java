package com.app.ttowang.ttowang.ModeChange.Recent;

/**
 * Created by jangjieun on 2016. 8. 19..
 */

public class recent {
    String seq,userName,stampDate,userId,couponUsing,stampNum,stampTime;

    recent(String seq, String userName, String stampDate,String stampTime) {
        this.seq = seq;
        this.userName = userName;
        this.stampDate = stampDate;
        this.stampTime = stampTime;
    }

    public String getSeq() {
        return seq;
    }

    public String getUserName() {return userName;}

    public String getStampDate() {return stampDate;}

    public String getUserId() {return userId;}

    public String getCouponUsing() {return couponUsing;}

    public String getStampNum() {return stampNum;}

    public String getStampTime() {return stampTime;
    }
}
