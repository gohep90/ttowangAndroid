package com.app.ttowang.ttowang.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class coupon extends android.support.v4.app.Fragment{

    public final static String ITEMS_COUNT_KEY = "Main_C$ItemsCount";

    public View onCreateView(LayoutInflater in, ViewGroup ctn, Bundle savedState)
    {
        View view = in.inflate(R.layout.coupon, ctn, false);


        return view;
    }

    public static coupon createInstance(int itemsCount) {
        coupon mainC = new coupon();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        mainC.setArguments(bundle);
        return mainC;
    }
}
