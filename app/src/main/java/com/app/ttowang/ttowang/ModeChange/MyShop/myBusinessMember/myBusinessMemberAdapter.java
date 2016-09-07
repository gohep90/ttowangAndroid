package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessMember;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by Park on 2016-08-05.
 */
public class myBusinessMemberAdapter extends BaseAdapter {

    String ip;

    String businessId,userId,userName;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<myBusinessMemberItem> listViewItemList = new ArrayList<myBusinessMemberItem>() ;

    // ListViewAdapter의 생성자
    public myBusinessMemberAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", context.MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", "");

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mybusinessmember_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView myBusinessStaffGender = (ImageView) convertView.findViewById(R.id.myBusinessStaffGender);
        TextView myBusinessStaffName = (TextView) convertView.findViewById(R.id.myBusinessStaffName) ;
        TextView myBusinessStaffTel = (TextView) convertView.findViewById(R.id.myBusinessStaffTel) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final myBusinessMemberItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        myBusinessStaffName.setText(listViewItem.getUserName());
        myBusinessStaffTel.setText(listViewItem.getUserTel());
        if(listViewItem.getUserGender().equals("여자")){
            myBusinessStaffGender.setImageDrawable(myBusinessMember.mContext.getDrawable(R.drawable.girl));
        }else{
            myBusinessStaffGender.setImageDrawable(myBusinessMember.mContext.getDrawable(R.drawable.man));
        }

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(myBusinessMember.mContext,"원클릭", Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() { //삭제
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(myBusinessCoupon.mContext,"롱클릭", Toast.LENGTH_SHORT).show();
                businessId = listViewItem.getBusinessId();
                userId = listViewItem.getUserId();
                userName = listViewItem.getUserName();
                Dialog();
                return true;
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


    public void addItem(String businessId, String userGender, String userTel ,String userName , String userId) {
        myBusinessMemberItem item = new myBusinessMemberItem();

        item.setBusinessId(businessId);
        item.setUserGender(userGender);
        item.setUserTel(userTel);
        item.setUserName(userName);
        item.setUserId(userId);

        listViewItemList.add(item);
    }

    private void Dialog() {

        AlertDialog.Builder _alert = new AlertDialog.Builder(myBusinessMember.mContext);
        _alert.setTitle(userName);
        _alert.setMessage("삭제하겠습니까?").setCancelable(true);

        _alert.setPositiveButton("취소", null);

        _alert.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int thisid) {

                new staffDelAsyncTask().execute();

            }
        });
        _alert.show();
    }


    public class staffDelAsyncTask extends AsyncTask<String,Integer,String> {

        protected void onPreExecute(){
        }

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



            prop.setProperty("businessId",businessId);
            prop.setProperty("userId",userId);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/staffDel.do");
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
            Log.i("서버에서 받은 전체 내용 : ", result);

            if(!result.equals("")){
                myBusinessMember.staffRefresh();
                //listViewItemList.remove(nowposition);
                //myBusinessMember.adapter.notifyDataSetChanged();
                Log.i("coupon 어댑터 ","새로 고친다");
            }else{
                Toast.makeText(myBusinessMember.mContext,"삭제 실패", Toast.LENGTH_SHORT).show();
            }

        }
    }
}