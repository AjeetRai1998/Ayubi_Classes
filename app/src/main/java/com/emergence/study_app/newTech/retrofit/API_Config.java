package com.emergence.study_app.newTech.retrofit;


import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class API_Config {
  //  public static String URL = "http://ayubi.ayubiclasses.in/API/";
    public static String URL = "https://ayubiclasses.in/ayubi/API/";
//    public static String URL = "http://ayubi.emergenceinfotech.in/AYUBI/API/";

    public static ApiServices getApiClient_ByPost() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(okHttpClient);
        Retrofit retrofit = builder.build();
        return retrofit.create(ApiServices.class);
    }
}