package com.app.ttowang.ttowang.ModeChange.Recent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String businessId = "1";
    recentAdapter baseMenuAdapter;
    AlertDialog.Builder recentDeleteConfirm;
    AlertDialog.Builder recentConfirm;

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

        baseMenuAdapter = new recentAdapter(getContext(), R.layout.recent_item, ArRecentList);

        recentDeleteConfirm = new AlertDialog.Builder(getActivity());
        recentConfirm = new AlertDialog.Builder(getActivity());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
            }
        });

        initRecentList();
        return view;
    }

    public class recentAdapter extends BaseAdapter{
        Context seq;
        LayoutInflater inflacter;
        ArrayList<recent> ArRecentList = null;
        int layout;

        recentAdapter(Context context, int alayout, ArrayList<recent> aarSrc) {
            seq = context;
            inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = alayout;
            ArRecentList = aarSrc;
        }

        public int getCount() {
            return ArRecentList.size();
        }

        public String getItem(int position) {
            return ArRecentList.get(position).seq;
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
            userNameTV.setText(ArRecentList.get(position).userName);
            stampDateTV = (TextView)convertView.findViewById(R.id.recentDate);
            stampDateTV.setText(ArRecentList.get(position).stampDate);
            stampTimeTV = (TextView)convertView.findViewById(R.id.recentTime);
            stampTimeTV.setText(ArRecentList.get(position).stampTime);

            cancelTV = (TextView) convertView.findViewById(R.id.recentCancel);
            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recentList.stampList.get(pos).getCouponUsing().equals("FALSE")) {
                        recentDeleteConfirm.setMessage("적립을 취소하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String stampDate = recentList.stampList.get(pos).getStampDate().replace("/", "-");
                                        deleteRecentList(recentList.stampList.get(pos).getUserId(), businessId,
                                                stampDate, recentList.stampList.get(pos).getStampNum());

                                        Log.i("지은 2 / ", recentList.stampList.get(0).getStampDate());
                                        ArRecentList.clear();
                                        recentList.stampList.clear();

                                        Log.i("지은 3 / 사이즈 = ", String.valueOf(recentList.stampList.size()));

                                        initRecentList();
                                    }
                                }).setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                });
                        AlertDialog alert = recentDeleteConfirm.create();
                        alert.show();
                    } else {
                        recentConfirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        recentConfirm.setMessage("이미 쿠폰으로 변경된 적립정보입니다.");
                        recentConfirm.show();
                    }
                }
            });
            return convertView;
        }
    };

    private void initRecentList() {
        NetworkModel.getInstance().getSelectRecent(new NetworkModel.OnNetworkResultListener<recentList>() {
            @Override
            public void onResult(recentList recentList) {
                String seq, userName, stampDate,stampTime;
                recent recent;
                for (int i = 0; i < recentList.getStampList().size(); i++) {
                    seq = String.valueOf(i + 1);
                    userName = recentList.getStampList().get(i).getUserName();
                    stampDate = recentList.getStampList().get(i).getStampDate();
                    stampTime = recentList.getStampList().get(i).getStampTime();

                    recent = new recent(seq, userName, stampDate, stampTime);
                    ArRecentList.add(recent);
                }

                Log.i("지은 1 / ", ArRecentList.get(0).getStampDate());
                listRecent.setAdapter(baseMenuAdapter);
            }

            @Override
            public void onFail(int code) {
            }
        }, businessId);
    }

    private void deleteRecentList(String USERID,String BUSINESSID,String STAMPDATE,String STAMPNUM) {
        NetworkModel.getInstance().getDeleteRecent(new NetworkModel.OnNetworkResultListener<recentList>() {
            @Override
            public void onResult(recentList recentList) {
            }

            @Override
            public void onFail(int code) {
            }
        }, USERID, BUSINESSID, STAMPDATE, STAMPNUM);
    }
}
