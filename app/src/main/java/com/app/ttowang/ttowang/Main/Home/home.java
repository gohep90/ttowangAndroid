package com.app.ttowang.ttowang.Main.Home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.ttowang.ttowang.Main.Home.coupon.coupon;
import com.app.ttowang.ttowang.Main.Home.stamp.stamp;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;


public class home extends Fragment implements homeFragment.OnFragmentInteractionListener {

    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    private static ViewPager upViewPager;
    private static ViewPager downViewPager;;

    private static homeAdapter adapter;
    private RelativeLayout viewlayout;
    private ImageView mybusinessimg;


    public static ArrayList<ArrayList<String>> myAllBusiness = new ArrayList<ArrayList<String>>();
    static ArrayList<String> myBusiness;

    public static ArrayList<ArrayList<String>> myAllBusinessCouponNum = new ArrayList<ArrayList<String>>();
    static ArrayList<String> myBusinessCouponNum;

    public static ArrayList<ArrayList<String>> myAllBusinessCouponUse = new ArrayList<ArrayList<String>>();
    static ArrayList<String> myBusinessCouponUse;

    public static ArrayList<ArrayList<String>> myAllBusinessCouponName = new ArrayList<ArrayList<String>>();
    static ArrayList<String> myBusinessCouponName;

    static String state = "first";

    static String userId = MainActivity.user;
    /*
    static List photoName = new ArrayList();     //즐겨찾기 매장 사진
    static List businessId = new ArrayList();     //즐겨찾기 매장 Id
    static List businessName = new ArrayList();     //즐겨찾기 매장 이름
    static List businessLocation = new ArrayList(); //즐겨찾기 매장 위치
    public static List remainStamp = new ArrayList();      //즐겨찾기 매장 사용가능 쿠폰
    public static List usedStamp = new ArrayList();        //즐겨찾기 매장 사용한 쿠폰
*/
//    static List myCoupon = new ArrayList();         //즐겨찾기 매장 내 쿠폰(사용가능한것과 사용한 것 포함)

    static View view;

    static ExtensiblePageIndicator extensiblePageIndicator;

    static PagerAdapter pagerAdapter;

    public static int remainStampNumber,usedStampNumber, myCouponNumber;

    Context context;

    static String encodedString="";

    public static int nowbusiness;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.home, container, false);

        /*
        businessName.clear();
        businessLocation.clear();
        remainStamp.clear();
        usedStamp.clear();
        */


        view = inflater.inflate(R.layout.home,container, false);
        upViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        //text_home = (TextView) view.findViewById(R.id.text_home);


        new selectMyBusinessAsyncTask().execute();
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        /*
        businessName.add("애들아");
        businessName.add("빡세게");
        businessName.add("하고 있니");
        businessName.add("나만");
        businessName.add("하나여..");
*/

/*
        remainStamp.clear();
        remainStamp.add("5");
        remainStamp.add("10");
        remainStamp.add("100");
        remainStamp.add("50");
        remainStamp.add("90");

        usedStamp.clear();
        usedStamp.add("108");
        usedStamp.add("15");
        usedStamp.add("30");
        usedStamp.add("90");
        usedStamp.add("2");
*/

/*
        myCoupon.clear();
        myCoupon.add("3");
        myCoupon.add("5");
        myCoupon.add("10");
        myCoupon.add("7");
        myCoupon.add("20");
*/

        upViewPager.setClipToPadding(false);      //양 옆의 카드 보이게 해주는거
        upViewPager.setPadding(100,0,100,0);      //양 옆의 카드 보이게 해주는거(패딩)
        setPagerAdapter();


////////////////////////////////////////////////////////////////////////////////////

/*
        usedStampNumber = Integer.parseInt((String) usedStamp.get(0));
        stamp.setAddAdapter((usedStampNumber / 10) + 1);
*/
        //initViewPagerAndTabs();

        //pagerAdapter.notifyDataSetChanged();
        //stamp.setAddAdapter(((Integer.parseInt((String) home.usedStamp.get(0))/ 10) + 1));    //처음 초기화
        Log.i("home - ","onCreate");
        return view;
    }

    private static void initViewPagerAndTabs() {

        downViewPager = (ViewPager) view.findViewById(R.id.down_viewPager);
        //pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        //pagerAdapter = new PagerAdapter(getChildFragmentManager());

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

        nowbusiness = number;

        Log.i("home - ","쿠폰 바꿈 : " + String.valueOf(myAllBusiness.get(number).get(1)));
        //text_home = (TextView) view.findViewById(R.id.text_home);
        viewlayout = (RelativeLayout) view.findViewById(R.id.viewlayout);


        remainStampNumber =  Integer.parseInt(String.valueOf(myAllBusiness.get(number).get(4)));

        usedStampNumber = Integer.parseInt(String.valueOf(myAllBusiness.get(number).get(5)));

        myCouponNumber = Integer.parseInt(String.valueOf(myAllBusiness.get(number).get(6)));

        if(MainActivity.first==0){

        }else{
            try {
                stamp.setAddAdapter(((usedStampNumber+remainStampNumber) / 10) + 1);
                Log.i("home - ", "스템프 리스트 갯수 : " + (((usedStampNumber+remainStampNumber) / 10) + 1));
            } catch (Exception e) {

            }
        }

        if(MainActivity.first==0){
            MainActivity.first = 1;
        }else{
            try {
                coupon.setAddAdapter(myCouponNumber);
                Log.i("home - ", "쿠폰 리스트 갯수 : " + myCouponNumber);
            } catch (Exception e) {

            }
        }
    }

    private void setPagerAdapter() {
        //adapter = new homeAdapter(getActivity().getSupportFragmentManager());
        adapter = new homeAdapter(getChildFragmentManager());
        upViewPager.setAdapter(adapter);
    }


////////////////////////////////////////////////////////////////서버 동기화 작업


    public static class selectMyBusinessAsyncTask extends AsyncTask<String,Integer,String> {



        protected void onPreExecute(){
            myAllBusiness.clear();
            myAllBusinessCouponNum.clear();
            myAllBusinessCouponUse.clear();
            myAllBusinessCouponName.clear();
        }

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            String result =recvList();
            return result;
        }

        public String encodeString(Properties params) {  //한글 encoding??
            StringBuffer sb = new StringBuffer(256);
            Enumeration names = params.propertyNames();

            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = params.getProperty(name);
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

                if (names.hasMoreElements()) sb.append("&");
            }
            return sb.toString();
        }

        private String recvList() { //데이터 보내고 받아오기!!

            HttpURLConnection urlConnection=null;
            URL url =null;
            DataOutputStream out=null;
            BufferedInputStream buf=null;
            BufferedReader bufreader=null;

            Properties prop = new Properties();
            prop.setProperty("USERID",userId);

            encodedString = encodeString(prop);

            try{

                url=new URL("http://" + MainActivity.ip + ":8080/ttowang/selectMyBusinesses.do");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                out = new DataOutputStream(urlConnection.getOutputStream());

                out.writeBytes(encodedString);

                out.flush();    //서버로 버퍼의 내용 전송

                buf = new BufferedInputStream(urlConnection.getInputStream());
                bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));

                String line=null;
                String result="";

                while((line=bufreader.readLine())!=null){
                    result += line;
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }finally{
                urlConnection.disconnect();  //URL 연결해제
            }
        }
        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            if(state.equals("first")) {
                jsonFirstList(result);
            }else if(state.equals("refresh")){
                jsonRefreshList(result);
            }

        }
    }

    private static void jsonFirstList(String recv) {

        Log.i("home - ", "서버에서 받은 전체 내용 : " + recv);

        try{
            JSONObject json=new JSONObject(recv);
            JSONArray jArr =json.getJSONArray("list");
            JSONArray jCoupon =json.getJSONArray("coupon");

            Log.i("home - ", "서버에서 받아온 매장 갯수" + jArr.length());

            int i;


            for (i = 0; i < jArr.length(); i++ ) {
                json = jArr.getJSONObject(i);
                myBusiness = new ArrayList<String>();

                myBusiness.add(json.getString("businessId"));
                myBusiness.add(json.getString("businessName"));
                myBusiness.add(json.getString("businessAddress"));
                myBusiness.add(json.getString("photoName"));
                myBusiness.add(json.getString("stampCount"));
                myBusiness.add(String.valueOf(json.getInt("totalStampCount") - json.getInt("stampCount")));
                myBusiness.add("0");

                myAllBusiness.add(myBusiness);


                myBusinessCouponNum = new ArrayList<String>();
                myBusinessCouponUse = new ArrayList<String>();
                myBusinessCouponName = new ArrayList<String>();

                myBusinessCouponNum.add("0");
                myBusinessCouponUse.add("0");
                myBusinessCouponName.add("0");

                myAllBusinessCouponNum.add(myBusinessCouponNum);
                myAllBusinessCouponUse.add(myBusinessCouponUse);
                myAllBusinessCouponName.add(myBusinessCouponName);
            }


            String nowcouponbusinessId = "0";
            int nowcouponbusinessnum = 0;
            int nowcouponbusinesswhere = 0;

            for (i = 0; i < jCoupon.length(); i++ ) {

                json = jCoupon.getJSONObject(i);


                Log.i("home - ","쿠폰있다");

                if(nowcouponbusinessId.equals("0")){        //처음이면
                    nowcouponbusinessId = json.getString("businessId");
                    nowcouponbusinessnum = 1;

                    for(int j=0 ; j<jArr.length(); j++){
                        if(myAllBusiness.get(j).get(0).equals(nowcouponbusinessId)){
                            nowcouponbusinesswhere = j;
                            break;
                        }
                    }

                    myAllBusinessCouponNum.get(nowcouponbusinesswhere).set(0,json.getString("couponNum"));
                    myAllBusinessCouponUse.get(nowcouponbusinesswhere).set(0,json.getString("couponUse"));
                    myAllBusinessCouponName.get(nowcouponbusinesswhere).set(0,json.getString("couponName"));

                    Log.i("home - ","처음매장");
                }else if(!nowcouponbusinessId.equals("0") & nowcouponbusinessId.equals(json.getString("businessId"))){  //처음도 아니고, 기존 매장 쿠폰이면
                    nowcouponbusinessnum += 1;
                    Log.i("home - ","기존매장");

                    myAllBusinessCouponNum.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1),json.getString("couponNum"));
                    myAllBusinessCouponUse.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1),json.getString("couponUse"));
                    myAllBusinessCouponName.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1),json.getString("couponName"));


                }else{      //처음도 아니고, 기존 매장 쿠폰도 아니면


                    myAllBusiness.get(nowcouponbusinesswhere).set(6, String.valueOf(nowcouponbusinessnum));
                    Log.i("home - ","쿠폰 갯수 삽입 " + nowcouponbusinesswhere + "번째 " + nowcouponbusinessnum + "개");

                    nowcouponbusinessId = json.getString("businessId");

                    for(int j=0 ; j<jArr.length(); j++){
                        if(myAllBusiness.get(j).get(0).equals(nowcouponbusinessId)){
                            nowcouponbusinesswhere = j;
                            break;
                        }
                    }
                    myAllBusinessCouponNum.get(nowcouponbusinesswhere).add(0,json.getString("couponNum"));
                    myAllBusinessCouponUse.get(nowcouponbusinesswhere).add(0,json.getString("couponUse"));
                    myAllBusinessCouponName.get(nowcouponbusinesswhere).add(0,json.getString("couponName"));
                    //Log.i("home - ",nowcouponbusinessId + "번째 매장 " + nowcouponbusinessnum + "개 쿠폰");

                    nowcouponbusinessnum = 1;
                    Log.i("home - ","새로운매장");
                }

                if(i == (jCoupon.length()-1) & nowcouponbusinessnum != 0){
                    for(int j=0 ; j<jArr.length(); j++){
                        if(myAllBusiness.get(j).get(0).equals(nowcouponbusinessId)){
                            myAllBusiness.get(j).set(6, String.valueOf(nowcouponbusinessnum));

                            Log.i("home - ","쿠폰 갯수 삽입 " + j + "번째 " + nowcouponbusinessnum + "개");

                            if(nowcouponbusinessnum == 0) {
                                myAllBusinessCouponNum.get(nowcouponbusinesswhere).set(0, json.getString("couponNum"));
                                myAllBusinessCouponUse.get(nowcouponbusinesswhere).set(0, json.getString("couponUse"));
                                myAllBusinessCouponName.get(nowcouponbusinesswhere).set(0, json.getString("couponName"));
                            }else{
                                myAllBusinessCouponNum.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1), json.getString("couponNum"));
                                myAllBusinessCouponUse.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1), json.getString("couponUse"));
                                myAllBusinessCouponName.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1), json.getString("couponName"));
                            }
                            break;
                        }
                    }
                }
            }

            adapter.notifyDataSetChanged();     //리스트
            //     mLockListView=false;
            extensiblePageIndicator = (ExtensiblePageIndicator) view. findViewById(R.id.flexibleIndicator);
            extensiblePageIndicator.initViewPager(upViewPager);

            initViewPagerAndTabs();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }


    private static void jsonRefreshList(String recv) {

        Log.i("home - ", "서버에서 받은 전체 내용 : " + recv);

        try{
            JSONObject json=new JSONObject(recv);
            JSONArray jArr =json.getJSONArray("list");
            JSONArray jCoupon =json.getJSONArray("coupon");

            Log.i("home - ", "서버에서 받아온 매장 갯수" + jArr.length());

            int i;

            for (i = 0; i < jArr.length(); i++ ) {
                json = jArr.getJSONObject(i);
                myBusiness = new ArrayList<String>();

                myBusiness.add(json.getString("businessId"));
                myBusiness.add(json.getString("businessName"));
                myBusiness.add(json.getString("businessAddress"));
                myBusiness.add(json.getString("photoName"));
                myBusiness.add(json.getString("stampCount"));
                myBusiness.add(String.valueOf(json.getInt("totalStampCount") - json.getInt("stampCount")));
                myBusiness.add("0");

                myAllBusiness.add(myBusiness);


                myBusinessCouponNum = new ArrayList<String>();
                myBusinessCouponUse = new ArrayList<String>();
                myBusinessCouponName = new ArrayList<String>();

                myBusinessCouponNum.add("0");
                myBusinessCouponUse.add("0");
                myBusinessCouponName.add("0");

                myAllBusinessCouponNum.add(myBusinessCouponNum);
                myAllBusinessCouponUse.add(myBusinessCouponUse);
                myAllBusinessCouponName.add(myBusinessCouponName);
            }


            String nowcouponbusinessId = "0";
            int nowcouponbusinessnum = 0;
            int nowcouponbusinesswhere = 0;

            for (i = 0; i < jCoupon.length(); i++ ) {

                json = jCoupon.getJSONObject(i);


                Log.i("home - ","쿠폰있다");

                if(nowcouponbusinessId.equals("0")){        //처음이면
                    nowcouponbusinessId = json.getString("businessId");
                    nowcouponbusinessnum = 1;

                    for(int j=0 ; j<jArr.length(); j++){
                        if(myAllBusiness.get(j).get(0).equals(nowcouponbusinessId)){
                            nowcouponbusinesswhere = j;
                            break;
                        }
                    }

                    myAllBusinessCouponNum.get(nowcouponbusinesswhere).set(0,json.getString("couponNum"));
                    myAllBusinessCouponUse.get(nowcouponbusinesswhere).set(0,json.getString("couponUse"));
                    myAllBusinessCouponName.get(nowcouponbusinesswhere).set(0,json.getString("couponName"));

                    Log.i("home - ","처음매장");
                }else if(!nowcouponbusinessId.equals("0") & nowcouponbusinessId.equals(json.getString("businessId"))){  //처음도 아니고, 기존 매장 쿠폰이면
                    nowcouponbusinessnum += 1;
                    Log.i("home - ","기존매장");

                    myAllBusinessCouponNum.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1),json.getString("couponNum"));
                    myAllBusinessCouponUse.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1),json.getString("couponUse"));
                    myAllBusinessCouponName.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1),json.getString("couponName"));


                }else{      //처음도 아니고, 기존 매장 쿠폰도 아니면


                    myAllBusiness.get(nowcouponbusinesswhere).set(6, String.valueOf(nowcouponbusinessnum));
                    Log.i("home - ","쿠폰 갯수 삽입 " + nowcouponbusinesswhere + "번째 " + nowcouponbusinessnum + "개");

                    nowcouponbusinessId = json.getString("businessId");

                    for(int j=0 ; j<jArr.length(); j++){
                        if(myAllBusiness.get(j).get(0).equals(nowcouponbusinessId)){
                            nowcouponbusinesswhere = j;
                            break;
                        }
                    }
                    myAllBusinessCouponNum.get(nowcouponbusinesswhere).add(0,json.getString("couponNum"));
                    myAllBusinessCouponUse.get(nowcouponbusinesswhere).add(0,json.getString("couponUse"));
                    myAllBusinessCouponName.get(nowcouponbusinesswhere).add(0,json.getString("couponName"));
                    //Log.i("home - ",nowcouponbusinessId + "번째 매장 " + nowcouponbusinessnum + "개 쿠폰");

                    nowcouponbusinessnum = 1;
                    Log.i("home - ","새로운매장");
                }

                if(i == (jCoupon.length()-1) & nowcouponbusinessnum != 0){
                    for(int j=0 ; j<jArr.length(); j++){
                        if(myAllBusiness.get(j).get(0).equals(nowcouponbusinessId)){
                            myAllBusiness.get(j).set(6, String.valueOf(nowcouponbusinessnum));

                            Log.i("home - ","쿠폰 갯수 삽입 " + j + "번째 " + nowcouponbusinessnum + "개");

                            if(nowcouponbusinessnum == 0) {
                                myAllBusinessCouponNum.get(nowcouponbusinesswhere).set(0, json.getString("couponNum"));
                                myAllBusinessCouponUse.get(nowcouponbusinesswhere).set(0, json.getString("couponUse"));
                                myAllBusinessCouponName.get(nowcouponbusinesswhere).set(0, json.getString("couponName"));
                            }else{
                                myAllBusinessCouponNum.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1), json.getString("couponNum"));
                                myAllBusinessCouponUse.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1), json.getString("couponUse"));
                                myAllBusinessCouponName.get(nowcouponbusinesswhere).add((nowcouponbusinessnum-1), json.getString("couponName"));
                            }
                            break;
                        }
                    }
                }
            }

            adapter.notifyDataSetChanged();     //리스트
            pagerAdapter.notifyDataSetChanged();
            //     mLockListView=false;

            extensiblePageIndicator.initViewPager(upViewPager);
            extensiblePageIndicator.refreshDrawableState();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    final public static void refresh(){
        state = "refresh";
        new selectMyBusinessAsyncTask().execute();
        //adapter.notifyDataSetChanged();
        Log.i("home - ","리프레쉬 한다");
    }

}
