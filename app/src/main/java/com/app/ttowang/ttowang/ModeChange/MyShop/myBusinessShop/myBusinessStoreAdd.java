package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
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
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by srpgs2 on 2016-08-25.
 */
public class myBusinessStoreAdd extends AppCompatActivity {
    public static Context mContext;

    Button upload,cancel;
    EditText
            businessLicenseEdittext,
            businessNameEdittext,
            businessTelEdittext,
            businessInfoEdittext,
            businessTimeEdittext,
            businessAddressEdittext,
            businessMenuEdittext,
            businessBenefitEdittext,
            businessGroupEdittext;

    String
            businessLicense,
            businessName,
            businessTel,
            businessInfo,
            businessTime,
            businessAddress,
            businessMenu,
            businessBenefit,
            businessGroup,
            photo1Uri="",
            photo2Uri="",
            photo3Uri="",
            photo4Uri="";

    String userId;

    Button businessphoto1;
    ImageView photo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybusinessstoreadd);
        mContext = this;
        Intent i = getIntent();
        userId = i.getExtras().getString("userId");

        businessLicenseEdittext = (EditText)findViewById(R.id.businessLicense);
        businessNameEdittext = (EditText)findViewById(R.id.businessName);
        businessTelEdittext = (EditText)findViewById(R.id.businessTel);
        businessInfoEdittext = (EditText)findViewById(R.id.businessInfo);
        businessTimeEdittext = (EditText)findViewById(R.id.businessTime);
        businessAddressEdittext = (EditText)findViewById(R.id.businessAddress);
        businessMenuEdittext = (EditText)findViewById(R.id.businessMenu);
        businessBenefitEdittext = (EditText)findViewById(R.id.businessBenefit);
        businessGroupEdittext = (EditText)findViewById(R.id.businessGroup);

        businessphoto1 = (Button)findViewById(R.id.businessphoto1);
        photo1 = (ImageView) findViewById(R.id.photo1);

        upload  =   (Button)findViewById(R.id.upload);
        cancel = (Button)findViewById(R.id.cancel);
        //new businessDownAsyncTask().execute();




        businessphoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                businessLicense = String.valueOf(businessLicenseEdittext.getText());
                businessName =String.valueOf(businessNameEdittext.getText());
                businessTel =String.valueOf(businessTelEdittext.getText());
                businessInfo =String.valueOf(businessInfoEdittext.getText());
                businessTime =String.valueOf(businessTimeEdittext.getText());
                businessAddress =String.valueOf(businessAddressEdittext.getText());
                businessMenu =String.valueOf(businessMenuEdittext.getText());
                businessBenefit =String.valueOf(businessBenefitEdittext.getText());
                businessGroup =String.valueOf(businessGroupEdittext.getText());

                if(
                    !businessLicense.equals("") &
                    !businessName.equals("") &
                    !businessTel.equals("") &
                    !businessInfo.equals("") &
                    !businessTime.equals("") &
                    !businessAddress.equals("") &
                    !businessMenu.equals("") &
                    !businessBenefit.equals("") &
                    !businessGroup.equals("")
                ){
                    new businessAddStoreAsyncTask().execute();
                    //Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                    //intent.putExtra("currentViewPager", currentViewPager);
                    //startActivity(intent);
                    //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(myBusinessStoreAdd.this,"빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //설정한 시간 가져오기 부분
        switch (requestCode) {
            case 0:
                if(resultCode== Activity.RESULT_OK) {
                    try {
                        photo1Uri = String.valueOf(data.getData());
                        businessphoto1.setText(photo1Uri);

                        Log.i("매장 추가 - ", "가져온 이미지 " + photo1Uri);

                        Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(photo1Uri));
                        //photo1.setImageBitmap(bm);
                        Bitmap reSized = Bitmap.createScaledBitmap(bm, 2000,2000, true);
                        photo1.setImageBitmap(reSized); //이미지 뷰에 리사이즈 된 비트맵을 넣는다.


                        //이미지 파일의 절대 경로 받아오기

                        Cursor c = getContentResolver().query(Uri.parse(photo1Uri), null,null,null,null);
                        c.moveToNext();
                        String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                        Log.i("매장 추가 - ", "가져온 이미지 절대경로" + absolutePath);


                    } catch (Exception e) {

                    }
                }
        }
    }


    public class businessAddStoreAsyncTask extends AsyncTask<String,Integer,String> {

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

            prop.setProperty("userId", String.valueOf(userId));
            prop.setProperty("businessLicense", businessLicense);
            prop.setProperty("businessName", businessName);
            prop.setProperty("businessTel", businessTel);
            prop.setProperty("businessInfo", businessInfo);
            prop.setProperty("businessTime", businessTime);
            prop.setProperty("businessAddress", businessAddress);
            prop.setProperty("businessMenu", businessMenu);
            prop.setProperty("businessBenefit", businessBenefit);
            prop.setProperty("businessLat","");
            prop.setProperty("businessLng","");
            prop.setProperty("businessType", "1");
            prop.setProperty("businessGroup", businessGroup);
/*
            prop.setProperty("userId", "5");
            prop.setProperty("businessLicense", "11");
            prop.setProperty("businessName", "11");
            prop.setProperty("businessTel", "11");
            prop.setProperty("businessInfo", "11");
            prop.setProperty("businessTime", "11");
            prop.setProperty("businessAddress", "11");
            prop.setProperty("businessMenu", "11");
            prop.setProperty("businessBenefit", "11");
            prop.setProperty("businessLat","");
            prop.setProperty("businessLng","");
            prop.setProperty("businessType", "1");
            prop.setProperty("businessGroup", "1");
*/
            String encodedString = encodeString(prop);

            try{
                //url=new URL("http://192.168.0.2:8181/ttowang/businessAdd.do");
                url=new URL("http://" + MainActivity.ip + ":8080/ttowang/businessAdd.do");
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
            Log.i("myBusinessShop - ", " 내 매장 등록");
            Log.i("서버에서 받은 전체 내용 : ", result);

            try{
                JSONObject json = new JSONObject(result);
                JSONArray jArr =json.getJSONArray("List");
                json = jArr.getJSONObject(0);
                Log.i("서버에서 받은 비즈니스 아이디 : ", json.getString("businessId"));

                myBusinessShop.adapter.addItem(
                        "1",
                        json.getString("businessId"),
                        businessLicense,
                        businessName,
                        businessTel,
                        businessInfo,
                        businessTime,
                        businessAddress,
                        businessMenu,
                        businessBenefit,
                        businessGroup
                );
                myBusinessShop.adapter.notifyDataSetChanged();

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
