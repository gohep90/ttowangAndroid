package com.app.ttowang.ttowang.Main.Home.HomeList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon.myBusinessCouponEdit;
import com.app.ttowang.ttowang.R;
import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Park on 2016-08-05.
 */
public class HomeListAdapter extends BaseAdapter {

    String ip;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<HomeListItem> listViewItemList = new ArrayList<HomeListItem>() ;

    String couponCode, couponName, businessId, stampNeed;
    int nowposition;
    // ListViewAdapter의 생성자
    public HomeListAdapter() {

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
        SharedPreferences sharedPreferences = HomeList.mContext.getSharedPreferences("sharedPreferences",HomeList.mContext.MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.homelist_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView mybusineename = (TextView) convertView.findViewById(R.id.mybusineename) ;
        TextView mybusineemap = (TextView) convertView.findViewById(R.id.mybusineemap) ;
        ImageView mybusinessimg = (ImageView) convertView.findViewById(R.id.mybusinessimg) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final HomeListItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mybusineename.setText(listViewItem.getBusinessName());
        mybusineemap.setText(listViewItem.getBusinessMap());
        Glide.with(HomeList.mContext).load("http://" + ip + ":8080/ttowang/image/"+listViewItem.getBusinessPhoto()).into(mybusinessimg);


        convertView.setOnClickListener(new View.OnClickListener(){  //수정
           @Override
           public void onClick(View v) {

               Toast.makeText(HomeList.mContext,listViewItem.getBusinessName(), Toast.LENGTH_SHORT).show();

           }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() { //삭제
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(myBusinessCoupon.mContext,"롱클릭", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return convertView;
    }

    public void clearItem(){
        listViewItemList.clear();
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
    public void addItem(String BusinessName, String BusinessMap, String BusinessPhoto) {
        HomeListItem item = new HomeListItem();

        item.setBusinessName(BusinessName);
        item.setBusinessMap(BusinessMap);
        item.setBusinessPhoto(BusinessPhoto);
        listViewItemList.add(item);

    }


}