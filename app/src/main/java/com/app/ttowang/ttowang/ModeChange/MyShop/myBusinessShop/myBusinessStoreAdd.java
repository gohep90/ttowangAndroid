package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
            businessLicense="",
            businessName="",
            businessTel="",
            businessInfo="",
            businessTime="",
            businessAddress="",
            businessMenu="",
            businessBenefit="",
            businessGroup="",
            photo1Path="",
            photo2Path="",
            photo3Path="",
            photo4Path="";

    String userId;

    Button businessphoto1;
    ImageView photo1;
    Uri photouri1;

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
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent ,100);
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


                new ImageAsyncTask().execute();
                Toast.makeText(myBusinessStoreAdd.this,"이미지 추가", Toast.LENGTH_SHORT).show();
             /*
                if(
                    !businessLicense.equals("") &
                    !businessName.equals("") &
                    !businessTel.equals("") &
                    !businessInfo.equals("") &
                    !businessTime.equals("") &
                    !businessAddress.equals("") &
                    !businessMenu.equals("") &
                    !businessBenefit.equals("") &
                    !businessGroup.equals("") &
                    !photo1Path.equals("")
                ){
                    //new businessAddStoreAsyncTask().execute();
                    new ImageAsyncTask().execute();
                    //Intent intent = new Intent(getApplicationContext(), myBusinessCouponAdd.class);   //인텐트로 넘겨줄건데요~
                    //intent.putExtra("currentViewPager", currentViewPager);
                    //startActivity(intent);
                    //Toast.makeText(myBusinessCoupon.this,"추가 버튼", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(myBusinessStoreAdd.this,"빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(Uri.parse(data.toString()), null, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String photoName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgPath;
     }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    //설정한 시간 가져오기 부분
        if (requestCode==100) {
                if(resultCode== Activity.RESULT_OK) {
                    try {
                        photo1Path = getImageNameToUri(data.getData()); //이미지 절대경로 구하기!!
                       // Log.i("기존 - ", "가져온 이미지 " + photo1Path);

                        // Toast.makeText(ChoiceSell.this, photoUri, Toast.LENGTH_SHORT).show();
                        //Log.i("이미지 :", absolutePath);

                        //   Bitmap image_bitmap = BitmapFactory.decodeFile(photoUri);
                        //  image_bitmap = Bitmap.createScaledBitmap(image_bitmap, 550, 600, true);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inSampleSize = calculateInSampleSize(options,550,600);     //포토 리사이즈
                        options.inSampleSize = 4;
                        Bitmap image_bitmap = BitmapFactory.decodeFile(photo1Path, options);


                        ExifInterface exif = new ExifInterface(photo1Path);     //이미지 자동회전 방지!!
                        int exifOrientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        int exifDegree;

                        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
                            exifDegree = 90;
                        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
                            exifDegree = 180;
                        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
                            exifDegree = 270;
                        }else{
                            exifDegree = 0;
                        }

                        if (exifDegree != 0 && image_bitmap != null) {
                            Matrix m = new Matrix();
                            m.setRotate(exifDegree, (float) image_bitmap.getWidth() / 2,
                                    (float) image_bitmap.getHeight() / 2);

                            try {
                                Bitmap converted = Bitmap.createBitmap(image_bitmap, 0, 0,
                                        image_bitmap.getWidth(), image_bitmap.getHeight(), m, true);
                                if (image_bitmap != converted) {
                                    image_bitmap.recycle();
                                    image_bitmap = converted;
                                }
                            } catch (OutOfMemoryError ex) {
                                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
                            }
                        }

                        int height = image_bitmap.getHeight();
                        int width = image_bitmap.getWidth();

                        Bitmap reSized = Bitmap.createScaledBitmap(image_bitmap, (width * 1000) / height,1000, true);
                        photo1.setImageBitmap(reSized); //이미지 뷰에 리사이즈 된 비트맵을 넣는다.


                    } catch (Exception e) {

                        photouri1 = data.getData();
                        Log.i("photouri1 :", "가져온 이미지 " + photouri1);

                        Log.i("매장 추가 - ", "가져온 이미지 " + photouri1);

                        Bitmap bm = null;
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), photouri1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        //절대 경로 가져오기
                        String wholeID = DocumentsContract.getDocumentId(photouri1);
                        // Split at colon, use second item in the array
                        String id = wholeID.split(":")[1];
                        String[] column = { MediaStore.Images.Media.DATA };
                        // where id is equal to
                        String sel = MediaStore.Images.Media._ID + "=?";
                        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        column, sel, new String[]{ id }, null);
                        int columnIndex = cursor.getColumnIndex(column[0]);
                        if (cursor.moveToFirst()) {
                            photo1Path = cursor.getString(columnIndex);
                        }
                        cursor.close();
                        Log.i("매장 추가 - ", "가져온 이미지 절대경로" + photo1Path);
                        Log.i("photo1Path :", "가져온 이미지 " + photo1Path);

                        businessphoto1.setText(photo1Path);


                        //Exif에서 회전 정보 가져오기
                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(photo1Path);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        int exifOrientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                        Log.i("매장 추가 - ","사진 회전 정보 : " + exifOrientation);

                        int exifDegree;
                        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
                        {
                            exifDegree = 90;
                            Log.i("매장 추가 - ","사진 회전 각도 : " + exifDegree);
                        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
                        {
                            exifDegree = 180;
                            Log.i("매장 추가 - ","사진 회전 각도 : " + exifDegree);
                        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
                        {
                            exifDegree = 270;
                            Log.i("매장 추가 - ","사진 회전 각도 : " + exifDegree);
                        }else{
                            exifDegree = 0;
                            Log.i("매장 추가 - ","사진 회전 각도 : " + exifDegree);
                        }
                        Log.i("매장 추가 - ","사진 회전 각도 : " + exifDegree);


                        //가져온 정보로 이미지 회전하기

                        Matrix m = new Matrix();
                        m.setRotate(exifDegree, (float) bm.getWidth() / 2,
                                (float) bm.getHeight() / 2);

                        try
                        {
                            Bitmap converted = Bitmap.createBitmap(bm, 0, 0,
                                    bm.getWidth(), bm.getHeight(), m, true);
                            if(bm != converted)
                            {
                                bm.recycle();
                                bm = converted;
                            }
                        }
                        catch(OutOfMemoryError ex)
                        {
                            // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
                        }

                        //photo1.setImageBitmap(bm);
                        int height = bm.getHeight();
                        int width = bm.getWidth();

                        Bitmap reSized = Bitmap.createScaledBitmap(bm, (width * 1000) / height,1000, true);
                        photo1.setImageBitmap(reSized); //이미지 뷰에 리사이즈 된 비트맵을 넣는다.


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









    public class ImageAsyncTask extends AsyncTask<Void,Integer,String> {
        @Override
        protected String doInBackground(Void... params) {  // 통신을 위한 Thread
            doFileUpload();
            return null;
        }

        public void doFileUpload() {
            URL connectUrl = null;
            HttpURLConnection conn = null;
            DataOutputStream dos = null;

            try {
                String boundary = "^******^";

                // 데이터 경계선
                String delimiter = "\r\n--" + boundary + "\r\n";

                Log.i("이미지 :",  photo1Path);
                FileInputStream fileInputStream = new FileInputStream(photo1Path);
                Log.i("이미지추가 ",  "했음");
                connectUrl = new URL("http://" + MainActivity.ip + ":8080/ttowang/saveImage.jsp");

                // open connection
                conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

               // conn.connect();

                Log.i("이미지 :", "연결??");
                // write data
                dos = new DataOutputStream(conn.getOutputStream());


                StringBuffer pd = new StringBuffer();
       /*         pd.append(delimiter);
                pd.append(setText("userId", String.valueOf(userId)));
                pd.append(delimiter);
                pd.append(setText("businessLicense", businessLicense));
                pd.append(delimiter);
                pd.append(setText("businessName", businessName));
                pd.append(delimiter);
                pd.append(setText("businessTel", businessTel));
                pd.append(delimiter);
                pd.append(setText("businessInfo", businessInfo));
                pd.append(delimiter);
                pd.append(setText("businessTime", businessTime));
                pd.append(delimiter);
                pd.append(setText("businessAddress", businessAddress));
                pd.append(delimiter);
                pd.append(setText("businessMenu", businessMenu));
                pd.append(delimiter);
                pd.append(setText("businessBenefit", businessBenefit));
                pd.append(delimiter);
                pd.append(setText("businessLat",""));
                pd.append(delimiter);
                pd.append(setText("businessLng",""));
                pd.append(delimiter);
                pd.append(setText("businessType", "1"));
                pd.append(delimiter);
                pd.append(setText("businessGroup", businessGroup));
*/
                pd.append(delimiter);
                pd.append(setText("businessName", businessName));
                pd.append(delimiter);
                pd.append(setImage("image", photo1Path));
                pd.append("\r\n");

                dos.writeUTF(pd.toString());

                int bytesAvailable = fileInputStream.available();
                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize); //이미지 전송
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(delimiter);

                //이미지와 텍스트값을 전송할때와 다르게 뒤에 --가 더 붙으며 전송데이터 값의 끝
                fileInputStream.close();
                dos.flush(); //전송완료

                //전송을 완료하고 꼭 서버에서 전송된 결과값을 받아야 완벽하게 업로드된 텍스트
                //이미지가 저장된다. 중요하다 ..
                BufferedReader rd = null;
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                //String result = "";
                while ((line = rd.readLine()) != null) {
                    //result += line;
                    Log.d("BufferedReader: ", line);
                }
                dos.close();
            } catch (MalformedURLException e) {
                Log.d("MalformedURLException", e.toString());
            }catch(Exception ex){
                Log.d("MalformedURLException", ex.toString());
            }
        }

        //텍스트값 처리
        private String setText(String tname, String text){
            return "Content-Disposition: form-data; name=\""+tname+"\"r\n\r\n"+text;
        }

        //이미지값 처리
        private String setImage(String iname, String image){
            return "Content-Disposition: form-data; name=\""+iname+"\";filename=\""+image+"\"\r\n";
        }


        protected void onPostExecute(String result){  //Thread 이후 UI 처리 result는 Thread의 리턴값!!!
            Log.i("myBusinessShop - ", " 내 매장 등록");


            if(result!=null) {
                Log.i("서버에서 받은 전체 내용 : ", result);
                try {

                    JSONObject json = new JSONObject(result);
                    JSONArray jArr = json.getJSONArray("List");
                    json = jArr.getJSONObject(0);
                    Log.i("서버에서 받은 비즈니스 아이디 : ", json.getString("businessId"));
/*
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
                    */
                    myBusinessShop.myBusinessRefresh();
                    //stamp.spinnerRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.i("서버에서 받은 전체 내용 : ", "null");
            }
        }
    }
}
