package com.snystudio.simpleaplication.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    public static ApiService getApiServices(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://94.74.86.174:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return retrofit.create(ApiService.class);
    }
}
