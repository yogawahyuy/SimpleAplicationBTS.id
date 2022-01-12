package com.snystudio.simpleaplication.config;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<JsonObject>registerRawJSON(@Body JsonObject jsonObject);
    @POST("login")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<JsonObject>loginRawJSON(@Body JsonObject jsonObject);

    @GET("checklist")
    Call<JsonObject> getAllChecklist(@Header("Authorization") String token);
    @POST("checklist")
    Call<JsonObject> saveChecklist(@Header("Authorization")String token,@Body JsonObject jsonObject);
    @DELETE("checklist/{checklistid}")
    Call<JsonObject> deleteChceklist(@Header("Authorization")String token, @Path("checklistid") int checkid);
}
