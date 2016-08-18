package com.app.ttowang.ttowang.Main.Home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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


public class homeFragment extends android.support.v4.app.Fragment{

    private View rootView;
    private TextView businessName,myRemainCoupon,myUsedCoupon,businessLocation;
    private ImageView mybusinessimg;
    private int number = -1;
    private RelativeLayout viewlayout;

    String ip= MainActivity.ip;



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

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("ArrayListFragment", "onCreate");
        number = getArguments() != null ? getArguments().getInt("number") : 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container,false);
        viewlayout = (RelativeLayout) view.findViewById(R.id.viewlayout);
        businessName = (TextView) view.findViewById(R.id.businessName);
        businessLocation = (TextView) view.findViewById(R.id.businessLocation);
        myRemainCoupon = (TextView) view.findViewById(R.id.myRemainCoupon);
        myUsedCoupon = (TextView) view.findViewById(R.id.myUsedCoupon);


        mybusinessimg = (ImageView) view.findViewById(R.id.mybusinessimg); //매장 사진 교체

        Glide.with(container.getContext()).load("http://" + ip + ":8080/ttowang/image/"+home.myAllBusiness.get(number).get(3)).into(mybusinessimg);

        businessName.setText(String.valueOf(home.myAllBusiness.get(number).get(1)));      //매장 이름
        businessLocation.setText(String.valueOf(home.myAllBusiness.get(number).get(2)));//매장 주소
        myRemainCoupon.setText(String.valueOf(home.myAllBusiness.get(number).get(4))); //남은 쿠폰 갯수
        myUsedCoupon.setText(String.valueOf(home.myAllBusiness.get(number).get(5)));     //사용한 쿠폰 갯수

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

                return false;
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

    }




    private void Dialog(final int number) {
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
                home.refresh();
                //home.PagerAdapter.notifyDataSetChanged;
                Toast.makeText(MainActivity.mContext, "삭제합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        _alert.show();
    }

}
