package com.app.ttowang.ttowang.Main.Loading;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.app.ttowang.ttowang.R;


/**
 * Created by srpgs2 on 2016-07-16.
 */
public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Handler hd = new Handler();

        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();       // 2 초후 이미지를 닫아버림
            }
        }, 1000);
    }

}

