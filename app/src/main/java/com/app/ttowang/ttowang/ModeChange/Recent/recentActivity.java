package com.app.ttowang.ttowang.ModeChange.Recent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.app.ttowang.ttowang.ModeChange.MyShop.KeyValueArrayAdapter;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.NetworkModel;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.recentList;
import com.app.ttowang.ttowang.ModeChange.Recent.Network.recentSpinnerList;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;

public class recentActivity extends Fragment {
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", getActivity().MODE_PRIVATE);
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";
    View view;
    ListView listRecent;
    EditText inputSearch;
    ArrayList<recent> ArRecentList = new ArrayList<recent>();
    ArrayList<recent> OriginalList = new ArrayList<recent>();

    ArrayList<String> ArRecentSpinnerKey = new ArrayList<>();
    ArrayList<String> ArRecentSpinnerValue = new ArrayList<>();

    public String businessId = null;
    int userId = sharedPreferences.getInt("userId", 0);;
    recentAdapter baseListAdapter;
    KeyValueArrayAdapter baseSelectAdapter = null;
    AlertDialog.Builder recentDeleteConfirm;
    AlertDialog.Builder recentConfirm;
    Spinner spinner;
    boolean change = false;

    public static recentActivity createInstance(int itemsCount) {
        recentActivity recent = new recentActivity();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        recent.setArguments(bundle);

        return recent;
    }

    public void onStart() {
        super.onStart();

        initSpinner(String.valueOf(userId));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent, container, false);

        listRecent = (ListView) view.findViewById(R.id.recentListView);
        inputSearch = (EditText) view.findViewById(R.id.recentInputSearch);
        spinner = (Spinner) view.findViewById(R.id.recentSpinner);

        baseListAdapter = new recentAdapter(getContext(), R.layout.recent_item, ArRecentList);

        recentDeleteConfirm = new AlertDialog.Builder(getActivity());
        recentConfirm = new AlertDialog.Builder(getActivity());

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                recentActivity.this.baseListAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) { }
        });

        return view;
    }


    /**
     * (최근스탬프리스트) SERVER에서 RECENT data를 가지고 와서 Adapter로 뿌려줌
     * @param BUSINESSID
     */
    private void initRecentList(String BUSINESSID) {
        if (change == true ) {
            change = false;
            AdapterClear();
        }
        NetworkModel.getInstance().getSelectRecent(new NetworkModel.OnNetworkResultListener<recentList>() {
            @Override
            public void onResult(recentList recentList) {
                String seq, userName, stampDate, stampTime, userTel;
                recent recent;
                for (int i = 0; i < recentList.getStampList().size(); i++) {
                    seq = String.valueOf(i + 1);
                    userName = recentList.getStampList().get(i).getUserName();
                    //if(userName.length()>3)
                    //userNameTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    stampDate = recentList.getStampList().get(i).getStampDate();
                    stampTime = recentList.getStampList().get(i).getStampTime();
                    userTel = recentList.getStampList().get(i).getUserTel();

                    recent = new recent(seq, userName, stampDate, stampTime,userTel);
                    ArRecentList.add(recent);
                }

                OriginalList = ArRecentList;
                listRecent.setAdapter(baseListAdapter);
            }

            @Override
            public void onFail(int code) {
            }
        }, BUSINESSID);
    }


    /**
     * (최근스탬프리스트) 해당 정보를 SERVER에서 삭제
     * @param USERID
     * @param BUSINESSID
     * @param STAMPDATE
     * @param STAMPNUM
     */
    private void deleteRecentList(String USERID, String BUSINESSID, String STAMPDATE, String STAMPNUM) {
        NetworkModel.getInstance().getDeleteRecent(USERID, BUSINESSID, STAMPDATE, STAMPNUM);
    }


    /**
     * (스피너) SERVER에서 해당 USER의 담당 매장 list가지고 오기
     * @param USERID
     */
    private void initSpinner(String USERID) {
        NetworkModel.getInstance().getSelectSpinner(new NetworkModel.OnNetworkResultListener<recentSpinnerList>() {
            @Override
            public void onResult(recentSpinnerList recentSpinnerList) {
                for (int i = 0; i < recentSpinnerList.getSpinner().size(); i++) {
                    ArRecentSpinnerKey.add(recentSpinnerList.getSpinner().get(i).getBusinessId());
                    ArRecentSpinnerValue.add(recentSpinnerList.getSpinner().get(i).getBusinessName());
                }
                setSpinnerAdapter(ArRecentSpinnerKey, ArRecentSpinnerValue);
            }

            @Override
            public void onFail(int code) {
            }
        }, USERID);
    }

    /**
     * (최근스탬프리스트) 변경에 따라 list clear Action
     */
    public void AdapterClear() {
        Log.i("지은", "=== 변경변경 ===");

        ArRecentList.clear();
        OriginalList.clear();

        if(!inputSearch.getText().toString().matches("")) {
            inputSearch.setText(null);
        }
    }


    /**
     * (스피너) Spinner Adapter
     * @param key
     * @param value
     */
    private void setSpinnerAdapter(ArrayList<String> key, ArrayList<String> value) {
        if (null != key) {
            if (null == baseSelectAdapter) {
                baseSelectAdapter = new KeyValueArrayAdapter(getContext(), R.layout.spinner_item);

                baseSelectAdapter.setEntries(ArRecentSpinnerKey.toArray(new String[ArRecentSpinnerKey.size()]));
                baseSelectAdapter.setEntryValues(ArRecentSpinnerValue.toArray(new String[ArRecentSpinnerValue.size()]));

                spinner.setAdapter(baseSelectAdapter);

                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) parent.getAdapter();

                        businessId = adapter.getEntry(position);
                        change = true;

                        Log.i("지은","Spinner 변경!!!");

                        if(!inputSearch.getText().toString().matches("")) {
                            inputSearch.setText(null);
                        }

                        initRecentList(businessId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else {
            }
        }
    }


    /**
     * (최근스탬프리스트) 적립리스트 Adapter
     */
    public class recentAdapter extends BaseAdapter {
        ArrayList<recent> filterList = new ArrayList<recent>();
        private Filter filter;

        Context seq;
        LayoutInflater inflacter;
        int layout;

        recentAdapter(Context context, int alayout, ArrayList<recent> aarSrc) {
            seq = context;
            inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = alayout;

            ArRecentList = aarSrc;
            filterList = aarSrc;
        }

        public int getCount() {
            return ArRecentList.size();
        }

        public String getItem(int position) {
            return ArRecentList.get(position).seq;
        }

        public long getItemId(int position) {
            return ArRecentList.get(position).hashCode();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if (convertView == null)
                convertView = inflacter.inflate(layout, parent, false);

            TextView seqTV = (TextView) convertView.findViewById(R.id.recentSeq);
            TextView userNameTV = (TextView) convertView.findViewById(R.id.recentName);
            TextView stampDateTV = (TextView) convertView.findViewById(R.id.recentDate);
            TextView stampTimeTV = (TextView) convertView.findViewById(R.id.recentTime);
            TextView cancelTV = (TextView) convertView.findViewById(R.id.recentCancel);

            seqTV.setText(ArRecentList.get(position).seq);
            userNameTV.setText(ArRecentList.get(position).userName);
            stampDateTV.setText(ArRecentList.get(position).stampDate);
            stampTimeTV.setText(ArRecentList.get(position).stampTime);

            //userNameTV.setTextSize(getResources().getDimension(R.dimen.textsize));

            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int SEQ = Integer.valueOf(ArRecentList.get(pos).getSeq()) - 1;
                    Log.i("지은","몇번째 클릭한건지 ? " + String.valueOf(SEQ));

                    if (recentList.stampList.get(SEQ).getCouponUsing().equals("FALSE")) {
                        recentDeleteConfirm.setMessage("적립을 취소하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String stampDate = recentList.stampList.get(SEQ).getStampDate().replace("/", "-");
                                        deleteRecentList(recentList.stampList.get(SEQ).getUserId(), businessId,
                                                stampDate, recentList.stampList.get(SEQ).getStampNum());

                                        change = true;

                                        baseListAdapter.notifyDataSetChanged();
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

        public Filter getFilter() {
            Log.i("지은", "Before Filter1");
            return new recentFilterSearch();
        }

        public class recentFilterSearch extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String constraintStr = constraint.toString();
                FilterResults result = new FilterResults();

                Log.i("지은", "Before Filter2");

                if (constraint != null && constraint.toString().length() > 0) {
                    Log.i("지은", "Before Filter3");
                    ArrayList<recent> filterItems = new ArrayList<recent>();
                    filterItems.clear();

                    synchronized (this) {
                        Log.i("지은", "Before Filter4");
                        Log.i("지은", "====================입력한 문구 = "+constraintStr);
                        for (recent recent : ArRecentList) {
                            Log.i("지은",recent.userName+" "+recent.userTel);
                            if (recent.userTel.contains(constraintStr)) {
                                Log.i("지은", "Before Filter4-1");
                                filterItems.add(recent);
                            }
                        }
                        Log.i("지은", "Before Filter5");
                        result.count = filterItems.size();
                        result.values = filterItems;

                        Log.i("지은","ADD된 ItemList NAME = " + filterItems.get(0).getUserName());
                    }
                } else {
                    synchronized (this) {
                        result.count = OriginalList.size();
                        result.values = new ArrayList<recent>(OriginalList);
                    }
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.i("지은", "Before Filter6 results.count = " + results.count);
                if(results.count == 0) {
                    ArRecentList.clear();
                    Toast.makeText(getActivity(), "등록한 회원이 없습니다", Toast.LENGTH_LONG).show();
                    Log.i("지은",String.valueOf(change));
                    if(inputSearch.getText().toString().matches("")) {
                        Log.i("지은", "EditText는 NULL이당");
                        change = true;
                        initRecentList(businessId);
                    }
                }
                else {
                    ArRecentList = (ArrayList<recent>) results.values;
                    Log.i("지은", String.valueOf(ArRecentList.size()));
                    //notifyDataSetChanged();
                    listRecent.setAdapter(baseListAdapter);
                    for(int i=0;i<ArRecentList.size();i++){
                        Log.i("지은", "PublishResults [" + i + "] : " + ArRecentList.get(i).getUserName());
                    }
                }
            }
        }
    }
}
