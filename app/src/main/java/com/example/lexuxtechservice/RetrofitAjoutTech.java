package com.example.lexuxtechservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAjoutTech {

    private static String BASE_URL="http://192.168.0.197/apiLexus/ajout_tech.php/";
    private static RetrofitAjoutTech  retrofitAjoutTech ;
    private static Retrofit retrofit = null;
    private OkHttpClient.Builder builder=new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();

    private RetrofitAjoutTech (){

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
    public static  synchronized  RetrofitAjoutTech  getInstance(){
        if(retrofitAjoutTech ==null){
            retrofitAjoutTech =new RetrofitAjoutTech();
        }
        return retrofitAjoutTech ;
    }

    Api getApi(){
        return retrofit.create(Api.class);
    }



}
