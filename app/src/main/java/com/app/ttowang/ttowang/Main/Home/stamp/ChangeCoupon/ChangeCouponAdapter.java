package com.app.ttowang.ttowang.Main.Home.stamp.ChangeCoupon;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Home.home;
import com.app.ttowang.ttowang.Main.MainActivity;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Park on 2016-08-05.
 */
public class ChangeCouponAdapter extends BaseAdapter {

    String couponName, businessId, couponCode,stampNeed;
    String ip= MainActivity.ip;
    static String userId = MainActivity.user;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ChangeCouponItem> listViewItemList = new ArrayList<ChangeCouponItem>() ;

    // ListViewAdapter의 생성자
    public ChangeCouponAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mybusinesscoupon_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView mybusineecouponname = (TextView) convertView.findViewById(R.id.mybusineecouponname) ;
        TextView mybusineecouponstampneed = (TextView) convertView.findViewById(R.id.mybusineecouponstampneed) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ChangeCouponItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mybusineecouponname.setText(listViewItem.getCouponName());
        mybusineecouponstampneed.setText(listViewItem.getStampNeed()+"개");


        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                couponName =listViewItem.getCouponName();
                businessId =listViewItem.getBusinessId();
                couponCode =listViewItem.getCouponCode();
                stampNeed  =listViewItem.getStampNeed();

                changeAsyncTaskCall();  //스탬프 쿠폰 전환
                //전환 확인 물어보기
                //Dialog();  //쓰고 싶은데 방법이.....

                //Toast.makeText(MainActivity.mContext, "businessId = "+businessId, Toast.LENGTH_SHORT).show();
            }
        });
/*
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(myBusinessCoupon.mContext,"롱클릭", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        */

        return convertView;
    }

    private void Dialog() {

        AlertDialog.Builder _alert = new AlertDialog.Builder(MainActivity.mContext);
        _alert.setTitle(couponName);
        _alert.setMessage("전환하겠습니까?").setCancelable(true);

        _alert.setPositiveButton("취소", null);

        _alert.setNegativeButton("전환", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int thisid) {
/*
*/

            }
        });
        _alert.show();
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
    public void addItem(String CouponName, String StampNeed,String BusinessId,String CouponCode) {
        ChangeCouponItem item = new ChangeCouponItem();

        item.setCouponName(CouponName);
        item.setStampNeed(StampNeed);
        item.setBusinessId(BusinessId);
        item.setCouponCode(CouponCode);

        listViewItemList.add(item);
    }


    //스탬프 쿠폰전환
    public void changeAsyncTaskCall(){
        new changeAsyncTask().execute();
    }

    public class changeAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("userId", userId);
            prop.setProperty("businessId", businessId);
            prop.setProperty("couponName", couponName);
            prop.setProperty("couponCode", couponCode);
            prop.setProperty("stampNeed", stampNeed);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/stampToCoupon.do");
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
            try{
                JSONObject json=new JSONObject(result);
                if(json.getString("result").equals("필요한 스탬프가 부족합니다.")){
                    Toast.makeText(MainActivity.mContext,json.getString("result") , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.mContext,json.getString("result") , Toast.LENGTH_SHORT).show();
                    home.stampRefresh();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }





}