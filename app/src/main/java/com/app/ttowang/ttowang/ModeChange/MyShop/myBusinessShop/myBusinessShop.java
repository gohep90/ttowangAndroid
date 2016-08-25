package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop;

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
public class myBusinessShop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessshop_main);

        ListView listview ;
        myBusinessShopAdapter adapter;

        // Adapter 생성
        adapter = new myBusinessShopAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);


        adapter.addItem("갤럭시") ;
        adapter.addItem("아이폰") ;
        adapter.addItem("블랙베리") ;
        adapter.addItem("HTC") ;
        adapter.addItem("헬G") ;
        adapter.addItem("화웨이") ;
        adapter.addItem("샤오미") ;



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                myBusinessShopItem item = (myBusinessShopItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getBenefit() ;

            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(getApplicationContext(), Add.class);   //인텐트로 넘겨줄건데요~
                //intent.putExtra("currentViewPager", currentViewPager);
                //startActivity(intent);
                Toast.makeText(myBusinessShop.this,"추가 버튼", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
