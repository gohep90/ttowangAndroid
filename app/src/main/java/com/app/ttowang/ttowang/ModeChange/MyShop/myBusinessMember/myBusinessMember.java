package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessMember;

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
public class myBusinessMember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessmember_main);

        ListView listview ;
        myBusinessMemberAdapter adapter;

        // Adapter 생성
        adapter = new myBusinessMemberAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);


        adapter.addItem("박가나", "010-1234-5678") ;
        adapter.addItem("박다라", "010-1234-5678") ;
        adapter.addItem("구가나", "010-1234-5678") ;
        adapter.addItem("장가나", "010-1234-5678") ;
        adapter.addItem("이가나", "010-1234-5678") ;
        adapter.addItem("11", "010") ;
        adapter.addItem("22", "010") ;
        adapter.addItem("33", "010") ;
        adapter.addItem("44", "010") ;


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                myBusinessMemberItem item = (myBusinessMemberItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getBenefit() ;
                String descStr = item.getNumber() ;

            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(getApplicationContext(), Add.class);   //인텐트로 넘겨줄건데요~
                //intent.putExtra("currentViewPager", currentViewPager);
                //startActivity(intent);
                Toast.makeText(myBusinessMember.this,"추가 버튼", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
