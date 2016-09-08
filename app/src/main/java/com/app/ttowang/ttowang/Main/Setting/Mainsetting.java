package com.app.ttowang.ttowang.Main.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

public class Mainsetting extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;

    TextView modeChange,homelist;
    TextView emailclick, callclick, facebookclick;
    ViewGroup myInfoclick, logoutclick, notice,alarm_switch;
    TextView alarm_switch_on, alarm_switch_off;
    TextView myName, myTel, myEmail;

    String userTel, userName, userBirth, userGender, userEmail;
    int onSwitch=0;//재훈
    String ip = "";
/*
    public static SharedPreferences sharedPreferences;//이거랑
    public static SharedPreferences.Editor Edit;//이거수정하는거 2개가필요
*/
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

        /*
        if(sharedPreferences.getString("name", "").equals("")) {//저장된게 없으면 실행하고 저장된게 있으면 하지마 근데 이거 없어도 됨 뭐지?
            Edit.putString("name", "이재훈");//이 함수를 이용해서 name에 데이터 저장하기
            Edit.putString("phone", "01031933763");//내정보로 수정해도 앱 다시킬때 이 코드 수행되니 안변해야 정상 아닌가?
            Edit.putString("email", "hoon123@naver.com");
            Edit.commit();//커밋
        }
        */
        /*
        String myname = sharedPreferences.getString("name", "이름" );//수정말고 원본객체에서 name이름의 문자열을 얻어와
        String Phone = sharedPreferences.getString("phone", "폰" );
        String Email = sharedPreferences.getString("email", "메일" );
        myName.setText(myname);//가져온 String myName에다가 넣어
        myPhone.setText(Phone);//맨위에 선언한 textview에 String넣어
        myEmail.setText(Email);
        */
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
        //재훈

        modeChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.Edit.putString("nowMode", "on");
                Log.i("setting - ", "가맹점 모드 : on");
                MainActivity.Edit.commit();
                Intent intent = new Intent(MainActivity.mContext, ChangeModeMain.class);
                Toast.makeText(getActivity(), "사업자 모드 실행", Toast.LENGTH_SHORT).show();
                getActivity().startActivityForResult(intent, 0);
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
                sharedPreferencesEditor.putString("userId", "");
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
