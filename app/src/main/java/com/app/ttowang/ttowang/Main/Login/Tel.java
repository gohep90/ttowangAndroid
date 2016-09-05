package com.app.ttowang.ttowang.Main.Login;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.app.ttowang.ttowang.Main.Loading.Loading;
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

public class Tel extends AppCompatActivity {

    String encodedString="", result="";
    String ip;

    int userId;
    int userCode = 0; //일반사용자 = 1, 준회원 = 4
    String userTel, userName, userBirth, userGender, userEmail;

    EditText edt_tel, edt_confirm;
    Button btn_send, btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tel);

        //startActivity(new Intent(this,Loading.class));

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("ip", "14.63.213.208" + "");  //ip 설정하기 저장하기
        sharedPreferencesEditor.commit();

        ip = sharedPreferences.getString("ip", "");
        userTel = sharedPreferences.getString("userTel", "");

        //로그인 되어있으면 MainActivity
        if (!userTel.equals("")) {

            Toast.makeText(getApplicationContext(), "로그인 O", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
        //로그인 안되어있으면 Tel
        else {
            Toast.makeText(getApplicationContext(), "로그인 X", Toast.LENGTH_SHORT).show();
        }

        edt_tel = (EditText)findViewById(R.id.edt_tel);
        edt_confirm = (EditText)findViewById(R.id.edt_confirm);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);

        buttonClickListener();
    }

    private void buttonClickListener() {
        btn_send.setOnClickListener(ClickListener);
        btn_confirm.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_send:
                    if(edt_tel.getText().toString() == null || edt_tel.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else if(edt_tel.getText().toString().length() != 11)
                        Toast.makeText(getApplicationContext(), "전화번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "인증번호를 받았습니다.", Toast.LENGTH_SHORT).show();
                                edt_confirm.setText("053176");
                            }
                        }, 1500);}
                    break;

                case R.id.btn_confirm:
                    if(edt_confirm.getText().toString() == null || edt_confirm.getText().toString().length() == 0)
                        Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else
                        CheckAsyncTaskCall();
                    break;
            }
        }
    };

    public void CheckAsyncTaskCall(){
        new CheckAsyncTask().execute();
    }

    public class CheckAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            result = recvList();
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

            HttpURLConnection urlConnection = null;
            URL url = null;
            DataOutputStream out = null;
            BufferedInputStream buf = null;
            BufferedReader bufreader = null;

            Properties prop = new Properties();
            prop.setProperty("userTel", edt_tel.getText().toString());

            encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/checkUser.do");
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

        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            checkList(result);
        }
    }

    private void checkList(String recv) {

        Log.i("서버에서 받은 전체 내용 : ", recv);

        try{
            JSONObject json=new JSONObject(recv);
            JSONArray jArr =json.getJSONArray("checkUser");

            // 정회원이거나 준회원일때
            if(jArr != null && jArr.length() > 0) {

                json = jArr.getJSONObject(0);
                userCode = json.getInt("userCode"); //정회원과 준회원 구분
                userId = json.getInt("userId");

                // 정회원일때
                if(userCode == 1) {
                    userName = json.getString("userName");
                    userBirth = json.getString("userBirth");
                    userGender = json.getString("userGender");
                    //userEmail = json.getString("userEmail");

                    Toast.makeText(getApplicationContext(), "정회원입니다.", Toast.LENGTH_SHORT).show();

                    // 자동 로그인
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);   //쉐어드 객체 얻기
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
                    sharedPreferencesEditor.putInt("userId", userId);
                    sharedPreferencesEditor.putString("userTel", edt_tel.getText().toString());
                    sharedPreferencesEditor.putString("userName", userName);
                    sharedPreferencesEditor.putString("userBirth", userBirth);
                    sharedPreferencesEditor.putString("userGender", userGender);
                    //sharedPreferencesEditor.putString("userEmail", userEmail);
                    sharedPreferencesEditor.commit();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }

                // 준회원일때
                else {
                    Toast.makeText(getApplicationContext(), "준회원입니다.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    i.putExtra("userTel", edt_tel.getText().toString());
                    startActivity(i);
                    finish();
                }
            }

            // 회원이 아닐때
            else {
                Toast.makeText(getApplicationContext(), "회원이 아닙니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Login.class);
                i.putExtra("userTel", edt_tel.getText().toString());
                startActivity(i);
                finish();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Tel.this);
        alert_confirm.setMessage("어플을 종료하겠습니까?").setCancelable(true).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'yes'
                        onPause();
                        android.os.Process.killProcess(android.os.Process.myPid());
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
