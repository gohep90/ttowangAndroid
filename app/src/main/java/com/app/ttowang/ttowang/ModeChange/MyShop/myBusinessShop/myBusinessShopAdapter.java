package com.app.ttowang.ttowang.ModeChange.MyShop.myBusinessShop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;

import java.util.ArrayList;

/**
 * Created by Park on 2016-08-05.
 */
public class myBusinessShopAdapter extends BaseAdapter {
    int userId;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<myBusinessShopItem> listViewItemList = new ArrayList<myBusinessShopItem>() ;

    // ListViewAdapter의 생성자
    public myBusinessShopAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mybusinessshop_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView mybusineename = (TextView) convertView.findViewById(R.id.mybusineename) ;
        TextView mybusineetype = (TextView) convertView.findViewById(R.id.mybusineetype) ;
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final myBusinessShopItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mybusineename.setText(listViewItem.getBusinessName());
        if(listViewItem.businessType.equals("1")) {
            mybusineetype.setText("매장");
        }else{
            mybusineetype.setText("개인");
        }
        convertView.setOnClickListener(new View.OnClickListener(){  //수정
            @Override
            public void onClick(View v) {

                Log.i("내 매장 종류",listViewItem.getBusinessGroup() + "");

                if(listViewItem.businessType.equals("1")) {   //정식 매장 이면
                    Intent intent = new Intent(myBusinessShop.mContext, myBusinessStoreEdit.class);   //인텐트로 넘겨줄건데요~
                    intent.putExtra("userId", userId);
                    intent.putExtra("businessId", listViewItem.getBusinessId());
                    intent.putExtra("businessLicense", listViewItem.getBusinessLicense());
                    intent.putExtra("businessName", listViewItem.getBusinessName());
                    intent.putExtra("businessTel", listViewItem.getBusinessTel());
                    intent.putExtra("businessInfo", listViewItem.getBusinessInfo());
                    intent.putExtra("businessTime", listViewItem.getBusinessTime());
                    intent.putExtra("businessAddress", listViewItem.getBusinessAddress());
                    intent.putExtra("businessMenu", listViewItem.getBusinessMenu());
                    intent.putExtra("businessBenefit", listViewItem.getBusinessBenefit());
                    intent.putExtra("businessGroup", listViewItem.getBusinessGroup());
                    intent.putExtra("position",position);
                    myBusinessShop.mContext.startActivity(intent);
                }else{  //개인 사업자이면
                    Intent intent = new Intent(myBusinessShop.mContext, myBusinessIndividualEdit.class);   //인텐트로 넘겨줄건데요~
                    intent.putExtra("userId", userId);
                    intent.putExtra("businessId", listViewItem.getBusinessId());
                    intent.putExtra("businessName", listViewItem.getBusinessName());
                    intent.putExtra("businessTel", listViewItem.getBusinessTel());
                    intent.putExtra("businessInfo", listViewItem.getBusinessInfo());
                    intent.putExtra("businessBenefit", listViewItem.getBusinessBenefit());
                    intent.putExtra("businessGroup", listViewItem.getBusinessGroup());
                    intent.putExtra("position",position);
                    myBusinessShop.mContext.startActivity(intent);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(myBusinessShop.mContext,"롱클릭", Toast.LENGTH_SHORT).show();
                return false;
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
    public void addItem(
            String businessType,
            String businessId,
            String businessLicense,
            String businessName,
            String businessTel,
            String businessInfo,
            String businessTime,
            String businessAddress,
            String businessMenu,
            String businessBenefit,
            String businessGroup) {

        myBusinessShopItem item = new myBusinessShopItem();

        item.setBusinessType(businessType);
        item.setBusinessId(businessId);
        item.setBusinessLicense(businessLicense);
        item.setBusinessName(businessName);
        item.setBusinessTel(businessTel);
        item.setBusinessInfo(businessInfo);
        item.setBusinessTime(businessTime);
        item.setBusinessAddress(businessAddress);
        item.setBusinessMenu(businessMenu);
        item.setBusinessBenefit(businessBenefit);
        item.setBusinessGroup(businessGroup);
        listViewItemList.add(item);
    }
}