package com.app.ttowang.ttowang.ModeChange.Recent.Network;

import android.util.Log;

import com.app.ttowang.ttowang.ModeChange.Recent.recent;
import com.app.ttowang.ttowang.ModeChange.Recent.recentSpinner;
import com.loopj.android.http.*;
import com.google.gson.*;

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

public void  getSelectSpinner(final OnNetworkResultListener<recentSpinnerList> listener, final String userId){
        RequestParams params = new RequestParams();
        params.put("userId", userId);

        client.get(SERVER_URL + "/spinnerList.do", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ArrayList<recentSpinner> List = new ArrayList<>();
                String strResult = new String(responseBody);
                JsonObject jsonObject = new JsonParser().parse(strResult).getAsJsonObject();
                JsonArray jsonArray = jsonObject.get("spinnerList").getAsJsonArray();

                Gson gson = new Gson();

                for (int i = 0; i < jsonArray.size(); i++) {
                    recentSpinner recentSpinner = gson.fromJson(jsonArray.get(i), recentSpinner.class);
                    List.add(recentSpinner);
                }

                recentSpinnerList recentSpinnerList = new recentSpinnerList();
                recentSpinnerList.spinner = List;

                listener.onResult(recentSpinnerList);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("지은", "리스너리스트 수신 실패");
                error.getMessage();
            }
        });
    }

    public void getSelectRecent(final OnNetworkResultListener<recentList> listener,String businessId){
        RequestParams params = new RequestParams();
        params.put("BUSINESSID", businessId);

        client.get(SERVER_URL + "/selectRecentList.do", params, new AsyncHttpResponseHandler() {
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
                Log.i("지은", "전체리스트 수신 실패");
                error.getMessage();
            }
        });
    }

    public void getDeleteRecent(String userId, String businessId, String StampDate, String StampNum) {
        RequestParams params = new RequestParams();
        params.put("USERID", userId);
        params.put("BUSINESSID", businessId);
        params.put("STAMPDATE", StampDate);
        params.put("STAMPNUM", StampNum);

        client.get(SERVER_URL + "/deleteRecentStamp.do", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("지은","DELETE 성공");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("지은","DELETE 실패");
                error.getMessage();
            }
        });
    }
}
