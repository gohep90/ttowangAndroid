package com.app.ttowang.ttowang.ModeChange.Stamp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class stamp extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    //Button modeChange;

    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_back; //번호입력버튼 and 빽버튼
    Button btn_addstamp; //적립하기버튼
    TextView text_telvalue; //번호입력하는부분
    TextView text_stampnum; //스템프갯수입력하는부분

    String encodedString;
    String ip;

    int focus = 1; //첫 포커스를 번호창으로 줌

    public static stamp createInstance(int itemsCount) {
        stamp stamp = new stamp();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        stamp.setArguments(bundle);
        return stamp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.stamp,container, false);
        /*
        modeChange = (Button) view.findViewById(R.id.modeChange);


        modeChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.mContext,ChangeModeMain.class));
            }
        });
        */

        //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", getActivity().MODE_PRIVATE);
        //ip = sharedPreferences.getString("ip", "" );

        text_telvalue = (TextView) view.findViewById(R.id.text_telvalue);
        btn_addstamp = (Button) view.findViewById(R.id.btn_addstamp);
        text_stampnum = (TextView) view.findViewById(R.id.text_stampnum);

        btn_0 = (Button) view.findViewById(R.id.btn_0);
        btn_1 = (Button) view.findViewById(R.id.btn_1);
        btn_2 = (Button) view.findViewById(R.id.btn_2);
        btn_3 = (Button) view.findViewById(R.id.btn_3);
        btn_4 = (Button) view.findViewById(R.id.btn_4);
        btn_5 = (Button) view.findViewById(R.id.btn_5);
        btn_6 = (Button) view.findViewById(R.id.btn_6);
        btn_7 = (Button) view.findViewById(R.id.btn_7);
        btn_8 = (Button) view.findViewById(R.id.btn_8);
        btn_9 = (Button) view.findViewById(R.id.btn_9);
        btn_back = (Button) view.findViewById(R.id.btn_back);

        text_stampnum.setText("1"); // 스템프 갯수 초기값을 1로 준다

        buttonClickListener();

        return view;
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

        text_stampnum.setOnClickListener(ClickListener);
        text_telvalue.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_addstamp:
                    //AddStampAsyncTaskCall();

                    if(text_stampnum.getText().toString().equals("") || text_stampnum.getText().toString().length() == 0)
                        Toast.makeText(getActivity(), "스템프 갯수를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else if(text_stampnum.getText().toString().equals("0"))
                        Toast.makeText(getActivity(), "스템프 갯수를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else if(text_telvalue.getText().toString().equals("") || text_telvalue.getText().toString().length() == 0)
                        Toast.makeText(getActivity(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else if(text_telvalue.getText().toString().length() != 11)
                        Toast.makeText(getActivity(), "전화번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();

                    else {
                        Toast.makeText(getActivity(), text_stampnum.getText().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), text_telvalue.getText().toString(), Toast.LENGTH_SHORT).show();

                        //입력한 스템프 갯수를 입력한 번호에 적립
                        //AddStampAsyncTaskCall();
                        //startActivity(new Intent(MainActivity.mContext,ChangeModeMain.class));
                    }
                    break;

                case R.id.text_telvalue:
                    focus = 1; //번호창을 누르면 포커스 이동
                    break;

                case R.id.text_stampnum:
                    focus = 0; ////스템프 갯수창을 누르면 포커스 이동
                    text_stampnum.setText(""); //스템프 갯수창을 누르면 초기화됨

                    //edt_stampnum.setInputType(1);

                    // 숫자로 나오게 하기
                    //edt_stampnum.setInputType(InputType.TYPE_CLASS_NUMBER);

                    //키보드 올라올때 화면 고정시키기(밀리지않게)
                    //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                    //이거 안하면 edittext를 두번 눌러야 키보드가 나와서
                    //InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //mgr.showSoftInput(edt_stampnum, InputMethodManager.SHOW_IMPLICIT);


                    break;

                case R.id.btn_0:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "0");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "0");
                    break;
                case R.id.btn_1:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "1");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "1");
                    break;
                case R.id.btn_2:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "2");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "2");
                    break;
                case R.id.btn_3:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "3");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "3");
                    break;
                case R.id.btn_4:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "4");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "4");
                    break;
                case R.id.btn_5:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "5");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "5");
                    break;
                case R.id.btn_6:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "6");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "6");
                    break;
                case R.id.btn_7:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "7");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "7");
                    break;
                case R.id.btn_8:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "8");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "8");
                    break;
                case R.id.btn_9:
                    if(focus == 0)
                        text_stampnum.setText(text_stampnum.getText().toString() + "9");
                    else
                        text_telvalue.setText(text_telvalue.getText().toString() + "9");
                    break;
                case R.id.btn_back:
                    if(focus == 0){
                        if (text_stampnum.getText().toString().length() == 0) {
                        } else
                            text_stampnum.setText(text_stampnum.getText().toString().substring(0, text_stampnum.getText().toString().length() - 1));
                        break;
                    }

                    else {
                        if (text_telvalue.getText().toString().length() == 0) {
                        } else
                            text_telvalue.setText(text_telvalue.getText().toString().substring(0, text_telvalue.getText().toString().length() - 1));
                        break;
                    }
            }
        }
    };

    public void AddStampAsyncTaskCall(){
        new AddStampAsyncTask().execute();
    }

    public class AddStampAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("stampNum", text_stampnum.getText().toString());
            prop.setProperty("stampTel", text_telvalue.getText().toString());
            encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/-----.do");
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