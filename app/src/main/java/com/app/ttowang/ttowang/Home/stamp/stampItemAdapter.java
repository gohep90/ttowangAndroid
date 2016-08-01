package com.app.ttowang.ttowang.Home.stamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.app.ttowang.ttowang.Home.home;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class stampItemAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<stampItemClass> listViewItemList = new ArrayList<stampItemClass>() ;
    private Button stamp0,stamp1,stamp2,stamp3,stamp4,stamp5,stamp6,stamp7,stamp8,stamp9;

    // ListViewAdapter의 생성자
    public stampItemAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.stampitem, parent, false);
        }
        stamp0 = (Button)convertView.findViewById(R.id.stamp0);
        stamp1 = (Button)convertView.findViewById(R.id.stamp1);
        stamp2 = (Button)convertView.findViewById(R.id.stamp2);
        stamp3 = (Button)convertView.findViewById(R.id.stamp3);
        stamp4 = (Button)convertView.findViewById(R.id.stamp4);
        stamp5 = (Button)convertView.findViewById(R.id.stamp5);
        stamp6 = (Button)convertView.findViewById(R.id.stamp6);
        stamp7 = (Button)convertView.findViewById(R.id.stamp7);
        stamp8 = (Button)convertView.findViewById(R.id.stamp8);
        stamp9 = (Button)convertView.findViewById(R.id.stamp9);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        stampItemClass stampItemClass = listViewItemList.get(position);
        stamp0.setText(position+"0");
        stamp1.setText(position+"1");
        stamp2.setText(position+"2");
        stamp3.setText(position+"3");
        stamp4.setText(position+"4");
        stamp5.setText(position+"5");
        stamp6.setText(position+"6");
        stamp7.setText(position+"7");
        stamp8.setText(position+"8");
        stamp9.setText(position+"9");

        stamp0.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"0", Toast.LENGTH_SHORT).show();
            }
        });
        stamp1.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"1", Toast.LENGTH_SHORT).show();
            }
        });
        stamp2.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"2", Toast.LENGTH_SHORT).show();
            }
        });
        stamp3.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"3", Toast.LENGTH_SHORT).show();
            }
        });
        stamp4.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"4", Toast.LENGTH_SHORT).show();
            }
        });
        stamp5.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"5", Toast.LENGTH_SHORT).show();
            }
        });
        stamp6.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"6", Toast.LENGTH_SHORT).show();
            }
        });
        stamp7.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"7", Toast.LENGTH_SHORT).show();
            }
        });
        stamp8.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"8", Toast.LENGTH_SHORT).show();
            }
        });
        stamp9.setOnClickListener(new View.OnClickListener() {        //경로보기의 버튼
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.mContext, position+"9", Toast.LENGTH_SHORT).show();
            }
        });



        // 아이템 내 각 위젯에 데이터 반영
        /*
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());
        */
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.

    public void clearItem(){
        listViewItemList.clear();
    }

    public void addItem() {
        stampItemClass item = new stampItemClass();
        listViewItemList.add(item);
    }

}
