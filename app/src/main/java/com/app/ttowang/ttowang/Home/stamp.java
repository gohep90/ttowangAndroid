package com.app.ttowang.ttowang.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ttowang.ttowang.R;

/**
 * Created by srpgs2 on 2016-08-01.
 */
public class stamp extends android.support.v4.app.ListFragment {

    public final static String ITEMS_COUNT_KEY = "Main_C$ItemsCount";

    static ArrayAdapter<String> adapter;
    int number;

    public View onCreateView(LayoutInflater in, ViewGroup ctn, Bundle savedState)
    {
        View view = in.inflate(R.layout.stamp, ctn, false);
        number = getArguments() != null ? getArguments().getInt("number") : 1;
        return view;
    }

    public static stamp createInstance(int itemsCount) {
        stamp mainC = new stamp();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        mainC.setArguments(bundle);
        return mainC;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("ArrayListFragment", "onCreate");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, home.thisisstamp);
        //adapter = new ArrayAdapter<String>(getActivity(), R.layout.coupon_item, home.thisisstamp);
        setListAdapter(adapter);
    }

    /*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "FragmentList position - " +position+" Item clicked: id - " + id,
                Toast.LENGTH_LONG).show();
    }
    */
}
