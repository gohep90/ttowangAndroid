package com.app.ttowang.ttowang.ModeChange.Recent.Network;

import com.app.ttowang.ttowang.ModeChange.Recent.recent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jangjieun on 2016. 8. 19..
 */
public class NetworkModel {
    String ip="14.63.213.208";

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

    public void getSelectRecent(final OnNetworkResultListener<recentList> listener,String businessId){
        RequestParams params = new RequestParams();
        params.put("BUSINESSID", businessId);

        client.get(SERVER_URL + "/selectRecentList.do",params, new AsyncHttpResponseHandler() {
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

    public void getDeleteRecent(final OnNetworkResultListener<recentList> listener,
                                String userId, String businessId, String StampDate, String StampNum){
        RequestParams params = new RequestParams();
        params.put("USERID", userId);
        params.put("BUSINESSID", businessId);
        params.put("STAMPDATE", StampDate);
        params.put("STAMPNUM", StampNum);

        client.get(SERVER_URL + "/deleteRecentStamp.do", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {  }
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {   }
        });
    }
}
