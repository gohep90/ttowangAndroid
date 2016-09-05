package com.app.ttowang.ttowang.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Business.businessMain;
import com.app.ttowang.ttowang.Main.Event.eventMain;
import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.Setting.Mainsetting;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
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
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    //final DBHelper dbHelper = new DBHelper(getActivi(), "alarm.db", null, 1);

    public static Context mContext;
    public static int first = 0;

    public static SharedPreferences.Editor Edit;
    public static String ip = "14.63.213.208";   //클라우드서버 아이피
    public static SharedPreferences sharedPreferences;
    //public static String ip = "192.168.0.2";  // 박주용 아이피

    //public static String ip = "192.168.21.63";   //박민수 아이피

    //public static String ip = "192.168.21.208"; //장지은 아이피

    public static String user ="2";

    public static ViewPager viewPager;
    public static PagerAdapter pagerAdapter;
    TabLayout tabLayout;

    int userId;
    String userTel;
    String encodedString="", result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //startActivity(new Intent(this,Loading.class));  //로딩화면
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");
        userTel = sharedPreferences.getString("userTel", "");
        userId = sharedPreferences.getInt("userId", 0);

        //방금 회원가입한 사람 userId 가져오기
        if (userId == 0) {
            Toast.makeText(getApplicationContext(), "userId X", Toast.LENGTH_SHORT).show();
            CheckAsyncTaskCall();
        }
        else {
            Toast.makeText(getApplicationContext(), "userId O", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "userId : " + userId, Toast.LENGTH_SHORT).show();
        }

        mContext = this;
        Edit = sharedPreferences.edit();

        if(sharedPreferences.getInt("openNumber",0) == 0){  //처음 열었으면
            Edit.putString("nowMode", "off");
            Edit.putInt("openNumber", 1);
            Log.i("MainActivity - ", "첫 로딩 초기화");
            Log.i("MainActivity - ", "가맹점 모드 : off");
            Edit.commit();

        }else{    //처음 연게 아니면
            String nowMode = sharedPreferences.getString("nowMode","");
            Log.i("MainActivity - ", "가맹점 모드 : " + nowMode);
            if(nowMode.equals("on")){   //마지막으로 종료한게 사업자 모드면
                Intent intent = new Intent(mContext, ChangeModeMain.class);
                Toast.makeText(getApplicationContext(), "사업자 모드 실행", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, 0);
            }
        }

        initViewPagerAndTabs();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {        //현재 뷰페이저 번호 가져오기
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.chome);
                        tabLayout.getTabAt(1).setIcon(R.drawable.shop);
                        tabLayout.getTabAt(2).setIcon(R.drawable.event);
                        tabLayout.getTabAt(3).setIcon(R.drawable.set);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.cshop);
                        tabLayout.getTabAt(2).setIcon(R.drawable.event);
                        tabLayout.getTabAt(3).setIcon(R.drawable.set);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(R.drawable.home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.shop);
                        tabLayout.getTabAt(2).setIcon(R.drawable.cevent);
                        tabLayout.getTabAt(3).setIcon(R.drawable.set);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).setIcon(R.drawable.home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.shop);
                        tabLayout.getTabAt(2).setIcon(R.drawable.event);
                        tabLayout.getTabAt(3).setIcon(R.drawable.cset);
                        break;
                }
                Log.i("MainActivity - ", "fragment 번호 = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPagerAndTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(home.createInstance(0), "홈");
        pagerAdapter.addFragment(businessMain.createInstance(1), "전체매장");
        pagerAdapter.addFragment(eventMain.createInstance(2), "이벤트");
        pagerAdapter.addFragment(Mainsetting.createInstance(3), "설정");

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.chome);
        tabLayout.getTabAt(1).setIcon(R.drawable.shop);
        tabLayout.getTabAt(2).setIcon(R.drawable.event);
        tabLayout.getTabAt(3).setIcon(R.drawable.set);

    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //설정한 시간 가져오기 부분
        switch (requestCode) {
            case 0:
                if(resultCode == RESULT_OK) {                 //ChangeMode에서 Modechange 버튼 누르면 기본 화면 전환
                    Log.i("MainActivity - ","가맹점 모드 RESULT_OK 받음");
                }else if(resultCode == RESULT_CANCELED) {      //ChangeMode에서 뒤로가기 누르면 어플 종료
                    Log.i("MainActivity - ","가맹점 모드 RESULT_CANCELED 받음");
                    finish();
                }
                break;
        }
    }

    // 방금 회원가입한 사람 userId 가져오기
    public void CheckAsyncTaskCall(){
        new CheckAsyncTask().execute();
    }

    public class CheckAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            result =recvList();
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
            prop.setProperty("userTel", userTel);

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

        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            checkList(result);
        }
    }

    private void checkList(String recv) {

        Log.i("서버에서 받은 전체 내용 : ", recv);

        try{
            JSONObject json=new JSONObject(recv);
            JSONArray jArr =json.getJSONArray("checkUser");

            json = jArr.getJSONObject(0);
            userId = json.getInt("userId");

            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);   //쉐어드 객체 얻기
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
            sharedPreferencesEditor.putInt("userId", userId);
            sharedPreferencesEditor.commit();

            Toast.makeText(getApplicationContext(), "userId : " + userId, Toast.LENGTH_SHORT).show();

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
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
}
