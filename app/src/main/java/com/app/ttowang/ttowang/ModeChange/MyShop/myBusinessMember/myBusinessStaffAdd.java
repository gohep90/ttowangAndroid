package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessMember;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.MyShop.KeyValueArrayAdapter;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop.myBusinessShop;
import com.app.ttowang.ttowang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessStaffAdd extends AppCompatActivity {
    public static Context mContext;

    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_back; //번호입력버튼 and 빽버튼
    Button btn_addstamp; //적립하기버튼
    TextView text_telvalue; //번호입력하는부분

    String ip;
    int userId;
    String businessId ="";
    Button businessphoto1;

    Spinner spinner;
    KeyValueArrayAdapter spn_adapter;
    ArrayList<String> spinnerKeys = new ArrayList<String>();
    ArrayList<String> spinnerValues = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessstaffadd);
        mContext = this;
        Intent i = getIntent();
        userId = i.getExtras().getInt("userId");

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        Toast.makeText(myBusinessStaffAdd.this, "유저아이디 : " + userId, Toast.LENGTH_SHORT).show();


//////////////////////////////////////////////////////////////////////////////////////
        spinner = (Spinner) findViewById(R.id.spinner);

        spn_adapter = new KeyValueArrayAdapter(myBusinessStaffAdd.this,R.layout.spinner_item);
        spn_adapter.setDropDownViewResource(R.layout.spinner_item);

        spinnerKeys = myBusinessMember.spinnerKeys;
        spinnerValues = myBusinessMember.spinnerValues;

        spn_adapter.setEntries(spinnerKeys.toArray(new String[spinnerKeys.size()]));
        spn_adapter.setEntryValues(spinnerValues.toArray(new String[spinnerValues.size()]));

        spinner.setAdapter(spn_adapter);
        spinner.setSelection(i.getExtras().getInt("position"));
        //스피너 선택 리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) parent.getAdapter();
                //Toast.makeText(myBusinessCoupon.this, adapter.getEntry(position), Toast.LENGTH_SHORT).show();
                businessId = adapter.getEntry(position);
                //Toast.makeText(myBusinessStaffAdd.this, businessId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        text_telvalue = (TextView) findViewById(R.id.text_telvalue);
        btn_addstamp = (Button) findViewById(R.id.btn_addstamp);

        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_back = (Button) findViewById(R.id.btn_back);

        buttonClickListener();

/*
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                businessLicense = String.valueOf(businessLicenseEdittext.getText());
                businessName =String.valueOf(businessNameEdittext.getText());
                businessTel =String.valueOf(businessTelEdittext.getText());
                businessInfo =String.valueOf(businessInfoEdittext.getText());
                businessTime =String.valueOf(businessTimeEdittext.getText());
                businessAddress =String.valueOf(businessAddressEdittext.getText());
                businessMenu =String.valueOf(businessMenuEdittext.getText());
                businessBenefit =String.valueOf(businessBenefitEdittext.getText());
                businessGroup =String.valueOf(businessGroupEdittext.getText());

                if(
                    !businessLicense.equals("") &
                    !businessName.equals("") &
                    !businessTel.equals("") &
                    !businessInfo.equals("") &
                    !businessTime.equals("") &
                    !businessAddress.equals("") &
                    !businessMenu.equals("") &
                    !businessBenefit.equals("") &
                    !businessGroup.equals("") &
                    !photo1Path.equals("")
                ){
                    //new businessAddStoreAsyncTask().execute();
                    new ImageAsyncTask().execute();
                    //Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                    //intent.putExtra("currentViewPager", currentViewPager);
                    //startActivity(intent);
                    //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(myBusinessStaffAdd.this,"빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/
    }


    private void buttonClickListener() {
        btn_0.setOnClickListener(ClickListener);
        btn_1.setOnClickListener(ClickListener);
        btn_2.setOnClickListener(ClickListener);
        btn_3.setOnClickListener(ClickListener);
        btn_4.setOnClickListener(ClickListener);
        btn_5.setOnClickListener(ClickListener);
        btn_6.setOnClickListener(ClickListener);
        btn_7.setOnClickListener(ClickListener);
        btn_8.setOnClickListener(ClickListener);
        btn_9.setOnClickListener(ClickListener);

        btn_addstamp.setOnClickListener(ClickListener);
        btn_back.setOnClickListener(ClickListener);

        text_telvalue.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_addstamp:
                    //AddStampAsyncTaskCall();

                    if(text_telvalue.getText().toString().equals("") || text_telvalue.getText().toString().length() == 0)
                        Toast.makeText(myBusinessStaffAdd.this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else if(text_telvalue.getText().toString().length() != 11)
                        Toast.makeText(myBusinessStaffAdd.this, "전화번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        //Toast.makeText(myBusinessStaffAdd.this, text_telvalue.getText().toString(), Toast.LENGTH_SHORT).show();
                        new staffAddAsyncTask().execute();
                        //입력한 스템프 갯수를 입력한 번호에 적립 쓰레드
                        //AddStampAsyncTaskCall();
                        //startActivity(new Intent(MainActivity.mContext,ChangeModeMain.class));
                    }
                    break;


                case R.id.btn_0:

                    text_telvalue.setText(text_telvalue.getText().toString() + "0");
                    break;
                case R.id.btn_1:

                    text_telvalue.setText(text_telvalue.getText().toString() + "1");
                    break;
                case R.id.btn_2:

                    text_telvalue.setText(text_telvalue.getText().toString() + "2");
                    break;
                case R.id.btn_3:

                    text_telvalue.setText(text_telvalue.getText().toString() + "3");
                    break;
                case R.id.btn_4:

                    text_telvalue.setText(text_telvalue.getText().toString() + "4");
                    break;
                case R.id.btn_5:

                    text_telvalue.setText(text_telvalue.getText().toString() + "5");
                    break;
                case R.id.btn_6:

                    text_telvalue.setText(text_telvalue.getText().toString() + "6");
                    break;
                case R.id.btn_7:

                    text_telvalue.setText(text_telvalue.getText().toString() + "7");
                    break;
                case R.id.btn_8:

                    text_telvalue.setText(text_telvalue.getText().toString() + "8");
                    break;
                case R.id.btn_9:

                    text_telvalue.setText(text_telvalue.getText().toString() + "9");
                    break;
                case R.id.btn_back:

                    if (text_telvalue.getText().toString().length() == 0) {
                    } else
                        text_telvalue.setText(text_telvalue.getText().toString().substring(0, text_telvalue.getText().toString().length() - 1));
                    break;

            }
        }
    };




    public class staffAddAsyncTask extends AsyncTask<String,Integer,String> {

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

            prop.setProperty("userTel",text_telvalue.getText().toString());
            prop.setProperty("businessId",businessId);

            String encodedString = encodeString(prop);

            try{
                //url=new URL("http://192.168.0.2:8181/ttowang/businessAdd.do");
                url=new URL("http://" + ip + ":8080/ttowang/staffAdd.do");
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
            //Log.i("myBusinessShop - ", " 내 매장 등록");
            Log.i("서버에서 받은 전체 내용 : ", result);

            try{
                JSONObject json=new JSONObject(result);
                result = json.getString("result");
                if(result.equals("staffAdd 성공")){
                    Toast.makeText(MainActivity.mContext, "직원 추가 했습니다.", Toast.LENGTH_SHORT).show();
                    myBusinessMember.staffRefresh();
                }else if(result.equals("이미 직원")){
                    Toast.makeText(MainActivity.mContext, "이미 직원 입니다." , Toast.LENGTH_SHORT).show();
                }else if(result.equals("전화번호 없음")){
                    Toast.makeText(MainActivity.mContext, "입력한 전화번호의 사용자가 없습니다." , Toast.LENGTH_SHORT).show();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

}
