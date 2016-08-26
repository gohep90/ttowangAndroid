package com.app.ttowang.ttowang.Main.Home.stamp.GiftStamp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class GiftStamp extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.giftstamp);
        //setTheme(R.style.AppDialogTheme);


/*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ChangeCouponItem item = (ChangeCouponItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getBenefit() ;
                String descStr = item.getNumber() ;

            }
        }) ;
*/
    }
}
