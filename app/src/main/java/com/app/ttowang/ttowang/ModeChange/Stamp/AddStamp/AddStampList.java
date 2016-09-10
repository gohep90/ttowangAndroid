package com.app.ttowang.ttowang.ModeChange.Stamp.AddStamp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.app.ttowang.ttowang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class AddStampList extends Activity {

    String businessId="";
    String ip;

    ArrayList<AddStampListData> datas = new ArrayList<AddStampListData>();

    ListView addStampListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addstamplist);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonArray");
        businessId = intent.getExtras().getString("businessId");

        try {
            JSONArray jArr = new JSONArray(jsonArray);   // JSONArray 생성
            for(int i=0; i < jArr.length(); i++){

                JSONObject jObject = jArr.getJSONObject(i);  // JSONObject 추출
                if(jObject.length() == 2) {
                    String userTel = jObject.getString("userTel");
                    String userName = jObject.getString("userName");
                    datas.add(new AddStampListData(userTel, userName, businessId));
                }
                else {
                    String userTel = jObject.getString("userTel");
                    String userName = "준회원";
                    datas.add(new AddStampListData(userTel, userName, businessId));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addStampListView = (ListView) findViewById(R.id.addStampListView);

        AddStampListAdapter adapter = new AddStampListAdapter(getLayoutInflater(), datas);

        addStampListView.setAdapter(adapter);


    }
}
