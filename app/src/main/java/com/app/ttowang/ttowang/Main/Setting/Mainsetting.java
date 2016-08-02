package com.app.ttowang.ttowang.Main.Setting;

import android.content.Context;
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


public class Mainsetting extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    Button modeChange;
    Context context;

    public static Mainsetting createInstance(int itemsCount) {
        Mainsetting Setting = new Mainsetting();
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
                //MainActivity.goModeChange();

                Toast.makeText(getActivity(), "체인지모드 실행", Toast.LENGTH_SHORT).show();

                MainActivity.Edit.putString("nowMode", "on");
                Log.i("setting - ", "가맹점 모드 : on");
                MainActivity.Edit.commit();

                Intent intent = new Intent(MainActivity.mContext, ChangeModeMain.class);
                intent.putExtra("where","Mainsetting");
                getActivity().startActivityForResult(intent, 0);
            }
        });
        return view;
    }

}
