package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessMember;

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

import com.app.ttowang.ttowang.Main.MainActivity;
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
public class myBusinessMember extends AppCompatActivity {
    public static Context mContext;
    int userId;
    static String ip;
    static myBusinessMemberAdapter adapter;
    Spinner spinner;
    KeyValueArrayAdapter spn_adapter;
    static ArrayList<String> spinnerKeys = new ArrayList<String>();
    static ArrayList<String> spinnerValues = new ArrayList<String>();
    static String businessId;
    int nowposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessmember_main);
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
                nowposition = position;
                new searchAllMyStaffAsyncTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ListView listview ;


        // Adapter 생성
        adapter = new myBusinessMemberAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                myBusinessMemberItem item = (myBusinessMemberItem) parent.getItemAtPosition(position) ;

            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), myBusinessStaffAdd.class);   //인텐트로 넘겨줄건데요~
                intent.putExtra("userId", userId);
                intent.putExtra("position", nowposition);

                startActivity(intent);
                //Toast.makeText(myBusinessMember.this,"추가 버튼", Toast.LENGTH_SHORT).show();

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
                spinnerValues.clear();
                spinnerKeys.clear();
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


    public static class searchAllMyStaffAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
            myBusinessMemberAdapter.listViewItemList.clear();
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
                url=new URL("http://" + ip + ":8080/ttowang/searchAllMyStaff.do");
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
                String result1 = json.getString("result");
                if(result1.equals("직원 있음")){

                    JSONArray jArr =json.getJSONArray("List");

                    for (int i = 0; i < jArr.length(); i++ ) {

                        json = jArr.getJSONObject(i);
                        adapter.addItem( json.getString("businessId"), json.getString("userGender"),json.getString("userTel"),json.getString("userName"),json.getString("userId")) ;

                    }
                    adapter.notifyDataSetChanged();
                }else if(result1.equals("직원 없음")){
                    Toast.makeText(MainActivity.mContext, "직원이 없습니다." , Toast.LENGTH_SHORT).show();
                }else if(result1.equals("직원찾기 실패")){
                    Toast.makeText(MainActivity.mContext, "직원 목록을 가져오지 못했습니다." , Toast.LENGTH_SHORT).show();
                }

                /*
                JSONArray jArr =json.getJSONArray("couponList");
                if(jArr.length()==0){

                    Toast.makeText(.this, "등록된 쿠폰이 없습니다.", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();     //리스트

                    return;
                }
                for (int i = 0; i < jArr.length(); i++ ) {

                    json = jArr.getJSONObject(i);
                    adapter.addItem( json.getString("couponName"), json.getString("stampNeed"),json.getString("businessId"),json.getString("couponCode")) ;

                }
                */

                //adapter.notifyDataSetChanged();     //리스트

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    final public static void staffRefresh(){

        new searchAllMyStaffAsyncTask().execute();

        Log.i("직원 - ","스템프 리프레쉬 한다");

    }
}
