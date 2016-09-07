package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon.ChangeCouponAdapter;
import com.app.ttowang.ttowang.Main.MainActivity;
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
public class myBusinessCouponAdd extends Activity {

    String businessId="";
    String ip;
    RelativeLayout ChangeCouponRelative;

    Button CouponAdd;
    ChangeCouponAdapter adapter;

    EditText couponNameEditText, stampNeedEditText;

    String couponCode, couponName, stampNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mybusinesscoupon_add);
        CouponAdd = (Button)findViewById(R.id.CouponAdd);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        Intent i = getIntent();
        businessId = i.getExtras().getString("businessId");

        //CouponAsyncTaskCall();//쿠폰 리스트 불러오기
        couponNameEditText = (EditText)findViewById(R.id.couponNameEditText);
        stampNeedEditText = (EditText)findViewById(R.id.stampNeedEditText);

        CouponAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CouponAddAsyncTask().execute();
                //Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                //intent.putExtra("currentViewPager", currentViewPager);
                //startActivity(intent);
                //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }




    public class CouponAddAsyncTask extends AsyncTask<String,Integer,String> {

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


           // couponCode = businessId + (System.currentTimeMillis()%10000);
            couponName = String.valueOf(couponNameEditText.getText());
            stampNeed = String.valueOf(stampNeedEditText.getText());
            prop.setProperty("businessId",businessId);
            prop.setProperty("couponName", couponName);
            prop.setProperty("stampNeed", stampNeed);
            //Log.i("couponAdd - ",businessId + " " + businessId + String.valueOf(couponNameEditText.getText()) + " " + String.valueOf(couponNameEditText.getText()) + " " +String.valueOf(stampNeedEditText.getText()));
            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/couponAdd.do");
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


            if(!result.equals("")){
                //myBusinessCoupon.adapter.addItem( couponName, stampNeed,businessId,couponCode) ;
                //myBusinessCoupon.CouponRefresh();
                //myBusinessCoupon.adapter.notifyDataSetChanged();
                Log.i("coupon 어댑터 ","새로 고친다");
            }else {
                Toast.makeText(myBusinessCoupon.mContext,"추가 실패", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
