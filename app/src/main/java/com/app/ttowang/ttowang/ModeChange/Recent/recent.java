package com.app.ttowang.ttowang.ModeChange.Recent;

/**
 * Created by jangjieun on 2016. 8. 19..
 */

public class recent {
    String businessId,userId,seq;

    recent(String seq, String businessId, String userId) {
        this.seq = seq;
        this.businessId = businessId;
        this.userId = userId;
    }

    public String getBusinessId() {return businessId;}

    public String getUserId() {return userId;}

    public String getSeq() {
        return seq;
    }

    /*
    String seq,userName,stampDate,stampTime;

    public String getSeq() {
        return seq;
    }

    public String getUserName() {
        return userName;
    }

    public String getStampDate() {
        return stampDate;
    }

    public String getStampTime() {
        return stampTime;
    }
    */

}
