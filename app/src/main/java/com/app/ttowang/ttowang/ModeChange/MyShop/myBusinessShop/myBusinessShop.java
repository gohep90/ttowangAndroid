package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessShop extends AppCompatActivity {
    public static Context mContext;

    static myBusinessShopAdapter adapter;

    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessshop_main);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        mContext = this;
        ListView listview ;


        // Adapter 생성
        adapter = new myBusinessShopAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        myBusinessShopAdapter.listViewItemList.clear();
        new businessDownAsyncTask().execute();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                myBusinessShopItem item = (myBusinessShopItem) parent.getItemAtPosition(position) ;

                //String titleStr = item.getBenefit() ;

            }
        }) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), myBusinessShopSelectType.class);   //인텐트로 넘겨줄건데요~
                intent.putExtra("userId", MainActivity.user);
                startActivity(intent);
                //Toast.makeText(myBusinessShop.this,"추가 버튼", Toast.LENGTH_SHORT).show();

            }
        });
    }




    public class businessDownAsyncTask extends AsyncTask<String,Integer,String> {

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

            prop.setProperty("userId",MainActivity.user);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/businessAll.do");
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
            Log.i("myBusinessShop - ", " 내 매장");
            Log.i("서버에서 받은 전체 내용 : ", result);

            try{
                JSONObject json=new JSONObject(result);
                JSONArray jArr =json.getJSONArray("List");
                if(jArr.length()==0){

                    finish();
                    Toast.makeText(myBusinessShop.this, "등록된 쿠폰이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < jArr.length(); i++ ) {
                    json = jArr.getJSONObject(i);
                    if(json.getString("businessType").equals("1")){ //정식 사업자면
                        adapter.addItem(
                                json.getString("businessType"),     //정식 사업자
                                json.getString("businessId"),       //매장 아이디
                                json.getString("businessLicense"),  //매장 라이센스
                                json.getString("businessName"),     //매장 이름
                                json.getString("businessTel"),      //매장 전화번호
                                json.getString("businessInfo"),     //매장 정보
                                json.getString("businessTime"),     //매장 시간
                                json.getString("businessAddress"),  //매장 주소
                                json.getString("businessMenu"),     //매장 메뉴
                                json.getString("businessBenefit"),  //매장 혜택
                                json.getString("businessGroup")     //매장 종류
                        ) ;
                    }else{                                           //일반 사업자(개인)
                        adapter.addItem(
                                json.getString("businessType"),     //정식 사업자
                                json.getString("businessId"),       //매장 아이디
                                "",  //매장 라이센스
                                json.getString("businessName"),     //매장 이름
                                json.getString("businessTel"),      //매장 전화번호
                                json.getString("businessInfo"),     //매장 정보
                                "",     //매장 시간
                                "",  //매장 주소
                                "",     //매장 메뉴
                                json.getString("businessBenefit"),  //매장 혜택
                                json.getString("businessGroup")     //매장 종류
                        ) ;
                    }
                }
                adapter.notifyDataSetChanged();     //리스트
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
