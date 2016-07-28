package com.app.ttowang.ttowang.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.app.ttowang.ttowang.R;


public class setting extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    public static setting createInstance(int itemsCount) {
        setting Setting = new setting();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        Setting.setArguments(bundle);
        return Setting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.setting, container, false);

        return linearLayout;
    }




}
