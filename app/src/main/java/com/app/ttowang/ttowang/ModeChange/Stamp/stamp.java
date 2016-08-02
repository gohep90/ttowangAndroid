package com.app.ttowang.ttowang.ModeChange.Stamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.R;


public class stamp extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    Button modeChange;

    public static stamp createInstance(int itemsCount) {
        stamp stamp = new stamp();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        stamp.setArguments(bundle);
        return stamp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.stamp,container, false);
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


    public void onBackPressed(){
        Log.i("stamp","뒤로가기");
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().setResult(getActivity().RESULT_CANCELED, intent);
        getActivity().finish();
    }

}
