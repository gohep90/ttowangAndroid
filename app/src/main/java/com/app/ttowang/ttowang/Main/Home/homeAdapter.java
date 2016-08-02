package com.app.ttowang.ttowang.Main.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class homeAdapter extends FragmentStatePagerAdapter {

    public homeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return homeFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return home.businessName.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
