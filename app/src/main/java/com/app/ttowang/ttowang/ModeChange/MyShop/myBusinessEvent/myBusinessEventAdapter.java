package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.R;

import java.util.ArrayList;

/**
 * Created by Park on 2016-08-05.
 */
public class myBusinessEventAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<myBusinessEventItem> listViewItemList = new ArrayList<myBusinessEventItem>() ;

    // ListViewAdapter의 생성자
    public myBusinessEventAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mybusinessevent_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView mybusineecouponbenefit = (TextView) convertView.findViewById(R.id.mybusineecouponbenefit) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        myBusinessEventItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mybusineecouponbenefit.setText(listViewItem.getBenefit());


        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(myBusinessEvent.mContext,"원클릭", Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(myBusinessEvent.mContext,"롱클릭", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

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
    public void addItem(String benefit) {
        myBusinessEventItem item = new myBusinessEventItem();

        item.setBenefit(benefit);

        listViewItemList.add(item);
    }
}