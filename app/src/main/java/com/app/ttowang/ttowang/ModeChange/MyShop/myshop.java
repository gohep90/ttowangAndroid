package com.app.ttowang.ttowang.ModeChange.MyShop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessEvent.myBusinessEvent;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessMember.myBusinessMember;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessCoupon.myBusinessCoupon;
import com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop.myBusinessShop;
import com.app.ttowang.ttowang.R;


public class myshop extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    Button modeChange,mybusinesscoupon,mybusinessmember,mybusinessshop,mybusinessevent;

    public static myshop createInstance(int itemsCount) {
        myshop myshop = new myshop();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        myshop.setArguments(bundle);
        return myshop;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.myshop,container, false);
        mybusinesscoupon = (Button) view.findViewById(R.id.mybusinesscoupon);
        mybusinessmember = (Button) view.findViewById(R.id.mybusinessmember);
        mybusinessshop = (Button)view.findViewById(R.id.mybusinessshop);
        mybusinessevent = (Button)view.findViewById(R.id.mybusinessevent);

        mybusinesscoupon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangeModeMain.mContext,myBusinessCoupon.class));
            }
        });

        mybusinessshop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangeModeMain.mContext,myBusinessShop.class));
            }
        });

        mybusinessmember.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangeModeMain.mContext,myBusinessMember.class));
            }
        });

        mybusinessevent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangeModeMain.mContext,myBusinessEvent.class));
            }
        });

        /*
        modeChange = (Button) view.findViewById(R.id.modeChange);


        modeChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "체인지모드.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.mContext,ChangeModeMain.class));
            }
        });
        */

        return view;
    }
}
