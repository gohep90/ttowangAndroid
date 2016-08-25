package com.app.ttowang.ttowang.Main.Home.coupon.ChangeCoupon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.R;


/**
 * Created by srpgs2 on 2016-08-25.
 */
public class ChangeCoupon extends Activity {
    String businessId="";
    RelativeLayout ChangeCouponRelative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.changecoupon_main);
        //setTheme(R.style.AppDialogTheme);
        ListView listview ;
        ChangeCouponAdapter adapter;
        ChangeCouponRelative = (RelativeLayout)findViewById(R.id.ChangeCouponRelative);
        // Adapter 생성
        adapter = new ChangeCouponAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);


        adapter.addItem("커피 한잔", "5개") ;
        adapter.addItem("피자 한조각", "6개") ;
        adapter.addItem("후라이드치느님", "20개") ;
        adapter.addItem("앙념치느님", "20개") ;
        adapter.addItem("간장치느님", "20개") ;
        adapter.addItem("파치느님", "20개") ;
        adapter.addItem("새우치느님", "20개") ;
        adapter.addItem("순살치느님", "20개") ;
        adapter.addItem("동네치느님", "20개") ;



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ChangeCouponItem item = (ChangeCouponItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getBenefit() ;
                String descStr = item.getNumber() ;

            }
        }) ;

        Intent i = getIntent();
        businessId = i.getExtras().getString("businessId");

    }
}
