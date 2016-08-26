package com.app.ttowang.ttowang.Main.Loading;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.app.ttowang.ttowang.R;


/**
 * Created by srpgs2 on 2016-07-16.
 */
public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

