package com.app.ttowang.ttowang.Main.Home.stamp.GiftStamp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;

import org.json.JSONException;
import org.json.JSONObject;

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
public class GiftStamp extends Activity {

    String ip= MainActivity.ip;
    String businessId ="";
    static String userId = MainActivity.user;

    EditText edt_tel;
    Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.giftstamp);

        Intent i = getIntent();
        businessId = i.getExtras().getString("businessId");

        edt_tel = (EditText)findViewById(R.id.edt_tel);
        btn_send = (Button)findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //giftStampAsyncTaskCall(); 선물하기  개수는 어케????
            }
        });

    }

    //스탬프 쿠폰전환
    public void giftStampAsyncTaskCall(){
        new giftStampAsyncTask().execute();
    }

    public class giftStampAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("userId", userId);
            prop.setProperty("businessId", businessId);
            prop.setProperty("userTel", edt_tel.getText().toString().trim());

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/giftStamp.do");
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
            try{
                JSONObject json=new JSONObject(result);
                if(json.getString("result").equals("필요한 스탬프가 부족합니다.")){
                    Toast.makeText(MainActivity.mContext,json.getString("result") , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.mContext,json.getString("result") , Toast.LENGTH_SHORT).show();
                    home.refresh();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

}
