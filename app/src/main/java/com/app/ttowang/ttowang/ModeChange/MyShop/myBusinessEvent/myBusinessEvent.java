package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessEvent;

import android.content.Context;
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
public class myBusinessEvent extends AppCompatActivity {
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessevent_main);
        mContext = this;
        ListView listview ;
        myBusinessEventAdapter adapter;

        // Adapter 생성
        adapter = new myBusinessEventAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);


        adapter.addItem("친") ;
        adapter.addItem("구") ;
        adapter.addItem("를") ;
        adapter.addItem("만") ;
        adapter.addItem("나") ;
        adapter.addItem("느") ;
        adapter.addItem("라") ;



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                myBusinessEventItem item = (myBusinessEventItem) parent.getItemAtPosition(position) ;

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
                Toast.makeText(myBusinessEvent.this,"추가 버튼", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
