package com.app.ttowang.ttowang.Main.Business;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

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

public class business extends AppCompatActivity implements OnMapReadyCallback {

    ViewPager pager;
    ExtensiblePageIndicator extensiblePageIndicator;

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap googleMap;

    TextView txt_title,txt_title2,txt_info,txt_phone,txt_time,txt_menu,txt_map,txt_benefit;
    ImageView img_group,img_bookMark;
    String businessId="";

    String [] photoList = new String[10];   //사진 최대 10개??
    int count=0;

    String ip= MainActivity.ip;
    static String userId = MainActivity.user;  //로그인시 ID값 저장해야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.business);


        //////////////////////////지도///////////////////////////////////
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//////////////////////////////////////설정///////////////////////////////////////////////////
        Intent i = getIntent();
        businessId = i.getExtras().getString("businessId");

        txt_title   =   (TextView)findViewById(R.id.txt_title);
        txt_title2   =   (TextView)findViewById(R.id.txt_title2);
        txt_info    =   (TextView)findViewById(R.id.txt_info);
        txt_phone   =   (TextView)findViewById(R.id.txt_phone);
        txt_time    =   (TextView)findViewById(R.id.txt_time);
        txt_menu    =   (TextView)findViewById(R.id.txt_menu);
        txt_map     =   (TextView)findViewById(R.id.txt_map);
        txt_benefit =   (TextView)findViewById(R.id.txt_benefit);

        img_group   =   (ImageView)findViewById(R.id.img_group);
        img_bookMark =  (ImageView)findViewById(R.id.img_bookMark);


        //즐겨찾기 등록버튼
        img_bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookMarkAsyncTaskCall();
            }
        });

        //초기값 불러오기
        BusinessAsyncTaskCall();


    }


    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(false);
        Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL).title("Seoul"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 15));  //숫자가 작을수록 넓은범위 보임
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { //지도 클릭
            @Override
            public void onMapClick(LatLng latLng) {
                //Toast.makeText(business.this, String.valueOf(latLng), Toast.LENGTH_SHORT).show();

                Intent map = new Intent(business.this,businessMap.class);
                //   intent.putExtra("index", myItems.get(pos).index);
                startActivity(map);
            }
        });

    }




    //비지니스 초기 상세정보 가져오기
    public void BusinessAsyncTaskCall(){
        new BusinessAsyncTask().execute();
    }

    public class BusinessAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("businessId", businessId);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/businessView.do");
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
            jsonParserList(result);
        }
    }


    private void jsonParserList(String recv) {

        Log.i("서버에서 받은 전체 내용 : ", recv);

        try{
            JSONObject json=new JSONObject(recv);
            JSONArray jArr =json.getJSONArray("List");
            JSONArray jArr2 =json.getJSONArray("Photo");

            String url="http://" + ip + ":8080/ttowang/image/";

            for(int i=0; i<jArr.length();i++){
                json=jArr.getJSONObject(i);

                txt_title.setText(json.getString("businessName"));
                txt_title2.setText(json.getString("businessInfo"));
                txt_info.setText(json.getString("businessInfo"));
                txt_phone.setText(json.getString("businessTel"));
                txt_time.setText(json.getString("businessTime"));
                txt_menu.setText(json.getString("businessMenu"));
                txt_map.setText(json.getString("businessAddress"));
                txt_benefit.setText(json.getString("businessBenefit"));

                switch (Integer.parseInt(json.getString("businessGroup"))){
                    case 1:
                        img_group.setImageResource(R.drawable.cafe);
                        break;
                    case 2:
                        img_group.setImageResource(R.drawable.food);
                        break;
                    case  3:
                        img_group.setImageResource(R.drawable.fashion);
                        break;
                    case 4:
                        img_group.setImageResource(R.drawable.hair);
                        break;
                    case 5:
                        img_group.setImageResource(R.drawable.market);
                        break;
                    default:
                        img_group.setImageResource(R.drawable.etc);
                }

            }

            count=jArr2.length();
            for(int i=0; i<jArr2.length();i++){
                json=jArr2.getJSONObject(i);

                photoList[i]=url+json.getString("photoName");
              //  Log.i("사진사진 : ", photoList[i]);

            }

            //////////////////////////////////////사진///////////////////////////////////////////////////

            pager= (ViewPager)findViewById(R.id.pager);
            extensiblePageIndicator = (ExtensiblePageIndicator)findViewById(R.id.flexibleIndicator);
            //ViewPager에 설정할 Adapter 객체 생성
            //ListView에서 사용하는 Adapter와 같은 역할.
            //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름
            //PagerAdapter를 상속받은 businessAdapter 객체 생성
            //businessAdapter에게 LayoutInflater 객체 전달

            businessAdapter adapter= new businessAdapter(getLayoutInflater(),photoList,count);

            //ViewPager에 Adapter 설정
            pager.setAdapter(adapter);
            extensiblePageIndicator.initViewPager(pager);


        }catch(JSONException e){
            e.printStackTrace();
        }
    }








    //즐겨찾기 등록 스레드
    public void BookMarkAsyncTaskCall(){
        new BookMarkAsyncTask().execute();
    }

    public class BookMarkAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("businessId", businessId);
            prop.setProperty("userId", userId);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/insertMyBusiness.do");
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
                Toast.makeText(business.this, json.getString("result"), Toast.LENGTH_SHORT).show();
                home.refresh();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

}
