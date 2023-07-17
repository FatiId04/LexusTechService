package com.example.lexuxtechservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBoitier {


    private static String BASE_URL="http://192.168.0.197/apiLexus/getBoitier.php/";
    private static RetrofitBoitier retrofitBoitier;
    private static Retrofit retrofit = null;
    private OkHttpClient.Builder builder=new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();

    private RetrofitBoitier(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build())
                .build();
    }
    public static  synchronized  RetrofitBoitier getInstance(){
        if(retrofitBoitier==null){
            retrofitBoitier=new RetrofitBoitier();
        }
        return retrofitBoitier;
    }

    Api getApi(){
        return retrofit.create(Api.class);
    }




}
