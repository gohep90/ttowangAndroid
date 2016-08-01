package com.app.ttowang.ttowang.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
    static ArrayList<String> thisisstamp = new ArrayList<String>();
    static ArrayList<String> thisiscoupon = new ArrayList<String>();

    private ViewPager upViewPager, downViewPager;;
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

    PagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.home, container, false);
        view = inflater.inflate(R.layout.home,container, false);
        upViewPager = (ViewPager)view.findViewById(R.id.viewpager);
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

        upViewPager.setClipToPadding(false);      //양 옆의 카드 보이게 해주는거
        upViewPager.setPadding(100,0,100,0);      //양 옆의 카드 보이게 해주는거(패딩)
        setPagerAdapter();
        extensiblePageIndicator = (ExtensiblePageIndicator) view. findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(upViewPager);

////////////////////////////////////////////////////////////////////////////////////

        thisisstamp.add("스탬프1");
        thisisstamp.add("스탬프2");
        thisisstamp.add("스탬프3");
        thisisstamp.add("스탬프4");
        thisisstamp.add("스탬프5");
        thisisstamp.add("스탬프6");
        thisisstamp.add("스탬프7");
        thisisstamp.add("스탬프8");

        thisiscoupon.add("쿠폰1");
        thisiscoupon.add("쿠폰2");
        thisiscoupon.add("쿠폰3");
        thisiscoupon.add("쿠폰4");
        thisiscoupon.add("쿠폰5");
        thisiscoupon.add("쿠폰6");
        thisiscoupon.add("쿠폰7");
        thisiscoupon.add("쿠폰8");

        initViewPagerAndTabs();

        pagerAdapter.notifyDataSetChanged();


        //thisisstamp.add("Chesse"+(position + 1));

        return view;
    }

    private void initViewPagerAndTabs() {

        downViewPager = (ViewPager) view.findViewById(R.id.down_viewPager);
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());

        pagerAdapter.addFragment(stamp.createInstance(0), "스탬프");
        pagerAdapter.addFragment(coupon.createInstance(1), "쿠폰");

        downViewPager.setOffscreenPageLimit(2);
        downViewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.add_tabLayout);
        tabLayout.setupWithViewPager(downViewPager);

        /*
        downViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {        //현재 뷰페이저 번호 가져오기
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Log.i("Add - ", "fragment 번호 = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        */
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

    public static home createInstance(int itemsCount) {
        home Home = new home();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        Home.setArguments(bundle);
        return Home;
    }

    public void onFragmentCreated(int number) { //여기서 쿠폰 갯수 세팅 해줌
        //text_home = (TextView) view.findViewById(R.id.text_home);
        viewlayout = (RelativeLayout) view.findViewById(R.id.viewlayout);

        //text_home.setText("남은 쿠폰 갯수 : " + remainCoupon.get(number));
    }

    private void setPagerAdapter() {
        //adapter = new homeAdapter(getActivity().getSupportFragmentManager());
        adapter = new homeAdapter(getChildFragmentManager());
        upViewPager.setAdapter(adapter);
    }
}
