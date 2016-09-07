package com.app.ttowang.ttowang.Main.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
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
import java.util.Enumeration;
import java.util.Properties;

public class Login extends AppCompatActivity {

    String encodedString="", result="";
    String ip;

    String userTel;
    int userId;
    String userGender="남"; //처음은 남자

    TextView text_tel;
    EditText edt_name, edt_birth, edt_email;
    Button btn_m, btn_w, btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        Intent i = getIntent();
        userTel = i.getExtras().getString("userTel");

        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_birth = (EditText)findViewById(R.id.edt_birth);
        edt_email = (EditText)findViewById(R.id.edt_email);
        btn_m = (Button)findViewById(R.id.btn_m);
        btn_w = (Button)findViewById(R.id.btn_w);
        btn_join = (Button)findViewById(R.id.btn_join);

        text_tel = (TextView)findViewById(R.id.text_tel);
        text_tel.setText(userTel);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_m.setOnClickListener(ClickListener);
        btn_w.setOnClickListener(ClickListener);
        btn_join.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_m:
                    btn_m.setBackgroundResource(R.drawable.btn_gender);
                    btn_w.setBackgroundResource(R.drawable.btn_ngender);
                    btn_m.setTextColor(getResources().getColorStateList(R.color.m));
                    btn_w.setTextColor(getResources().getColorStateList(R.color.w));
                    userGender="남";
                    break;

                case R.id.btn_w:
                    btn_m.setBackgroundResource(R.drawable.btn_ngender);
                    btn_w.setBackgroundResource(R.drawable.btn_gender);
                    btn_m.setTextColor(getResources().getColorStateList(R.color.w));
                    btn_w.setTextColor(getResources().getColorStateList(R.color.m));
                    userGender="여";
                    break;

                case R.id.btn_join:
                    if(edt_name.getText().toString() == null || edt_name.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else if(edt_birth.getText().toString() == null || edt_birth.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else {

                        LoginAsyncTaskCall();

                        Toast.makeText(getApplicationContext(), "회원가입", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);

                        // 자동로그인
                        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);   //쉐어드 객체 얻기
                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
                        sharedPreferencesEditor.putInt("userId", 0);
                        sharedPreferencesEditor.putString("userTel", userTel);
                        sharedPreferencesEditor.putString("userName", edt_name.getText().toString());
                        sharedPreferencesEditor.putString("userBirth", edt_birth.getText().toString());
                        sharedPreferencesEditor.putString("userGender", userGender);
                        if(edt_email.getText().toString() != null || edt_email.getText().toString().length() > 0)
                            sharedPreferencesEditor.putString("userEmail", edt_email.getText().toString());
                        sharedPreferencesEditor.commit();
                        finish();
                    }
                    break;
            }
        }
    };

    public void LoginAsyncTaskCall(){
        new LoginAsyncTask().execute();
    }

    public class LoginAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            String result = recvList();
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

            HttpURLConnection urlConnection = null;
            URL url = null;
            DataOutputStream out = null;
            BufferedInputStream buf = null;
            BufferedReader bufreader = null;

            Properties prop = new Properties();
            prop.setProperty("userTel", userTel);
            prop.setProperty("userName", edt_name.getText().toString());
            prop.setProperty("userBirth", edt_birth.getText().toString());
            prop.setProperty("userGender", userGender);

            if(edt_email.getText().toString() == null || edt_email.getText().toString().length() == 0) {
            } else
                prop.setProperty("userEmail", edt_email.getText().toString());

            encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/insertUser.do");
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

                while((line = bufreader.readLine()) != null){
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
    }

    public void onBackPressed(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Login.this);
        alert_confirm.setMessage("회원가입을 종료하겠습니까?").setCancelable(true).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'yes'
                        Intent i = new Intent(getApplicationContext(), Tel.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    protected void onPause() {
        super.onPause();
    }
}
