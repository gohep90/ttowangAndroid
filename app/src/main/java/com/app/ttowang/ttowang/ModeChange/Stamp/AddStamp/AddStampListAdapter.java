package com.app.ttowang.ttowang.ModeChange.Stamp.AddStamp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon.ChangeCouponItem;
import com.app.ttowang.ttowang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddStampListAdapter extends BaseAdapter {

    ArrayList<AddStampListData> datas;
    LayoutInflater inflater;

    public AddStampListAdapter(LayoutInflater inflater, ArrayList<AddStampListData> datas) {
        // TODO Auto-generated constructor stub

        this.datas= datas;
        this.inflater= inflater;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size(); //datas의 개수를 리턴
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);//datas의 특정 인덱스 위치 객체 리턴.
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Context context = parent.getContext();

        if( convertView==null){
            convertView= inflater.inflate(R.layout.addstamplist_item, null);
        }

        final TextView text_tel= (TextView)convertView.findViewById(R.id.addstamp_tel);
        final TextView text_name= (TextView)convertView.findViewById(R.id.addstamp_name);

        text_tel.setText( datas.get(position).getTel() );
        text_name.setText( datas.get(position).getName() );
        final String businessId = datas.get(position).getBusinessId();

        text_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddStamp.class);
                intent.putExtra("userTel", text_tel.getText().toString());
                intent.putExtra("userName", text_name.getText().toString());
                intent.putExtra("businessId", businessId);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        text_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddStamp.class);
                intent.putExtra("userTel", text_tel.getText().toString());
                intent.putExtra("userName", text_name.getText().toString());
                intent.putExtra("businessId", businessId);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        return convertView;
    }
}
