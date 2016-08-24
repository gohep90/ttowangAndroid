package com.app.ttowang.ttowang.ModeChange.Recent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.ModeChange.Recent.Network.NetworkModel;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.recentList;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;

public class recentActivity extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";
    View view;
    ImageView search;
    ListView listRecent;
    ArrayList<recent> ArRecentList = new ArrayList<recent>();
    TextView seqTV,userNameTV,stampDateTV,stampTimeTV,cancelTV;

    public static recentActivity createInstance(int itemsCount) {
        recentActivity recent = new recentActivity();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        recent.setArguments(bundle);
        return recent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent,container, false);
        search = (ImageView) view.findViewById(R.id.search);

        listRecent = (ListView) view.findViewById(R.id.recentListView);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
            }
        });

        initHomeMainList();
        return view;
    }

    AdapterView.OnItemClickListener MainItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(getActivity(),Integer.toString(position), Toast.LENGTH_SHORT).show();
        }
    };

    public class recentAdapter extends BaseAdapter {
        Context BUSINESSID;
        LayoutInflater inflacter;
        ArrayList<recent> ArRecentList = null;
        int layout;

        recentAdapter(Context context, int alayout, ArrayList<recent> aarSrc) {
            BUSINESSID = context;
            inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = alayout;
            ArRecentList = aarSrc;
        }

        public int getCount() {
            return ArRecentList.size();
        }

        public String getItem(int position) {
            return ArRecentList.get(position).businessId;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if (convertView == null)
                convertView = inflacter.inflate(layout, parent, false);
            seqTV = (TextView)convertView.findViewById(R.id.recentSeq);
            seqTV.setText(ArRecentList.get(position).seq);
            userNameTV = (TextView)convertView.findViewById(R.id.recentName);
            userNameTV.setText(ArRecentList.get(position).businessId);
            stampDateTV = (TextView)convertView.findViewById(R.id.recentDate);
            stampDateTV.setText(ArRecentList.get(position).userId);

            return convertView;
        }
    };

    private void initHomeMainList() {
        NetworkModel.getInstance().getRecent(new NetworkModel.OnNetworkResultListener<recentList>() {
            @Override
            public void onResult(recentList recentList) {
                String seq,userName,businessId;
                recent recent;
                for (int i = 0 ; i < recentList.getStampList().size() ; i++ ) {
                    seq = String.valueOf(i+1);
                    Log.i("지은", String.valueOf(seq));
                    userName = recentList.getStampList().get(i).getBusinessId();
                    businessId = recentList.getStampList().get(i).getUserId();
                    recent = new recent(seq,userName,businessId);
                    ArRecentList.add(recent);
                }

                seq = "8";
                userName = "홍길동";
                businessId = "2017/8/19";
                recent = new recent(seq,userName,businessId);
                ArRecentList.add(recent);

                recentAdapter baseMenuAdapter = new recentAdapter(getContext(), R.layout.recent_item,ArRecentList);
                listRecent.setAdapter(baseMenuAdapter);
                listRecent.setOnItemClickListener(MainItemClickListener);
            }
            @Override
            public void onFail(int code) {
            }
        });
    }
}