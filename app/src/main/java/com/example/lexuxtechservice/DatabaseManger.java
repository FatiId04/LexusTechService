package com.example.lexuxtechservice;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DatabaseManger {

    private Context context;

    public RequestQueue queue ;

    public DatabaseManger(Context context){
        this.context=context;
        this.queue= Volley.newRequestQueue(context);
    }
}
