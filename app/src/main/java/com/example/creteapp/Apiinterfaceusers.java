package com.example.creteapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

interface Apiinterfaceusers {
    @GET("users")
    Call<List<users>> allResponse(
            @Header("Authorization") String token
    );
}