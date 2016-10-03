package com.app.ttowang.ttowang.Main.Home.HomeList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.ModeChange.MyShop.KeyValueArrayAdapter;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon.myBusinessCouponAdd;
import com.app.ttowang.ttowang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by srpgs2 on 2016-08-25.
 */
public class HomeList extends AppCompatActivity {


    static HomeListAdapter adapter;

    int userId;
    static String ip;

    Spinner spinner;
    public static ListView homelistView ;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelist_main);
        mContext = this;


        // Adapter 생성
        adapter = new HomeListAdapter() ;
        adapter.clearItem();
        if(home.hometotlasu != 0) {
            for (int i = 0; i < home.hometotlasu; i++) {
                adapter.addItem(home.myAllBusiness.get(i).get(1), home.myAllBusiness.get(i).get(2), home.myAllBusiness.get(i).get(3));
            }
        }

        // 리스트뷰 참조 및 Adapter달기
        homelistView = (ListView) findViewById(R.id.homelistView);
        homelistView.setAdapter(adapter);



        homelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                HomeListItem item = (HomeListItem) parent.getItemAtPosition(position) ;
            }
        }) ;

    }

    final public static void stampRefresh(){
        Log.i("내 쿠폰 - ","쿠폰 리프레쉬 한다");
    }
}
