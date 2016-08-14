package com.app.ttowang.ttowang.Main.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.R;


public class myInfoEdit extends AppCompatActivity {
    //EditText 여러개 선언
    TextView text_tel, edt_name, edt_year, edt_month, edt_day, edt_email;
    Button btn_edit;

    //다른곳에 선언되어있는 SharedPreferences 가져오기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfoedit);//내정보수정 창 띄워주고
        btn_edit=(Button)findViewById(R.id.btn_edit);
        edt_name=(TextView)findViewById(R.id.edt_name);//(TextView)findViewById(R.id.~);
        text_tel=(TextView)findViewById(R.id.text_tel);
        edt_email=(TextView)findViewById(R.id.edt_email);

        SharedPreferences sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);//쉐어드객체얻기

        //String name = sharedPreferences.getString("name", "");
        edt_name.setText(sharedPreferences.getString("name", ""));
        text_tel.setText(sharedPreferences.getString("phone", ""));
        edt_email.setText(sharedPreferences.getString("email",""));
        //폰에 들어있는 내정보를 edittext로 뿌려줘

        btn_edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);//쉐어드객체 다시 얻기
                SharedPreferences.Editor Edit= sharedPreferences.edit();


                Edit.putString("name", edt_name.getText().toString());//이 함수를 이용해서 name에 데이터 저장하기,
                Edit.putString("phone", text_tel.getText().toString());
                Edit.putString("email", edt_email.getText().toString());
                Edit.commit();

                Toast.makeText(myInfoEdit.this, edt_name.getText().toString(), Toast.LENGTH_SHORT).show();

                finish();
                //Intent intent = new Intent(getApplicationContext(),Mainsetting.class);
                //startActivity(intent);
            }
        });
        //저장버튼이있어서 저장버튼 누르면 버튼 메소드안에서 내용을 바꿔줘
        //그러면 setting에서 새로고침하면 바뀌겠지
    }
}
