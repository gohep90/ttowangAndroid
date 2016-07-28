package com.app.ttowang.ttowang.Business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.ttowang.ttowang.R;

public class business extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business);

        initToolbar();
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("또왕");
        // mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}
