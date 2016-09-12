package com.app.ttowang.ttowang.ModeChange.Stamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.ModeChange.Stamp.AddStamp.AddStamp;
import com.app.ttowang.ttowang.ModeChange.Stamp.AddStamp.AddStampList;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
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


public class stamp extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;

    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_back; //번호입력버튼 and 빽버튼
    Button btn_searchtel; //적립하기버튼
    TextView text_telvalue; //번호입력하는부분

    String encodedString="", result="";
    String businessId ="";
    String ip;
    int userId;

    Context mContext;

    Spinner spinner;
    KeyValueArrayAdapter spn_adapter;
    ArrayList<String> spinnerKeys = new ArrayList<String>();
    ArrayList<String> spinnerValues = new ArrayList<String>();

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

        spinner = (Spinner) view.findViewById(R.id.spinner);

        spn_adapter = new KeyValueArrayAdapter(ChangeModeMain.mContext,R.layout.spinner_item);
        spn_adapter.setDropDownViewResource(R.layout.spinner_item);

        businessListAsyncTaskCall();

        //스피너 선택 리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) parent.getAdapter();
                //Toast.makeText(myBusinessCoupon.this, adapter.getEntry(position), Toast.LENGTH_SHORT).show();
                businessId = adapter.getEntry(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", getActivity().MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");
        userId = sharedPreferences.getInt("userId", 0);

        text_telvalue = (TextView) view.findViewById(R.id.text_telvalue);
        btn_searchtel = (Button) view.findViewById(R.id.btn_searchtel);

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

        btn_searchtel.setOnClickListener(ClickListener);
        btn_back.setOnClickListener(ClickListener);

        text_telvalue.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_searchtel:

                    if(text_telvalue.getText().toString().equals("") || text_telvalue.getText().toString().length() == 0)
                        Toast.makeText(getActivity(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        try {
                            CheckTelAsyncTaskCall();
                        }catch (Exception e){
                        }
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
                url=new URL("http://" + ip + ":8080/ttowang/spinnerListStaff.do");
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
                JSONArray jArr =json.getJSONArray("spinnerListStaff");

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

    //////////////////////////////////////////////   번호 체크   ////////////////////////////////////////////////////////////////////////////////////

    public void CheckTelAsyncTaskCall(){
        new CheckTelAsyncTask().execute();
    }

    public class CheckTelAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("tel", text_telvalue.getText().toString());

            encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/checkTel.do");
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
            JSONObject json = new JSONObject(recv);
            JSONArray jArr = json.getJSONArray("checkTel");

            // 검색 결과가 없을 때
            if(jArr.length() == 0){
                Toast.makeText(getActivity(), String.valueOf(jArr.length()) + "명 있습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), AddStamp.class);
                intent.putExtra("userTel", text_telvalue.getText().toString());
                intent.putExtra("userName", "회원이 아닙니다.");
                intent.putExtra("businessId", businessId);
                startActivity(intent);
                text_telvalue.setText("");
            }

            // 검색 결과가 한명일 때
            else if(jArr.length() == 1) {
                Toast.makeText(getActivity(), String.valueOf(jArr.length()) + "명 있습니다.", Toast.LENGTH_SHORT).show();

                String userName="";

                //Log.i("AddStamp-- : ", jArr.getJSONObject(0).getString("userName"));
                if(jArr.getJSONObject(0).getString("userCode").equals("4")){ //1 정회원,  4 준회원
                    userName="준회원";
                }else{
                    userName=jArr.getJSONObject(0).getString("userName");
                }

                Intent intent = new Intent(getContext(), AddStamp.class);
                intent.putExtra("userTel", jArr.getJSONObject(0).getString("userTel"));
                intent.putExtra("userName", userName);
                intent.putExtra("businessId", businessId);
                startActivity(intent);
                text_telvalue.setText("");
            }

            // 검색 결과가 2명 이상일 때
            else {
                Toast.makeText(getActivity(), String.valueOf(jArr.length()) + "명 있습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), AddStampList.class);
                intent.putExtra("jsonArray", jArr.toString());
                intent.putExtra("businessId", businessId);
                startActivity(intent);
                text_telvalue.setText("");
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}