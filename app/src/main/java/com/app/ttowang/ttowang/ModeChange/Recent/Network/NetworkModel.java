package com.app.ttowang.ttowang.ModeChange.Recent.Network;

import android.util.Log;

import com.app.ttowang.ttowang.Main.MainActivity;
import com.app.ttowang.ttowang.ModeChange.Recent.recent;
import com.loopj.android.http.*;
import com.google.gson.*;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jangjieun on 2016. 8. 19..
 */
public class NetworkModel {
    String ip= MainActivity.ip;

    private String SERVER_URL = "http://"+ip+":8080/ttowang";
    AsyncHttpClient client;

    private static NetworkModel instance;

    public static NetworkModel getInstance(){
        if(instance == null){
            instance = new NetworkModel();
        }
        return instance;
    }

    private NetworkModel(){
        client = new AsyncHttpClient();
    }

    public interface OnNetworkResultListener<T>{
        public void onResult(T result);
        public void onFail(int code);
    }

    public void getRecent(final OnNetworkResultListener<recentList> listener){
        client.get(SERVER_URL + "/checkMembership.do", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ArrayList<recent> List = new ArrayList<>();
                String strResult = new String(responseBody);
                JsonObject jsonObject = new JsonParser().parse(strResult).getAsJsonObject();
                JsonArray jsonArray = jsonObject.get("list").getAsJsonArray();

                Gson gson = new Gson();

                for (int i = 0 ; i < jsonArray.size() ; i++) {
                    recent recent = gson.fromJson(jsonArray.get(i),recent.class);
                    List.add(recent);
                }
                recentList recentList = new recentList();
                recentList.stampList = List;

                listener.onResult(recentList);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}
