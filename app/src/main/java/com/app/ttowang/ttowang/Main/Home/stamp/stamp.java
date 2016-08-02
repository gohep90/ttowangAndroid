package com.app.ttowang.ttowang.Main.Home.stamp;

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
public class stamp extends android.support.v4.app.ListFragment {

    public final static String ITEMS_COUNT_KEY = "Main_C$ItemsCount";

    //static ArrayAdapter<String> adapter;
    int number;
    View view;
    public static stampItemAdapter adapter;
    ListView list;

    public View onCreateView(LayoutInflater in, ViewGroup ctn, Bundle savedState)
    {
        Log.i("stamp - ", "초기화");
        view = in.inflate(R.layout.stampview, ctn, false);
        number = getArguments() != null ? getArguments().getInt("number") : 1;
        list = (ListView) view.findViewById(android.R.id.list);

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

        //adapter = new ArrayAdapter<String>(getActivity(), R.layout.stampitem, home.thisisstamp);
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

        //adapter = new ArrayAdapter<String>(getActivity(), R.layout.coupon_item, home.thisisstamp);
        setAddAdapter(((Integer.parseInt((String) home.usedStamp.get(0)) + Integer.parseInt((String) home.remainStamp.get(0)))/10) +1);    //처음 초기화

        list.setSelection((Integer.parseInt(((String) home.usedStamp.get(0))+(String) home.remainStamp.get(0))/ 10));      // 처음은 코드로 하단으로 넘어준다
        Log.i("stamp - ","초기화 스템프 리스트 갯수 " +(Integer.parseInt(((String) home.usedStamp.get(0))+(String) home.remainStamp.get(0))/ 10));
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
