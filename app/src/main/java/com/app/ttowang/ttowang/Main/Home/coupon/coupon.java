package com.app.ttowang.ttowang.Main.Home.coupon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class coupon extends android.support.v4.app.ListFragment{

    public final static String ITEMS_COUNT_KEY = "Main_C$ItemsCount";
    int number;
    View view;
    public static couponItemAdapter adapter;
    ListView list;

    public View onCreateView(LayoutInflater in, ViewGroup ctn, Bundle savedState)
    {
        Log.i("coupon - ", "초기화");
        view = in.inflate(R.layout.couponview, ctn, false);
        number = getArguments() != null ? getArguments().getInt("number") : 1;
        list = (ListView) view.findViewById(android.R.id.list);

        adapter = new couponItemAdapter() ;

        list.setAdapter(adapter);

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

        Log.d("coupon - ", "onActivityCreated");

        setAddAdapter(home.myCouponNumber);    //처음 초기화

        //list.setSelection(home.myCouponNumber);      // 처음은 코드로 하단으로 넘어준다
        Log.i("coupon - ","초기화 쿠폰 리스트 갯수 " +home.myCouponNumber);

    }

    public static void setAddAdapter(int a){

        adapter.clearItem();                 //클리어 해주고
        for(int i = 1; i <= a; i++){        //하나씩 추가
            adapter.addItem();
            Log.i("coupon - ","스템프 리스트 추가" + i);
        }
        adapter.notifyDataSetChanged();      //수정됐다고 알림
    }
}
