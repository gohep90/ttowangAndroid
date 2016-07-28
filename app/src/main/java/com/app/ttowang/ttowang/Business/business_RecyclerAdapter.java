package com.app.ttowang.ttowang.Business;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.app.ttowang.ttowang.R;

import java.util.List;

public class business_RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;
    String[] apm={"오","전","오","후","5","6","7","8","9"};
    String[] time={"1111","2222","3333","4444","5555","6666","7","8","9"};
    String[] day={"월 수","학교가기","시러","4","5","6","7","8","9"};
    String[] location={"집","홍대","서울시 성동구 성수1가2동","4","5","6","7","8","9"};

    public business_RecyclerAdapter(List<String> itemList) {
        mItemList = itemList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.business_item, parent, false);
        return new business_RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        business_RecyclerItemViewHolder holder = (business_RecyclerItemViewHolder) viewHolder;

    }

    @Override
    public int getItemCount() {
        return apm.length;      //배열 길이 만큼
    }


}
