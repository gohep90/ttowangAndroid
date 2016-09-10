package com.app.ttowang.ttowang.ModeChange.Stamp.AddStamp;

public class AddStampListData{
    String tel;
    String name;
    String businessId;

    public AddStampListData(String tel, String name, String businessId){
        this.tel = tel;
        this.name = name;
        this.businessId = businessId;
    }

    public void setTel(String tel){
        this.tel = tel;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBusinessId(String businessId){
        this.businessId = businessId;
    }

    public String getTel() {
        return tel;
    }

    public String getName() { return name; }

    public String getBusinessId() { return businessId; }

}

