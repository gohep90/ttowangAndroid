package com.app.ttowang.ttowang.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment implements homeFragment.OnFragmentInteractionListener {

    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    private ViewPager pager;
    private TextView text_home;
    private homeAdapter adapter;
    private RelativeLayout viewlayout;
    private ImageView mybusinessimg;

    static List businessName = new ArrayList();
    static List businessLocation = new ArrayList();
    static List remainCoupon = new ArrayList();
    static List usedCoupon = new ArrayList();

    static View view;

    ExtensiblePageIndicator extensiblePageIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.home, container, false);
        view = inflater.inflate(R.layout.home,container, false);
        pager = (ViewPager)view.findViewById(R.id.viewpager);
        //text_home = (TextView) view.findViewById(R.id.text_home);

        businessName.clear();
        businessName.add("애들아");
        businessName.add("빡세게");
        businessName.add("하고 있니");
        businessName.add("나만");
        businessName.add("하나여..");

        businessLocation.clear();
        businessLocation.add("누구보다 빠르게");
        businessLocation.add("난 남들과는 다르게");
        businessLocation.add("색다르게 리듬을 타는");
        businessLocation.add("비트위의 나그네");
        businessLocation.add("더빠르게 빨려들어가");

        remainCoupon.clear();
        remainCoupon.add("5");
        remainCoupon.add("10");
        remainCoupon.add("100");
        remainCoupon.add("50");
        remainCoupon.add("5000");

        usedCoupon.clear();
        usedCoupon.add("108");
        usedCoupon.add("15");
        usedCoupon.add("30");
        usedCoupon.add("9000");
        usedCoupon.add("2");


        pager.setClipToPadding(false);      //양 옆의 카드 보이게 해주는거
        pager.setPadding(100,0,100,0);      //양 옆의 카드 보이게 해주는거(패딩)
        setPagerAdapter();
        extensiblePageIndicator = (ExtensiblePageIndicator) view. findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(pager);
        return view;
    }

    public static home createInstance(int itemsCount) {
        home Home = new home();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        Home.setArguments(bundle);
        return Home;
    }

    public void onFragmentCreated(int number) { //여기서 쿠폰 갯수 세팅 해줌
        text_home = (TextView) view.findViewById(R.id.text_home);
        viewlayout = (RelativeLayout) view.findViewById(R.id.viewlayout);

        text_home.setText("남은 쿠폰 갯수 : " + remainCoupon.get(number));


    }

    private void setPagerAdapter() {
        //adapter = new homeAdapter(getActivity().getSupportFragmentManager());
        adapter = new homeAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
    }
}
