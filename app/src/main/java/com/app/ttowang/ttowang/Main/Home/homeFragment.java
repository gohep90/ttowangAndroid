package com.app.ttowang.ttowang.Main.Home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.Business.business;
import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;
import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;


public class homeFragment extends android.support.v4.app.Fragment{

    private View rootView;
    private TextView businessName,myRemainStamp,myTotalStamp,businessLocation;
    private ImageView mybusinessimg;
    private int number = -1;
    private RelativeLayout viewlayout;

    String ip= MainActivity.ip;
    static String userId = MainActivity.user;
    String businessId="";



    public interface OnFragmentInteractionListener {
        public void onFragmentCreated(int number);
    }

    private OnFragmentInteractionListener listener;

    static homeFragment newInstance(int number) {
        homeFragment f = new homeFragment();
        //Log.d("ArrayListFragment", "newInstance");
        // Supply num input as an argument.

        Bundle args = new Bundle();
        args.putInt("number", number);
        f.setArguments(args);
        Log.i("homeFragment - ","newInstance "+ number);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("ArrayListFragment", "onCreate");
        number = getArguments() != null ? getArguments().getInt("number") : 1;
        Log.i("homeFragment - ","onCreate "+ number);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("homeFragment - ","onCreateView ");
        View view = inflater.inflate(R.layout.home_fragment, container,false);
        viewlayout = (RelativeLayout) view.findViewById(R.id.viewlayout);
        businessName = (TextView) view.findViewById(R.id.businessName);
        businessLocation = (TextView) view.findViewById(R.id.businessLocation);
        myRemainStamp = (TextView) view.findViewById(R.id.myRemainStamp);
        myTotalStamp = (TextView) view.findViewById(R.id.myTotalStamp);


        mybusinessimg = (ImageView) view.findViewById(R.id.mybusinessimg); //매장 사진 교체

        Glide.with(container.getContext()).load("http://" + ip + ":8080/ttowang/image/"+home.myAllBusiness.get(number).get(3)).into(mybusinessimg);

        businessName.setText(String.valueOf(home.myAllBusiness.get(number).get(1)));      //매장 이름
        businessLocation.setText(String.valueOf(home.myAllBusiness.get(number).get(2)));//매장 주소
        myRemainStamp.setText(String.valueOf(home.myAllBusiness.get(number).get(4))); //남은 쿠폰 갯수
        myTotalStamp.setText(String.valueOf(home.myAllBusiness.get(number).get(5)));     //토탈 스탬프 수

        String as = String.valueOf(home.myAllBusiness.get(number).get(5));

        viewlayout.setOnClickListener(new View.OnClickListener() {    //시간 누르면
            @Override
            public void onClick(View v) {               //쿠폰을 클릭하면 토스트 뜸
                //Toast.makeText(getActivity(), (String)home.myAllBusiness.get(number).get(1), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), business.class);
                intent.putExtra("businessId", String.valueOf(home.
                        myAllBusiness.get(number).get(0)));
                startActivity(intent);
            }
        });

        viewlayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {

                Dialog(number);

                return true;
            }

        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {

        try {
            listener = (OnFragmentInteractionListener) new home();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }

        super.onAttach(activity);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            listener.onFragmentCreated(number);
            //TextView text_home = (TextView) home.view.findViewById(R.id.text_home);
            //text_home.setText(number+"");
            Log.i("Fragment", "call activity: " + number);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Fragment", "onActivityCreated ");
    }




    private void Dialog(final int number) {
        businessId=String.valueOf(home.myAllBusiness.get(number).get(0));  //businessId 받아오기
        AlertDialog.Builder _alert = new AlertDialog.Builder(MainActivity.mContext);
        _alert.setTitle((String)home.myAllBusiness.get(number).get(1));
        _alert.setMessage("삭제하면 등록된 스탬프와 쿠폰이\n삭제 됩니다.").setCancelable(true);

        _alert.setPositiveButton("취소", null);

        _alert.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int thisid) {
/*
                if(!sharedPreferences.getString(PREF_ACCOUNT_NAME, "").equals("") & setyear != 0){
                    if(!calendarid.equals("0") & !calendarid.equals("")){
                        new delete().execute();
                    }
                }
*/
                deleteAsyncTaskCall();  //즐겨찾기 삭제
                //home.PagerAdapter.notifyDataSetChanged;
                Toast.makeText(MainActivity.mContext, "삭제합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        _alert.show();
    }

    //즐겨찾기 삭제 스레드
    public void deleteAsyncTaskCall(){
        new deleteAsyncTask().execute();
    }

    public class deleteAsyncTask extends AsyncTask<String,Integer,String> {

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
            prop.setProperty("BUSINESSID", businessId);
            prop.setProperty("USERID", userId);

            String encodedString = encodeString(prop);

            try{
                url=new URL("http://" + ip + ":8080/ttowang/deleteMyBusiness.do");
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
            home.refresh();
        }
    }


}
