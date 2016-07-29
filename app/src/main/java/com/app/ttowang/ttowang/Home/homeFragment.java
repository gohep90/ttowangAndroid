package com.app.ttowang.ttowang.Home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.R;


public class homeFragment extends android.support.v4.app.Fragment{

    private View rootView;
    private TextView textView;
    private int number = -1;
    private RelativeLayout viewlayout;



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
        View textView = view.findViewById(R.id.text);

        ((TextView) textView).setText((String) home.myBusiness.get(number));

        viewlayout = (RelativeLayout) view.findViewById(R.id.viewlayout);

        viewlayout.setOnClickListener(new View.OnClickListener() {    //시간 누르면
            @Override
            public void onClick(View v) {               //오전 오후 눌러도 시간 선택
                Toast.makeText(getActivity(), (String)home.myBusiness.get(number), Toast.LENGTH_SHORT).show();
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
