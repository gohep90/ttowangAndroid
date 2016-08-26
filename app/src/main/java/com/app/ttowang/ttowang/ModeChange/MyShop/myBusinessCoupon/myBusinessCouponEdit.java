package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon.ChangeCouponAdapter;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;


/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessCouponEdit extends Activity {

    String businessId= ChangeModeMain.businessId;
    String ip= MainActivity.ip;
    RelativeLayout ChangeCouponRelative;

    Button CouponAdd;
    ChangeCouponAdapter adapter;

    EditText couponName, stampNeed;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mybusinesscoupon_add);
        CouponAdd = (Button)findViewById(R.id.CouponAdd);
        i = getIntent();


        //CouponAsyncTaskCall();//쿠폰 리스트 불러오기
        couponName = (EditText)findViewById(R.id.couponName);
        stampNeed = (EditText)findViewById(R.id.stampNeed);

        couponName.setText(i.getExtras().getString("CouponName"));
        stampNeed.setText(i.getExtras().getString("StampNeed"));



        couponName.setSelection(couponName.getText().length()); //커서 맨뒤로 보내기요

        CouponAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CouponEditAsyncTask().execute();
                //Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                //intent.putExtra("currentViewPager", currentViewPager);
                //startActivity(intent);
                //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                myBusinessCoupon.adapter.notifyDataSetChanged();
                finish();
            }
        });

    }




    public class CouponEditAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
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

            prop.setProperty("businessId",i.getExtras().getString("BusinessId"));
            prop.setProperty("couponCode",i.getExtras().getString("CouponCode"));
            prop.setProperty("couponName", String.valueOf(couponName.getText()));
            prop.setProperty("stampNeed", String.valueOf(stampNeed.getText()));
            Log.i("couponAdd - ",businessId + " " + businessId + String.valueOf(couponName.getText()) + " " + String.valueOf(couponName.getText()) + " " +String.valueOf(stampNeed.getText()));
            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + MainActivity.ip + ":8080/ttowang/couponUpdate.do");
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

        }
    }
}
