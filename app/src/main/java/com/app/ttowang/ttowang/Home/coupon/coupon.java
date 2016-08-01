package com.app.ttowang.ttowang.Home.coupon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.app.ttowang.ttowang.Home.home;
import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class coupon extends android.support.v4.app.ListFragment{

    public final static String ITEMS_COUNT_KEY = "Main_C$ItemsCount";
    static ArrayAdapter<String> adapter;
    int number;

    public View onCreateView(LayoutInflater in, ViewGroup ctn, Bundle savedState)
    {
        View view = in.inflate(R.layout.coupon, ctn, false);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, home.thisiscoupon);
        return view;
    }

    public static coupon createInstance(int itemsCount) {
        coupon mainC = new coupon();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        mainC.setArguments(bundle);
        return mainC;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("ArrayListFragment", "onCreate");

        setListAdapter(adapter);
    }

    public static void thisiscouponRefresh(){
        adapter.notifyDataSetChanged();

    }
}
