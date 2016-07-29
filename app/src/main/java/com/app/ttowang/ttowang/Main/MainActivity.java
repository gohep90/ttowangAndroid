package com.app.ttowang.ttowang.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.app.ttowang.ttowang.Business.businessMain;
import com.app.ttowang.ttowang.Event.eventMain;
import com.app.ttowang.ttowang.Home.home;
import com.app.ttowang.ttowang.R;
import com.app.ttowang.ttowang.Setting.setting;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //final DBHelper dbHelper = new DBHelper(getActivi(), "alarm.db", null, 1);

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        initToolbar();
        initViewPagerAndTabs();

    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("또왕");
       // mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(home.createInstance(0), "홈");
        pagerAdapter.addFragment(businessMain.createInstance(1), "전체매장");
        pagerAdapter.addFragment(eventMain.createInstance(2), "이벤트");
        pagerAdapter.addFragment(setting.createInstance(3), "설정");
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

}
