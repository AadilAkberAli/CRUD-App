package com.example.creteapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Apiinterfaceusers myApi;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gorest.co.in/public/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Apiinterfaceusers.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Apiinterfaceusers getMyApi() {
        return myApi;
    }
}