package com.app.ttowang.ttowang.Event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;
import java.util.List;

public class eventMain extends Fragment {

    public final static String ITEMS_COUNT_KEY = "eventMain$ItemsCount";

    public static eventMain createInstance(int itemsCount) {
        eventMain EventMain = new eventMain();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        EventMain.setArguments(bundle);
        return EventMain;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.event_main, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        event_RecyclerAdapter recyclerAdapter_alarm = new event_RecyclerAdapter(createItemList());
        recyclerView.setAdapter(recyclerAdapter_alarm);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle!=null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < 4; i++) {
                itemList.add(i+"");
            }
        }
        return itemList;
    }


}
