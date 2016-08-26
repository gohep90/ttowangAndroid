package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessCoupon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinesscoupon_main);

        ListView listview ;
        myBusinessCouponAdapter adapter;

        // Adapter 생성
        adapter = new myBusinessCouponAdapter() ;

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
                myBusinessCouponItem item = (myBusinessCouponItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getBenefit() ;
                String descStr = item.getNumber() ;

            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                //intent.putExtra("currentViewPager", currentViewPager);
                startActivity(intent);
                //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
