package com.app.ttowang.ttowang.ModeChange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.MyShop.myshop;
import com.app.ttowang.ttowang.ModeChange.Recent.recent;
import com.app.ttowang.ttowang.ModeChange.Recent.recentActivity;
import com.app.ttowang.ttowang.ModeChange.Stamp.stamp;
import com.app.ttowang.ttowang.ModeChange.setting.Modesetting;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;
import java.util.List;

public class ChangeModeMain extends AppCompatActivity {
    //final DBHelper dbHelper = new DBHelper(getActivi(), "alarm.db", null, 1);

    public static Context mContext;
    public static int first = 0;
    public static String businessId = "2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeRed);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modechangemain);
        mContext = this;
        initViewPagerAndTabs();
//
    }


    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(stamp.createInstance(0), "Stamp");
        pagerAdapter.addFragment(myshop.createInstance(1), "MY SHOP");
        pagerAdapter.addFragment(recentActivity.createInstance(2), "RECENT");
        pagerAdapter.addFragment(Modesetting.createInstance(3), "SETTING");
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

/*
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){

        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            Log.i("버튼","뒤로가기");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(RESULT_CANCELED, intent);
            finish();
        }else if(event.getKeyCode() == KeyEvent.KEYCODE_HOME){
            Log.i("버튼","홈");
        }else if(event.getKeyCode() == KeyEvent.KEYCODE_MENU){
            Log.i("버튼","메뉴");
        }
        return true;
    }
*/

    @Override
    public void onBackPressed(){
        Log.i("ChangeModeMain - ","뒤로가기 버튼");
        Intent intent = new Intent(getApplication(), MainActivity.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
