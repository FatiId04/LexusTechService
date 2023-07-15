package com.example.lexuxtechservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientRegister {
    private static String BASE_URL="http://172.20.10.9/apiLexus/register.php/";
    private static RetrofitClientRegister retrofitClient;
    private static Retrofit retrofit = null;
    private OkHttpClient.Builder builder=new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();

    private RetrofitClientRegister(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(builder.build())
                .build();
    }
    public static  synchronized  RetrofitClientRegister getInstance(){
        if(retrofitClient==null){
            retrofitClient=new RetrofitClientRegister();
        }
        return retrofitClient;
    }

    Api getApi(){
        return retrofit.create(Api.class);
    }

}
