package com.app.ttowang.ttowang.ModeChange.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.HomeList.HomeList;
import com.app.ttowang.ttowang.Main.Login.Tel;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.Main.Setting.myInfoEdit;
import com.app.ttowang.ttowang.R;


public class Modesetting extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    TextView modeChange,homelist;
    TextView emailclick, callclick,facebookclick;//재훈
    ViewGroup myInfoclick, logoutclick, notice,alarm_switch;
    TextView alarm_switch_on, alarm_switch_off;

    TextView myName, myTel, myEmail;

    String userTel, userName, userBirth, userGender, userEmail;
    int onSwitch=0;//재훈
    String ip = "";

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
        modeChange = (TextView) view.findViewById(R.id.modeChange);
        myInfoclick=(ViewGroup) view.findViewById(R.id.myInfo);
        logoutclick=(ViewGroup) view.findViewById(R.id.logout);
        notice=(ViewGroup) view.findViewById(R.id.notice);
        //emailclick=(TextView) view.findViewById(R.id.emailclick);
        //callclick=(TextView) view.findViewById(R.id.callclick);
        //facebookclick=(TextView) view. findViewById(R.id.facebookclick);
        alarm_switch=(ViewGroup) view.findViewById(R.id.alarm_switch);//재훈
        alarm_switch_on = (TextView)view.findViewById(R.id.alarm_switch_on);
        alarm_switch_off = (TextView)view.findViewById(R.id.alarm_switch_off);

        myName = (TextView)view.findViewById(R.id.myName);//xml에서 TextView id가져와
        myTel = (TextView)view.findViewById(R.id.myTel);//xml에서 TextView id가져와
        myEmail = (TextView)view.findViewById(R.id.myEmail);//xml에서 TextView id가져와
        homelist = (TextView)view.findViewById(R.id.homelist);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences",getActivity().MODE_PRIVATE);
        //Edit = sharedPreferences.edit();//수정하겠다는 객체 얻기
        ip = sharedPreferences.getString("ip", "");
        userTel=sharedPreferences.getString("userTel", "");
        userName=sharedPreferences.getString("userName", "");
        userBirth=sharedPreferences.getString("userBirth", "");
        userGender=sharedPreferences.getString("userGender", "");
        userEmail=sharedPreferences.getString("userEmail", "");

        myTel.setText(userTel);
        myName.setText(userName);
        myEmail.setText(userEmail);

        if(onSwitch == 0) {//off라면 on으로
            alarm_switch_on.setTextColor(Color.parseColor("#ff005c"));
            alarm_switch_off.setTextColor(Color.parseColor("#000000"));
        }else{
            alarm_switch_on.setTextColor(Color.parseColor("#000000"));
            alarm_switch_off.setTextColor(Color.parseColor("#ff005c"));
        }
//재훈
        alarm_switch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(onSwitch==0){//on이라면 off로
                    alarm_switch_on.setTextColor(Color.parseColor("#000000"));
                    alarm_switch_off.setTextColor(Color.parseColor("#ff005c"));
                    onSwitch=1;
                    //Toast.makeText(getActivity(), "off로 전환합니다", Toast.LENGTH_SHORT).show();
                }
                else{//off라면 on으로
                    alarm_switch_on.setTextColor(Color.parseColor("#ff005c"));
                    alarm_switch_off.setTextColor(Color.parseColor("#000000"));
                    //Toast.makeText(getActivity(), "on으로 전환합니다", Toast.LENGTH_SHORT).show();
                    onSwitch=0;
                }
            }
        });

        modeChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                MainActivity.Edit.putString("nowMode", "off");
                Log.i("setting - ", "가맹점 모드 : off");
                MainActivity.Edit.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().setResult(getActivity().RESULT_OK, intent);
                Toast.makeText(getActivity(), "사업자 모드 종료", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        myInfoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), myInfoEdit.class);
                startActivity(intent);
            }
        });

        logoutclick.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(), "로그아웃 클릭", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences",getActivity().MODE_PRIVATE);   //쉐어드 객체 얻기
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();                        //쉐어드 쓰기
                sharedPreferencesEditor.putString("userTel", "");
                sharedPreferencesEditor.putString("userName", "");
                sharedPreferencesEditor.putString("userBirth", "");
                sharedPreferencesEditor.putString("userGender", "");
                sharedPreferencesEditor.putString("userEmail", "");
                sharedPreferencesEditor.commit();

                Intent i = new Intent(getActivity().getApplicationContext(), Tel.class);
                startActivity(i);

                getActivity().finish();
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "공지사항 클릭", Toast.LENGTH_SHORT).show();
            }
        });


        homelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), HomeList.class);
                startActivity(i);
            }
        });

        return view;
    }
}
