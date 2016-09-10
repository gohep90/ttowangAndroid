package com.app.ttowang.ttowang.ModeChange.Stamp.AddStamp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class AddStamp extends Activity {

    String businessId;
    String ip;
    String userTel, userName;

    TextView text_addStampName, text_addStampTel;
    Button btn_add, btn_minus, btn_plus;
    EditText edt_stampnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addstamp);

        Intent i = getIntent();
        userTel = i.getExtras().getString("userTel");
        userName = i.getExtras().getString("userName");
        businessId = i.getExtras().getString("businessId");

        text_addStampName = (TextView)findViewById(R.id.text_addStampName);
        text_addStampTel = (TextView)findViewById(R.id.text_addStampTel);
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_minus = (Button)findViewById(R.id.btn_minus);
        btn_plus = (Button)findViewById(R.id.btn_plus);
        edt_stampnum = (EditText)findViewById(R.id.edt_stampnum);

        text_addStampName.setText(userName);
        text_addStampTel.setText(userTel);

        edt_stampnum.setText("1");
        edt_stampnum.setSelection(edt_stampnum.length());

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_add.setOnClickListener(ClickListener);
        btn_minus.setOnClickListener(ClickListener);
        btn_plus.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_add:
                    if(edt_stampnum.getText().toString() == "" || edt_stampnum.getText().length() == 0)
                        Toast.makeText(getApplicationContext(), "스탬프 갯수를 입력하세요", Toast.LENGTH_SHORT).show();
                    else {
                        //Toast.makeText(getApplicationContext(), "stampNum : " + edt_stampnum.getText().toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "userTel : " + userTel, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "businessId : " + businessId, Toast.LENGTH_SHORT).show();
                        AddStampAsyncTaskCall();
                        finish();
                    }
                    break;

                case R.id.btn_minus:
                    if(Integer.parseInt(edt_stampnum.getText().toString()) > 1)
                        edt_stampnum.setText(toString().valueOf(Integer.parseInt(edt_stampnum.getText().toString()) - 1));
                    edt_stampnum.setSelection(edt_stampnum.length());
                    break;

                case R.id.btn_plus:
                    edt_stampnum.setText(toString().valueOf(Integer.parseInt(edt_stampnum.getText().toString()) + 1));
                    edt_stampnum.setSelection(edt_stampnum.length());
                    break;
            }
        }
    };

    //스탬프 적립 스레드
    public void AddStampAsyncTaskCall(){
        new AddStampAsyncTask().execute();
    }

    public class AddStampAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("stampNum", edt_stampnum.getText().toString());
            prop.setProperty("userTel", userTel);
            prop.setProperty("businessId", businessId);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/addStamp.do");
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
        }
    }
}
