package com.app.ttowang.ttowang.Main.Business;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

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
    String index="";
    String [] photoList = new String[10];   //사진 최대 10개??
    int count=0;

    String ip="117.17.142.99";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.business);

        initToolbar();

        //////////////////////////지도///////////////////////////////////
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//////////////////////////////////////설정///////////////////////////////////////////////////
        Intent i = getIntent();
        index = i.getExtras().getString("index");

        txt_title   =   (TextView)findViewById(R.id.txt_title);
        txt_title2   =   (TextView)findViewById(R.id.txt_title2);
        txt_info    =   (TextView)findViewById(R.id.txt_info);
        txt_phone   =   (TextView)findViewById(R.id.txt_phone);
        txt_time    =   (TextView)findViewById(R.id.txt_time);
        txt_menu    =   (TextView)findViewById(R.id.txt_menu);
        txt_map     =   (TextView)findViewById(R.id.txt_map);
        txt_benefit =   (TextView)findViewById(R.id.txt_benefit);

        //초기값 불러오기
        BusinessAsyncTaskCall();


    }



    //onClick속성이 지정된 View를 클릭했을때 자동으로 호출되는 메소드
/*
    public void mOnClick(View v){

        int position;

        switch( v.getId() ){
            case R.id.btn_previous://이전버튼 클릭
                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴
                //현재 위치(position)에서 -1 을 해서 이전 position으로 변경
                //이전 Item으로 현재의 아이템 변경 설정(가장 처음이면 더이상 이동하지 않음)
                //첫번째 파라미터: 설정할 현재 위치
                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
                pager.setCurrentItem(position-1,true);
                break;

            case R.id.btn_next://다음버튼 클릭
                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴
                //현재 위치(position)에서 +1 을 해서 다음 position으로 변경
                //다음 Item으로 현재의 아이템 변경 설정(가장 마지막이면 더이상 이동하지 않음)
                //첫번째 파라미터: 설정할 현재 위치
                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜
                pager.setCurrentItem(position+1,true);
                break;
        }

    }
*/





    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("또왕");
        // mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
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
        googleMap.setMyLocationEnabled(true);
        Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL).title("Seoul"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 10));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

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
            prop.setProperty("businessId", index);

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
                // image.setImageBitmap(imageAsyncTaskCall(json.getString("photo"),json.getString("exif")));
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
}
