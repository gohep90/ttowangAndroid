package com.app.ttowang.ttowang.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Business.businessMain;
import com.app.ttowang.ttowang.Main.Event.eventMain;
import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.Setting.Mainsetting;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //final DBHelper dbHelper = new DBHelper(getActivi(), "alarm.db", null, 1);

    public static Context mContext;
    public static int first = 0;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor Edit;

    //public static String ip = "192.168.0.2";  주용

    public static String ip = "192.168.21.118";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //startActivity(new Intent(this,Loading.class));  //로딩화면
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mContext = this;

        sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
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

        initToolbar();
        initViewPagerAndTabs();
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
       // mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(home.createInstance(0), "홈");
        pagerAdapter.addFragment(businessMain.createInstance(1), "전체매장");
        pagerAdapter.addFragment(eventMain.createInstance(2), "이벤트");
        pagerAdapter.addFragment(Mainsetting.createInstance(3), "설정");
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

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
}
