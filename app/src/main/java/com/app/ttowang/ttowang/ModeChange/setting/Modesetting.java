package com.app.ttowang.ttowang.ModeChange.setting;

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
import com.app.ttowang.ttowang.R;


public class Modesetting extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    Button modeChange;

    public static Modesetting createInstance(int itemsCount) {
        Modesetting Setting = new Modesetting();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        Setting.setArguments(bundle);
        return Setting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.setting,container, false);
        modeChange = (Button) view.findViewById(R.id.modeChange);


        modeChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "체인지모드 종료", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.mContext,ChangeModeMain.class));
                MainActivity.Edit.putString("nowMode", "off");
                Log.i("setting - ", "가맹점 모드 : off");
                MainActivity.Edit.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().setResult(getActivity().RESULT_OK, intent);
                getActivity().finish();
            }
        });
        return view;
    }




}
