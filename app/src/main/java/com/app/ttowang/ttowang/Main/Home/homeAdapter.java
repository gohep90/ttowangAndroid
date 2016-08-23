package com.app.ttowang.ttowang.Main.Home;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class homeAdapter extends FragmentStatePagerAdapter {

    public homeAdapter(FragmentManager fm) {

        super(fm);
        Log.i("homeAdapter - ","homeAdapter ");
    }

    @Override
    public Fragment getItem(int i) {
        return homeFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        Log.i("homeAdapter - ","getCount ");
        return home.myAllBusiness.size();
    }

    @Override
    public int getItemPosition(Object object) {
        Log.i("homeAdapter - ","getItemPosition ");
        //home.extensiblePageIndicator.initViewPager(home.upViewPager);
        //home.extensiblePageIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        //home.extensiblePageIndicator.onPageScrolled(home.upViewPager.getCurrentItem(),0,0);
        //extensiblePageIndicator.refreshDrawableState();
        return POSITION_NONE;
    }
}
