package com.app.ttowang.ttowang.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment implements homeFragment.OnFragmentInteractionListener {

    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    private ViewPager pager;
    private TextView text_home;
    private homeAdapter adapter;

    static List myBusiness = new ArrayList();
    static List mycoupon = new ArrayList();
    static View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.home, container, false);
        view = inflater.inflate(R.layout.home,container, false);
        pager = (ViewPager)view.findViewById(R.id.viewpager);
        text_home = (TextView) view.findViewById(R.id.text_home);

        myBusiness.clear();
        myBusiness.add("집");
        myBusiness.add("학교");
        myBusiness.add("엑");
        myBusiness.add("대충");
        myBusiness.add("빡세다");

        mycoupon.clear();
        mycoupon.add("5");
        mycoupon.add("10");
        mycoupon.add("100");
        mycoupon.add("50");
        mycoupon.add("5000");


        pager.setClipToPadding(false);      //양 옆의 카드 보이게 해주는거
        pager.setPadding(100,0,100,0);      //양 옆의 카드 보이게 해주는거(패딩)
        setPagerAdapter();

        return view;
    }

    public static home createInstance(int itemsCount) {
        home Home = new home();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        Home.setArguments(bundle);
        return Home;
    }

    public void onFragmentCreated(int number) {
        text_home = (TextView) view.findViewById(R.id.text_home);
        text_home.setText("쿠폰 갯수 : " + mycoupon.get(number));
    }

    private void setPagerAdapter() {
        //adapter = new homeAdapter(getActivity().getSupportFragmentManager());
        adapter = new homeAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
    }
}
