package com.app.ttowang.ttowang.Main.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        Glide.with(container.getContext()).load("http://" + ip + ":8080/ttowang/image/"+home.photoName.get(number)).into(mybusinessimg);

        businessName.setText(String.valueOf(home.businessName.get(number)));      //매장 이름
        businessLocation.setText(String.valueOf(home.businessLocation.get(number)));//매장 주소
        myRemainCoupon.setText(String.valueOf(home.remainStamp.get(number))); //남은 쿠폰 갯수
        myUsedCoupon.setText(String.valueOf(home.usedStamp.get(number)));     //사용한 쿠폰 갯수

        String as = String.valueOf(home.usedStamp.get(number));

        viewlayout.setOnClickListener(new View.OnClickListener() {    //시간 누르면
            @Override
            public void onClick(View v) {               //쿠폰을 클릭하면 토스트 뜸
                Toast.makeText(getActivity(), (String)home.businessName.get(number), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), business.class);
                intent.putExtra("businessId", String.valueOf(home.businessId.get(number)));
                startActivity(intent);
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






}
