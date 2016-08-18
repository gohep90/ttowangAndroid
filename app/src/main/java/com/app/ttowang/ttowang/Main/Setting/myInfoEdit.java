package com.app.ttowang.ttowang.Main.Setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;


public class myInfoEdit extends AppCompatActivity {
    //EditText 여러개 선언
    TextView change_text_tel;
    EditText change_edt_name, change_edt_birth, change_edt_email;
    Button change_btn_m, change_btn_w, change_btn_edit;

    String userTel, userName, userBirth, userGender, userEmail;
    int userCode = 0;

    String stringGender;
    int intGender = 0;//남0, 여1

    String ip = "";

    //다른곳에 선언되어있는 SharedPreferences 가져오기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfoedit);//내정보수정 창 띄워주고

        change_text_tel = (TextView) findViewById(R.id.change_text_tel);
        change_edt_name = (EditText) findViewById(R.id.change_edt_name);
        change_edt_birth = (EditText) findViewById(R.id.change_edt_birth);
        change_edt_email = (EditText) findViewById(R.id.change_edt_email);
        change_btn_edit = (Button) findViewById(R.id.change_btn_edit);
        change_btn_m = (Button) findViewById(R.id.change_btn_m);
        change_btn_w = (Button) findViewById(R.id.change_btn_w);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");
        userTel = sharedPreferences.getString("userTel", "");
        userName = sharedPreferences.getString("userName", "");
        userBirth = sharedPreferences.getString("userBirth", "");
        userGender = sharedPreferences.getString("userGender", "");
        userEmail = sharedPreferences.getString("userEmail", "");

        change_text_tel.setText(userTel);
        change_edt_name.setText(userName);
        change_edt_birth.setText(userBirth);
        change_edt_email.setText(userEmail);

        if (userGender.equals("남")) {
            intGender = 0;
            stringGender = "남";
        }
        else {
            intGender = 1;
            stringGender = "여";
        }

        if(intGender == 0){
            change_btn_m.setBackgroundResource(R.drawable.btn_gender);
            change_btn_w.setBackgroundResource(R.drawable.btn_ngender);
            change_btn_m.setTextColor(getResources().getColorStateList(R.color.m));
            change_btn_w.setTextColor(getResources().getColorStateList(R.color.w));
        }

        else{
            change_btn_m.setBackgroundResource(R.drawable.btn_ngender);
            change_btn_w.setBackgroundResource(R.drawable.btn_gender);
            change_btn_m.setTextColor(getResources().getColorStateList(R.color.w));
            change_btn_w.setTextColor(getResources().getColorStateList(R.color.m));
        }

        /*
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);//쉐어드객체얻기

        //String name = sharedPreferences.getString("name", "");
        edt_name.setText(sharedPreferences.getString("name", ""));
        text_tel.setText(sharedPreferences.getString("phone", ""));
        edt_email.setText(sharedPreferences.getString("email",""));
        //폰에 들어있는 내정보를 edittext로 뿌려줘
*/


        change_btn_edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
/*
                SharedPreferences sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);//쉐어드객체 다시 얻기
                SharedPreferences.Editor Edit= sharedPreferences.edit();
                Edit.putString("userName", change_edt_name.getText().toString());
                Edit.putString("userBirth", change_edt_birth.getText().toString());
                Edit.putString("userEmail", change_edt_email.getText().toString());
                Edit.putString("userGender", stringGender);
                Edit.commit();

                //Toast.makeText(myInfoEdit.this, change_edt_name.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Mainsetting.class);
                startActivity(intent);
*/
                finish();
            }
        });


        //저장버튼이있어서 저장버튼 누르면 버튼 메소드안에서 내용을 바꿔줘
        //그러면 setting에서 새로고침하면 바뀌겠지

        buttonClickListener();
    }

    private void buttonClickListener() {
        change_btn_m.setOnClickListener(ClickListener);
        change_btn_w.setOnClickListener(ClickListener);
    }

    View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.change_btn_m:
                    change_btn_m.setBackgroundResource(R.drawable.btn_gender);
                    change_btn_w.setBackgroundResource(R.drawable.btn_ngender);
                    change_btn_m.setTextColor(getResources().getColorStateList(R.color.m));
                    change_btn_w.setTextColor(getResources().getColorStateList(R.color.w));
                    intGender = 0;
                    stringGender = "남";
                    break;

                case R.id.change_btn_w:
                    change_btn_m.setBackgroundResource(R.drawable.btn_ngender);
                    change_btn_w.setBackgroundResource(R.drawable.btn_gender);
                    change_btn_m.setTextColor(getResources().getColorStateList(R.color.w));
                    change_btn_w.setTextColor(getResources().getColorStateList(R.color.m));
                    intGender = 1;
                    stringGender = "여";
                    break;
            }
        }
    };

}
