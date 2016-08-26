package com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon;

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
public class ChangeCouponAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ChangeCouponItem> listViewItemList = new ArrayList<ChangeCouponItem>() ;

    // ListViewAdapter의 생성자
    public ChangeCouponAdapter() {

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
            convertView = inflater.inflate(R.layout.mybusinesscoupon_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView mybusineecouponname = (TextView) convertView.findViewById(R.id.mybusineecouponname) ;
        TextView mybusineecouponstampneed = (TextView) convertView.findViewById(R.id.mybusineecouponstampneed) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ChangeCouponItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mybusineecouponname.setText(listViewItem.getCouponName());
        mybusineecouponstampneed.setText(listViewItem.getStampNeed()+"개");


        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeCoupon.mContext,listViewItem.getCouponName(), Toast.LENGTH_SHORT).show();
            }
        });
/*
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(myBusinessCoupon.mContext,"롱클릭", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
    public void addItem(String CouponName, String StampNeed,String BusinessId,String CouponCode) {
        ChangeCouponItem item = new ChangeCouponItem();

        item.setCouponName(CouponName);
        item.setStampNeed(StampNeed);
        item.setBusinessId(BusinessId);
        item.setCouponCode(CouponCode);

        listViewItemList.add(item);
    }
}