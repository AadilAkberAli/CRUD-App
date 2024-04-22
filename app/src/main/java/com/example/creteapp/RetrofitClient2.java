package com.example.creteapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {

    private static RetrofitClient2 instance = null;
    private postpage.ApiInterface myApi;

    public RetrofitClient2() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gorest.co.in/public/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(postpage.ApiInterface.class);
    }

    public static synchronized RetrofitClient2 getInstance() {
        if (instance == null) {
            instance = new RetrofitClient2();
        }
        return instance;
    }

    public postpage.ApiInterface getMyApi() {
        return myApi;
    }
}