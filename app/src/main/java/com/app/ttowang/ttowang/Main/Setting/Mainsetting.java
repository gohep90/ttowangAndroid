package com.app.ttowang.ttowang.Main.Setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.ChangeModeMain;
import com.app.ttowang.ttowang.R;

public class Mainsetting extends Fragment {
    public final static String ITEMS_COUNT_KEY = "home$ItemsCount";

    View view;
    Button modeChange;
    Button myInfo;
    Context context;

    TextView myName, myPhone, myEmail;

    public static SharedPreferences sharedPreferences;//이거랑
    public static SharedPreferences.Editor Edit;//이거수정하는거 2개가필요


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
        myInfo=(Button) view.findViewById(R.id.myInfo);//위에꺼처럼 myInfo해

        myName=(TextView)view.findViewById(R.id.myName);//xml에서 TextView id가져와
        myPhone=(TextView)view.findViewById(R.id.myPhone);//xml에서 TextView id가져와
        myEmail=(TextView)view.findViewById(R.id.myEmail);//xml에서 TextView id가져와

        sharedPreferences = getActivity().getSharedPreferences("setting",getActivity().MODE_PRIVATE);//그냥 setting이라는 이름의 객체를 얻어서 초기화
        Edit = sharedPreferences.edit();//수정하겠다는 객체 얻기

        if(sharedPreferences.getString("name", "").equals("")) {//저장된게 없으면 실행하고 저장된게 있으면 하지마 근데 이거 없어도 됨 뭐지?
            Edit.putString("name", "이재훈");//이 함수를 이용해서 name에 데이터 저장하기
            Edit.putString("phone", "01031933763");//내정보로 수정해도 앱 다시킬때 이 코드 수행되니 안변해야 정상 아닌가?
            Edit.putString("email", "hoon123@naver.com");
            Edit.commit();//커밋
        }




        String myname = sharedPreferences.getString("name", "이름" );//수정말고 원본객체에서 name이름의 문자열을 얻어와
        String Phone = sharedPreferences.getString("phone", "폰" );
        String Email = sharedPreferences.getString("email", "메일" );
        myName.setText(myname);//가져온 String myName에다가 넣어
        myPhone.setText(Phone);//맨위에 선언한 textview에 String넣어
        myEmail.setText(Email);



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


        myInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getContext(),myInfoEdit.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
