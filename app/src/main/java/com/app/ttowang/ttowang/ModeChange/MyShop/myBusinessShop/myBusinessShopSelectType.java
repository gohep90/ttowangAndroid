package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon.ChangeCouponAdapter;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon.myBusinessCoupon;
import com.app.ttowang.ttowang.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;


/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessShopSelectType extends Activity {

    String ip= MainActivity.ip;
    RelativeLayout ChangeCouponRelative;

    Button store,individual;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mybusinessshop_selecttype);
        store = (Button)findViewById(R.id.store);
        individual = (Button)findViewById(R.id.individual);
        Intent i = getIntent();
        userId = i.getExtras().getString("userId");

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new CouponAddAsyncTask().execute();
                Intent intent = new Intent(getApplicationContext(), myBusinessStoreAdd.class);   //인텐트로 넘겨줄건데요~
                intent.putExtra("userId", userId);
                startActivity(intent);
                //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), myBusinessIndividualAdd.class);   //인텐트로 넘겨줄건데요~
                intent.putExtra("userId", userId);
                startActivity(intent);
                //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
