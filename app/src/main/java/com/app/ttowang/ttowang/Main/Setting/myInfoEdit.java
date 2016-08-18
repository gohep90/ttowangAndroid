package com.app.ttowang.ttowang.Main.Setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.home;
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


public class myInfoEdit extends AppCompatActivity {
    //EditText 여러개 선언
    TextView change_text_tel;
    EditText change_edt_name, change_edt_birth, change_edt_email;
    Button change_btn_m, change_btn_w, change_btn_edit;

    String userTel, userName, userBirth, userGender, userEmail;
    int userCode = 0;

    String stringGender;
    int intGender = 0;//남0, 여1

    String ip = "";
    String encodedString="";

    Context context = MainActivity.mContext;

    //다른곳에 선언되어있는 SharedPreferences 가져오기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfoedit);//내정보수정 창 띄워주고

        change_text_tel = (TextView) findViewById(R.id.change_text_tel);
        change_edt_name = (EditText) findViewById(R.id.change_edt_name);
        change_edt_birth = (EditText) findViewById(R.id.change_edt_birth);
        change_edt_email = (EditText) findViewById(R.id.change_edt_email);
        change_btn_edit = (Button) findViewById(R.id.change_btn_edit);
        change_btn_m = (Button) findViewById(R.id.change_btn_m);
        change_btn_w = (Button) findViewById(R.id.change_btn_w);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");
        userTel = sharedPreferences.getString("userTel", "");
        userName = sharedPreferences.getString("userName", "");
        userBirth = sharedPreferences.getString("userBirth", "");
        userGender = sharedPreferences.getString("userGender", "");
        userEmail = sharedPreferences.getString("userEmail", "");

        change_text_tel.setText(userTel);
        change_edt_name.setText(userName);
        change_edt_birth.setText(userBirth);
        change_edt_email.setText(userEmail);

        if (userGender.equals("남")) {
            intGender = 0;
            stringGender = "남";
        }
        else {
            intGender = 1;
            stringGender = "여";
        }

        if(intGender == 0){
            change_btn_m.setBackgroundResource(R.drawable.btn_gender);
            change_btn_w.setBackgroundResource(R.drawable.btn_ngender);
            change_btn_m.setTextColor(getResources().getColorStateList(R.color.m));
            change_btn_w.setTextColor(getResources().getColorStateList(R.color.w));
        }

        else{
            change_btn_m.setBackgroundResource(R.drawable.btn_ngender);
            change_btn_w.setBackgroundResource(R.drawable.btn_gender);
            change_btn_m.setTextColor(getResources().getColorStateList(R.color.w));
            change_btn_w.setTextColor(getResources().getColorStateList(R.color.m));
        }

        buttonClickListener();
    }

    private void buttonClickListener() {
        change_btn_m.setOnClickListener(ClickListener);
        change_btn_w.setOnClickListener(ClickListener);
        change_btn_edit.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.change_btn_edit:
                    MyInfoEditAsyncTaskCall();

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);//쉐어드객체 다시 얻기
                    SharedPreferences.Editor Edit= sharedPreferences.edit();
                    Edit.putString("userName", change_edt_name.getText().toString());
                    Edit.putString("userBirth", change_edt_birth.getText().toString());
                    Edit.putString("userEmail", change_edt_email.getText().toString());
                    Edit.putString("userGender", stringGender);
                    Edit.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    //finish();
                    break;

                case R.id.change_btn_m:
                    change_btn_m.setBackgroundResource(R.drawable.btn_gender);
                    change_btn_w.setBackgroundResource(R.drawable.btn_ngender);
                    change_btn_m.setTextColor(getResources().getColorStateList(R.color.m));
                    change_btn_w.setTextColor(getResources().getColorStateList(R.color.w));
                    intGender = 0;
                    stringGender = "남";
                    break;

                case R.id.change_btn_w:
                    change_btn_m.setBackgroundResource(R.drawable.btn_ngender);
                    change_btn_w.setBackgroundResource(R.drawable.btn_gender);
                    change_btn_m.setTextColor(getResources().getColorStateList(R.color.w));
                    change_btn_w.setTextColor(getResources().getColorStateList(R.color.m));
                    intGender = 1;
                    stringGender = "여";
                    break;
            }
        }
    };

    public void MyInfoEditAsyncTaskCall(){
        new MyInfoEditAsyncTask().execute();
    }

    public class MyInfoEditAsyncTask extends AsyncTask<String,Integer,String> {

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

        private String recvList() { //데이터 보내기!!

            HttpURLConnection urlConnection=null;
            URL url =null;
            DataOutputStream out=null;
            BufferedInputStream buf=null;
            BufferedReader bufreader=null;

            Properties prop = new Properties();
            prop.setProperty("userTel", change_text_tel.getText().toString());
            prop.setProperty("userName", change_edt_name.getText().toString());
            prop.setProperty("userBirth", change_edt_birth.getText().toString());
            prop.setProperty("userGender", stringGender);
            prop.setProperty("userEmail", change_edt_email.getText().toString());

            encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/myInfoEdit.do");
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

                String line = null;
                String result = "";

                while((line=bufreader.readLine()) != null){
                    result += line;
                }

                prop.setProperty("userTel", change_text_tel.getText().toString());
                prop.setProperty("userName", change_edt_name.getText().toString());
                prop.setProperty("userBirth", change_edt_birth.getText().toString());
                prop.setProperty("userGender", stringGender);
                prop.setProperty("userEmail", change_edt_email.getText().toString());

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }finally{
                urlConnection.disconnect();  //URL 연결해제
            }
        }
    }

}
