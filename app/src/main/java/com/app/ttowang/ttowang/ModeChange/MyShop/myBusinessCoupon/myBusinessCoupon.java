package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon;

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

import com.app.ttowang.ttowang.ModeChange.MyShop.KeyValueArrayAdapter;
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
public class myBusinessCoupon extends AppCompatActivity {

    static String businessId="" ;
    static myBusinessCouponAdapter adapter;

    int userId;
    static String ip;
    ArrayList<String> spinnerKeys = new ArrayList<String>();
    ArrayList<String> spinnerValues = new ArrayList<String>();
    KeyValueArrayAdapter spn_adapter;


    Spinner spinner;
    public static ListView couponlistview ;

    static int Couponadd = 0;
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinesscoupon_main);
        mContext = this;

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");
        userId = sharedPreferences.getInt("userId", 0);

        spinner = (Spinner)findViewById(R.id.spinner);

        spn_adapter = new KeyValueArrayAdapter(this,R.layout.spinner_item);
        spn_adapter.setDropDownViewResource(R.layout.spinner_item);


        businessListAsyncTaskCall(); //businessList (spinner)

        //스피너 선택 리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) parent.getAdapter();
                //Toast.makeText(myBusinessCoupon.this, adapter.getEntry(position), Toast.LENGTH_SHORT).show();
                businessId=adapter.getEntry(position);

                new CouponDownAsyncTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        // Adapter 생성
        adapter = new myBusinessCouponAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        couponlistview = (ListView) findViewById(R.id.listView);
        couponlistview.setAdapter(adapter);

        couponlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                myBusinessCouponItem item = (myBusinessCouponItem) parent.getItemAtPosition(position) ;


            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("현재 시간",System.currentTimeMillis() + "");
                Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                intent.putExtra("businessId", businessId);
                startActivity(intent);
                //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();

            }
        });
    }


    //비지니스 List (스피너)
    public void businessListAsyncTaskCall(){
        new businessListAsyncTask().execute();
    }

    public class businessListAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
            spn_adapter.clear();
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            String result =recvList();
            return result;
        }

        public String encodeString(Properties params) {  //한글 encoding??
            StringBuffer sb = new StringBuffer(256);
            Enumeration names = params.propertyNames();

            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = params.getProperty(name);
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

                if (names.hasMoreElements()) sb.append("&");
            }
            return sb.toString();
        }

        private String recvList() { //데이터 보내고 받아오기!!

            HttpURLConnection urlConnection=null;
            URL url =null;
            DataOutputStream out=null;
            BufferedInputStream buf=null;
            BufferedReader bufreader=null;

            Properties prop = new Properties();

            prop.setProperty("userId", String.valueOf(userId));

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/spinnerList.do");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                out = new DataOutputStream(urlConnection.getOutputStream());

                out.writeBytes(encodedString);

                out.flush();    //서버로 버퍼의 내용 전송

                buf = new BufferedInputStream(urlConnection.getInputStream());
                bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));

                String line=null;
                String result="";

                while((line=bufreader.readLine())!=null){
                    result += line;
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }finally{
                urlConnection.disconnect();  //URL 연결해제
            }
        }
        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            Log.i("서버에서 받은 전체 내용 : ", result);
            try{
                JSONObject json=new JSONObject(result);
                JSONArray jArr =json.getJSONArray("spinnerList");

                for (int i = 0; i < jArr.length(); i++ ) {
                    json = jArr.getJSONObject(i);

                    spinnerValues.add(json.getString("businessName"));
                    spinnerKeys.add(json.getString("businessId"));

                }
                //spn_adapter.notifyDataSetChanged();     //리스트

                spn_adapter.setEntries(spinnerKeys.toArray(new String[spinnerKeys.size()]));
                spn_adapter.setEntryValues(spinnerValues.toArray(new String[spinnerValues.size()]));

                spinner.setAdapter(spn_adapter);

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }






    public static class CouponDownAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
            myBusinessCouponAdapter.listViewItemList.clear();
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            String result =recvList();
            return result;
        }

        public String encodeString(Properties params) {  //한글 encoding??
            StringBuffer sb = new StringBuffer(256);
            Enumeration names = params.propertyNames();

            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = params.getProperty(name);
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

                if (names.hasMoreElements()) sb.append("&");
            }
            return sb.toString();
        }

        private String recvList() { //데이터 보내고 받아오기!!

            HttpURLConnection urlConnection=null;
            URL url =null;
            DataOutputStream out=null;
            BufferedInputStream buf=null;
            BufferedReader bufreader=null;

            Properties prop = new Properties();

            prop.setProperty("businessId",businessId);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/couponList.do");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                out = new DataOutputStream(urlConnection.getOutputStream());

                out.writeBytes(encodedString);

                out.flush();    //서버로 버퍼의 내용 전송

                buf = new BufferedInputStream(urlConnection.getInputStream());
                bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));

                String line=null;
                String result="";

                while((line=bufreader.readLine())!=null){
                    result += line;
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }finally{
                urlConnection.disconnect();  //URL 연결해제
            }
        }
        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            Log.i("서버에서 받은 전체 내용 : ", result);

            try{
                JSONObject json=new JSONObject(result);
                JSONArray jArr =json.getJSONArray("couponList");
                adapter.clearItem();

                if(jArr.length()==0){
                    Toast.makeText(myBusinessCoupon.mContext, "등록된 쿠폰이 없습니다.", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();     //리스트
                    return;
                }
                for (int i = 0; i < jArr.length(); i++ ) {
                    json = jArr.getJSONObject(i);
                    adapter.addItem( json.getString("couponName"), json.getString("stampNeed"),json.getString("businessId"),json.getString("couponCode")) ;

                }

                adapter.notifyDataSetChanged();     //리스트
                if(Couponadd==1){
                    couponlistview.setSelection(jArr.length());
                    Couponadd = 0;
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    final public static void stampRefresh(){
        Couponadd = 1;
        new CouponDownAsyncTask().execute();
        Log.i("내 쿠폰 - ","쿠폰 리프레쉬 한다");


    }
}
