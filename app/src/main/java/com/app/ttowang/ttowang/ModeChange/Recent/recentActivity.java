package com.app.ttowang.ttowang.ModeChange.Recent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;
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
import android.widget.AdapterView.OnItemSelectedListener;

import com.app.ttowang.ttowang.ModeChange.MyShop.KeyValueArrayAdapter;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.NetworkModel;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.recentList;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.recentSpinnerList;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;

public class recentActivity extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";
    View view;
    ImageView search;
    ListView listRecent;
    ArrayList<recent> ArRecentList = new ArrayList<recent>();

    ArrayList<String> ArRecentSpinnerKey = new ArrayList<>();
    ArrayList<String> ArRecentSpinnerValue = new ArrayList<>();

    String businessId = null;
    String userId = "5";
    recentAdapter baseListAdapter;
    KeyValueArrayAdapter baseSelectAdapter = null;
    AlertDialog.Builder recentDeleteConfirm;
    AlertDialog.Builder recentConfirm;
    Spinner spinner;

    public static recentActivity createInstance(int itemsCount) {
        recentActivity recent = new recentActivity();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        recent.setArguments(bundle);

        return recent;
    }

    public void onStart(){
        super.onStart();

        initSpinner(userId);
    }

    public void onResume() {
        super.onResume();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "검색검색.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent,container, false);
        search = (ImageView) view.findViewById(R.id.search);
        listRecent = (ListView) view.findViewById(R.id.recentListView);
        spinner = (Spinner) view.findViewById(R.id.recentSpinner);

        baseListAdapter = new recentAdapter(getContext(), R.layout.recent_item, ArRecentList);

        recentDeleteConfirm = new AlertDialog.Builder(getActivity());
        recentConfirm = new AlertDialog.Builder(getActivity());

        return view;
    }

    private void initRecentList(String BUSINESSID) {
        NetworkModel.getInstance().getSelectRecent(new NetworkModel.OnNetworkResultListener<recentList>() {
            @Override
            public void onResult(recentList recentList) {
                String seq, userName, stampDate, stampTime;
                recent recent;
                for (int i = 0; i < recentList.getStampList().size(); i++) {
                    seq = String.valueOf(i + 1);
                    userName = recentList.getStampList().get(i).getUserName();
                    //if(userName.length()>3)
                    //userNameTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    stampDate = recentList.getStampList().get(i).getStampDate();
                    stampTime = recentList.getStampList().get(i).getStampTime();

                    recent = new recent(seq, userName, stampDate, stampTime);
                    ArRecentList.add(recent);
                }

                listRecent.setAdapter(baseListAdapter);
            }

            @Override
            public void onFail(int code) {
            }
        }, BUSINESSID);
    }

    private void deleteRecentList(String USERID,String BUSINESSID,String STAMPDATE,String STAMPNUM) {
        NetworkModel.getInstance().getDeleteRecent(new NetworkModel.OnNetworkResultListener<recentList>() {
            @Override
            public void onResult(recentList result) {
            }

            @Override
            public void onFail(int code) {
                Log.i("지은", "DELETE 실패");
            }
        }, USERID, BUSINESSID, STAMPDATE, STAMPNUM);
    }

    public void AdapterClear() {
        ArRecentList.clear();
    }

    private void initSpinner(String USERID) {
        NetworkModel.getInstance().getSelectSpinner(new NetworkModel.OnNetworkResultListener<recentSpinnerList>() {
            @Override
            public void onResult(recentSpinnerList recentSpinnerList) {
                for (int i = 0; i < recentSpinnerList.getSpinner().size(); i++) {
                    ArRecentSpinnerKey.add(recentSpinnerList.getSpinner().get(i).getBusinessId());
                    Log.i("지은 업소명",recentSpinnerList.getSpinner().get(i).getBusinessName());
                    ArRecentSpinnerValue.add(recentSpinnerList.getSpinner().get(i).getBusinessName());
                }
                setSpinnerAdapter(ArRecentSpinnerKey, ArRecentSpinnerValue);
            }

            @Override
            public void onFail(int code) {
            }
        }, USERID);
    }

    private void setSpinnerAdapter(ArrayList<String> key,ArrayList<String> value) {
        if (null != key) {
            if (null == baseSelectAdapter) {
                baseSelectAdapter = new KeyValueArrayAdapter(getContext(),R.layout.spinner_item);

                baseSelectAdapter.setEntries(ArRecentSpinnerKey.toArray(new String[ArRecentSpinnerKey.size()]));
                baseSelectAdapter.setEntryValues(ArRecentSpinnerValue.toArray(new String[ArRecentSpinnerValue.size()]));

                spinner.setAdapter(baseSelectAdapter);

                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) parent.getAdapter();

                        businessId = adapter.getEntry(position);

                        initRecentList(businessId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else{}
        }
    }

    public class recentAdapter extends BaseAdapter{
        Context seq;
        LayoutInflater inflacter;
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
            TextView seqTV = (TextView)convertView.findViewById(R.id.recentSeq);
            seqTV.setText(ArRecentList.get(position).seq);
            TextView userNameTV = (TextView)convertView.findViewById(R.id.recentName);
            userNameTV.setText(ArRecentList.get(position).userName);

            //userNameTV.setTextSize(getResources().getDimension(R.dimen.textsize));

            TextView stampDateTV = (TextView)convertView.findViewById(R.id.recentDate);
            stampDateTV.setText(ArRecentList.get(position).stampDate);
            TextView stampTimeTV = (TextView)convertView.findViewById(R.id.recentTime);
            stampTimeTV.setText(ArRecentList.get(position).stampTime);

            TextView cancelTV = (TextView) convertView.findViewById(R.id.recentCancel);
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

                                        ArRecentList.clear();

                                        initRecentList(businessId);
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
}
