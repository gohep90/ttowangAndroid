package com.app.ttowang.ttowang.Main.Home.stamp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon.ChangeCoupon;


import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class stamp extends android.support.v4.app.ListFragment {

    public final static String ITEMS_COUNT_KEY = "Main_C$ItemsCount";

    //static ArrayAdapter<String> adapter;
    int number;
    View view;
    public static stampItemAdapter adapter;
    ListView list;
    Button coupon_change_button;
    public View onCreateView(LayoutInflater in, ViewGroup ctn, Bundle savedState)
    {
        Log.i("stamp - ", "초기화");

        view = in.inflate(R.layout.stampview, ctn, false);
        number = getArguments() != null ? getArguments().getInt("number") : 1;
        list = (ListView) view.findViewById(android.R.id.list);

        Log.i("stamp - ", "number : " + number);

        // Adapter 생성
        adapter = new stampItemAdapter() ;

        list.setAdapter(adapter);

        //setListAdapter(adapter);


        //setAddAdapter(((Integer.parseInt((String) home.usedStamp.get(0))/ 10) + 1));    //처음 초기화
        //setAddAdapter(((Integer.parseInt((String) home.usedStamp.get(0))/ 10) + 1));    //처음 초기화


        //list.setSelection((num/ 10));
        //list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        /*
        for(int i=1;i<=((num/ 10) + 1);i++){
            adapter.addItem();
        }
        */
        coupon_change_button = (Button)view.findViewById(R.id.coupon_change_button);
        //adapter = new ArrayAdapter<String>(getActivity(), R.layout.stampitem, home.thisisstamp);

        //쿠폰 바꾸기 버튼 클릭!!
        coupon_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Toast.makeText(MainActivity.mContext, "터치터치", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.mContext, String.valueOf(home.myAllBusiness.get(home.nowbusiness).get(0)), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getContext(), couponPopup.class);
                    Intent intent = new Intent(getContext(), ChangeCoupon.class);
                    intent.putExtra("businessId", String.valueOf(home.myAllBusiness.get(home.nowbusiness).get(0)));
                    startActivity(intent);
                }catch (Exception e){

                }
            }
        });

        return view;
    }

    public static stamp createInstance(int itemsCount) {
        stamp mainC = new stamp();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        mainC.setArguments(bundle);
        return mainC;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("stamp - ", "onCreate");

        try {
            Log.i("stamp - ", "초기화 스템프 리스트 갯수 " + ((Integer.parseInt(home.myAllBusiness.get(number).get(5)) + Integer.parseInt(home.myAllBusiness.get(number).get(4))) / 10));
            //adapter = new ArrayAdapter<String>(getActivity(), R.layout.coupon_item, home.thisisstamp);
            setAddAdapter(((Integer.parseInt(home.myAllBusiness.get(number).get(5)) + Integer.parseInt(home.myAllBusiness.get(number).get(4))) / 10) + 1);    //처음 초기화
            list.setSelection((Integer.parseInt((home.myAllBusiness.get(number).get(5)) + home.myAllBusiness.get(number).get(4)) / 10));      // 처음은 코드로 하단으로 넘어준다
        }catch (Exception e){
            Log.i("stamp - ", "매장 없음");
        }

        //Log.i("stamp - ","초기화 스템프 리스트 갯수 " +(Integer.parseInt(((String) home.usedStamp.get(0))+(String) home.remainStamp.get(0))/ 10));
    }


    /*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "FragmentList position - " +position+" Item clicked: id - " + id,
                Toast.LENGTH_LONG).show();
    }
    */

    public static void setAddAdapter(int a){

        adapter.clearItem();                 //클리어 해주고
        for(int i = 1; i <= a; i++){        //하나씩 추가
            adapter.addItem();
            Log.i("stamp - ","스템프 리스트 추가" + i);
        }
        adapter.notifyDataSetChanged();      //수정됐다고 알림
    }
}
