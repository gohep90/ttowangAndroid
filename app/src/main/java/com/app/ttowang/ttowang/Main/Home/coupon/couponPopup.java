package com.app.ttowang.ttowang.Main.Home.coupon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.app.ttowang.ttowang.R;

public class couponPopup extends Activity {

    TextView txt_businessId;

    String businessId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.coupon_popup);

        Intent i = getIntent();
        businessId = i.getExtras().getString("businessId");

        txt_businessId = (TextView)findViewById(R.id.txt_businessId);
        txt_businessId.setText(businessId);
    }
}
