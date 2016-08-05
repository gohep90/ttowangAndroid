package com.app.ttowang.ttowang.Main.Business;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ttowang.ttowang.R;

import org.json.JSONArray;
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

public class businessMain extends Fragment {
    public final static String ITEMS_COUNT_KEY = "businessMain$ItemsCount";
        public static businessMain createInstance(int itemsCount) {
            businessMain BusinessMain = new businessMain();
            Bundle bundle = new Bundle();
            bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
            BusinessMain.setArguments(bundle);
            return BusinessMain;
        }



    ListView listView;

    ArrayList<MyItem> storeList = new ArrayList<MyItem>();

    MyListAdapter adapter;

    String encodedString="";
    String ip="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View result =inflater.inflate(R.layout.business_main, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        ip = sharedPreferences.getString("ip", ""); //데이터 가져오기


         Bitmap img= BitmapFactory.decodeResource(getResources(),R.drawable.bo);
         storeList.add(new MyItem(img,"카페베네","빙수가 오지게 맛나요","0"));
         storeList.add(new MyItem(img,"대구반야월막창","막창이 오지게 맛나요","1"));
        // storeList.add(new MyItem(img,"롯데시네마","팝콘이 오지게 맛나요",2));
        //storeList.add(new MyItem(img,"이마트","수박이 오지게 맛나요",3));

        adapter = new MyListAdapter(getContext(),storeList,R.layout.business_item);

        listView =(ListView)result.findViewById(R.id.listView);
        listView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        listView.setAdapter(adapter);
        listView.setDivider(null);

        StoreAsyncTaskCall();

        return result;
    }



    public class MyItem{
        Bitmap image=null;
        String title="";
        String info="";
        String index="";

        public MyItem(Bitmap image, String title, String info, String index){
            super();
            this.image=image;
            this.title=title;
            this.info=info;
            this.index=index;
        }
    }

    public class MyListAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        ArrayList<MyItem> myItems;
        int layout;

        public MyListAdapter(Context context, ArrayList<MyItem> myItems, int layout){
            this.context=context;
            this.myItems=myItems;
            this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layout=layout;
        }

        @Override
        public int getCount() {
            return myItems.size();
        }

        @Override
        public Object getItem(int position) {return position;}

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos =position;
            if(convertView == null) {
                convertView = inflater.inflate(layout,parent,false);
            }

            // final LinearLayout click =(LinearLayout)convertView.findViewById(R.id.click);

            final ImageView imageView=(ImageView)convertView.findViewById(R.id.image);
            imageView.setImageBitmap(myItems.get(pos).image);

            TextView title=(TextView)convertView.findViewById(R.id.txt_title);
            title.setText(myItems.get(pos).title);

            TextView info=(TextView)convertView.findViewById(R.id.txt_info);
            info.setText(myItems.get(pos).info);



            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
      /*              SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                    myname = sharedPreferences.getString("name", "");

                    if(!myname.equals("")) {     //로그인 됐으면
                        Intent intent = new Intent(getApplicationContext(), SellBoardInform.class);
                        //int whereindex = myItems.get(pos).index;
                        //Log.i("aaa", String.valueOf(myItems.get(pos).index));
                        //Toast.makeText(SellBoardBook.this, whereindex, Toast.LENGTH_SHORT).show();
                        intent.putExtra("index", myItems.get(pos).index);
                        startActivity(intent);

                    }
                    else{   //로그인 안됐으면
                        Toast.makeText(SellBoardBook.this, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
                        Intent newIntent = new Intent(getBaseContext(), Login.class);
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//넘기는 메인 flag에 설정을 넣은거야, 원래있던게 사라지고 다시 로드되는건가?
                        // 메인 액티비티를 다시 띄웁니다.
                        newIntent.putExtra("where", "sellBook");
                        newIntent.putExtra("index", myItems.get(pos).index);
                        //Log.i("이건 인덱스 처음 넘겨줌aaa", String.valueOf(whereindex));
                        startActivity(newIntent);
                    }

*/
                    //    click.setBackgroundColor(Color.parseColor("#6B66FF"));


       //클릭 시 이동 보류!!
                    Intent intent = new Intent(getContext(),business.class);
                 //   intent.putExtra("index", myItems.get(pos).index);
                    startActivity(intent);
                    //getActivity().finish();
                }
            });
            return convertView;
        }
    }


    public void StoreAsyncTaskCall(){
        new StoreAsyncTask().execute();
    }

    public class StoreAsyncTask extends AsyncTask<String,Integer,String> {

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
            //      prop.setProperty("edt_search",edt_search.getText().toString());
            //     prop.setProperty("check",check);

            encodedString = encodeString(prop);

            try{

                url=new URL("http://" + ip + ":8080/MyCard/storeList.do");
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
            jsonFirstList(result);

        }
    }

    private void jsonFirstList(String recv) {

        Log.i("서버에서 받은 전체 내용 : ", recv);

        try{
            JSONObject json=new JSONObject(recv);
            JSONArray jArr =json.getJSONArray("List");
    /*        totalListNum=jArr.length();
            //Toast.makeText(SellBoardBook.this, String.valueOf(totalListNum), Toast.LENGTH_SHORT).show();

            if (totalListNum > 7) {
                for (itemNum = 0; itemNum < 7; itemNum++ ) {
                    json = jArr.getJSONObject(itemNum);
                    bookList.add(new MyItem(imageAsyncTaskCall(json.getString("photo"),json.getString("exif")), json.getString("title"), json.getString("publisher"), json.getString("price") + "원", json.getString("day"), json.getInt("index")));
                }
            } else {
                for (itemNum = 0; itemNum < totalListNum; itemNum++ ) {
                    json = jArr.getJSONObject(itemNum);
                    bookList.add(new MyItem(imageAsyncTaskCall(json.getString("photo"),json.getString("exif")), json.getString("title"), json.getString("publisher"), json.getString("price") + "원", json.getString("day"), json.getInt("index")));
                }
            }

            */

            int i;
            for (i = 0; i < jArr.length(); i++ ) {
                json = jArr.getJSONObject(i);
                storeList.add(new MyItem(BitmapFactory.decodeResource(getResources(),R.drawable.bo),json.getString("매장이름"), json.getString("한줄소개"), json.getString("사업자등록번호")));

            }

            adapter.notifyDataSetChanged();     //리스트
            //     mLockListView=false;

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
