package com.app.ttowang.ttowang.Main.Home.coupon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class couponItemAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<couponItemClass> listViewItemList = new ArrayList<couponItemClass>() ;
    private Button useCoupon;
    private TextView couponName, couponNumber;

    String couponNum="";
    String ip;

    public static int pos;

    private LinearLayout thisCoupon;
    // ListViewAdapter의 생성자
    public couponItemAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        pos = position;
        final Context context = parent.getContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", context.MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");


        final couponItemClass listViewItem = listViewItemList.get(position);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.couponitem, parent, false);
        }

        useCoupon = (Button)convertView.findViewById(R.id.useCoupon);
        couponName = (TextView)convertView.findViewById(R.id.couponName);
        couponNumber = (TextView)convertView.findViewById(R.id.couponNumber);
        thisCoupon = (LinearLayout)convertView.findViewById(R.id.thisCoupon);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        couponItemClass couponItemClass = listViewItemList.get(position);

        couponName.setText(home.myAllBusinessCouponName.get(home.nowbusiness).get(position));
        couponNumber.setText(home.myAllBusinessCouponNum.get(home.nowbusiness).get(position));

        if(listViewItem.getCouponUse().equals("0")){    //사용했으면
            thisCoupon.setBackgroundColor(Color.parseColor("#ededed"));
            useCoupon.setBackgroundColor(Color.parseColor("#d8d8d8"));
            useCoupon.setText("사용 완료");
            //useCoupon.setClickable(false);
        }else{
            thisCoupon.setBackgroundColor(Color.parseColor("#FFDDDD"));
            useCoupon.setBackgroundColor(Color.parseColor("#FFB9B9"));
            useCoupon.setText("사용 하기");
        }
        // 아이템 내 각 위젯에 데이터 반영


        useCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.mContext, position+ " 번째 " + useCoupon.getText(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.mContext, listViewItem.getCouponUse()+"", Toast.LENGTH_SHORT).show();

                Log.i("쿠폰 - ",position + " 번째 " + listViewItem.getCouponUse() + " " + listViewItem.getCouponNum());
                Log.i("쿠폰 사용함",pos + " 번째 쿠폰");
                if(listViewItem.getCouponUse().equals("0")) {   //사용한거면
                    Toast.makeText(MainActivity.mContext,"이미 사용한 쿠폰 입니다.", Toast.LENGTH_SHORT).show();
                }else{  //안사용 한거면
                    //Toast.makeText(MainActivity.mContext, position + "번째 사용 하기", Toast.LENGTH_SHORT).show();
                    couponNum = listViewItem.getCouponNum();
                    Dialog(listViewItem.getCouponName());

                    //Toast.makeText(MainActivity.mContext, position + "번째 사용 완료", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.

    public void clearItem(){
        listViewItemList.clear();
    }


    private void Dialog(String name) {
        //businessId=String.valueOf(home.myAllBusiness.get(number).get(0));  //businessId 받아오기
        AlertDialog.Builder _alert = new AlertDialog.Builder(MainActivity.mContext);
        _alert.setTitle(name);
        _alert.setMessage("정말로 쿠폰을 사용하시겠습니까?").setCancelable(true);

        _alert.setPositiveButton("취소", null);

        _alert.setNegativeButton("사용", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int thisid) {

                useCouponAsyncTaskCall();  //쿠폰 사용
                //home.PagerAdapter.notifyDataSetChanged;

            }
        });

        _alert.show();
    }


    //비지니스 초기 상세정보 가져오기
    public void useCouponAsyncTaskCall(){
        new useCouponAsyncTask().execute();
    }

    public class useCouponAsyncTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {  // 통신을 위한 Thread
            String result =recvList();
            return result;
        }

        public String encodeString(Properties params) {  //한글 encoding??
            StringBuffer sb = new StringBuffer(256);
            Enumeration names = params.propertyNames();

            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = params.getProperty(name);
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

                if (names.hasMoreElements()) sb.append("&");
            }
            return sb.toString();
        }

        private String recvList() { //데이터 보내고 받아오기!!

            HttpURLConnection urlConnection=null;
            URL url =null;
            DataOutputStream out=null;
            BufferedInputStream buf=null;
            BufferedReader bufreader=null;

            Properties prop = new Properties();
            prop.setProperty("couponNum", couponNum);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/couponUse.do");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                out = new DataOutputStream(urlConnection.getOutputStream());

                out.writeBytes(encodedString);

                out.flush();    //서버로 버퍼의 내용 전송

                buf = new BufferedInputStream(urlConnection.getInputStream());
                bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));

                String line=null;
                String result="";

                while((line=bufreader.readLine())!=null){
                    result += line;
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }finally{
                urlConnection.disconnect();  //URL 연결해제
            }
        }
        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!

            Toast.makeText(MainActivity.mContext, "쿠폰을 사용합니다.", Toast.LENGTH_SHORT).show();
            UseupdateCoupon();

        }
    }

    /*
    public void addItem(String couponNum, String couponUse, String couponName ,String businessId , String couponCode) {
        couponItemClass item = new couponItemClass();
        item.setCouponNum(couponNum);
        item.setCouponUse(couponUse);
        item.setCouponName(couponName);
        item.setBusinessId(businessId);
        item.setCouponCode(couponCode);
    }
    */

    public void addItem(String couponNum, String couponUse, String couponName) {
        couponItemClass item = new couponItemClass();
        item.setCouponNum(couponNum);
        item.setCouponUse(couponUse);
        item.setCouponName(couponName);

        listViewItemList.add(item);
    }

    public void UseupdateCoupon(){
        home.stampRefresh();
    }
}
