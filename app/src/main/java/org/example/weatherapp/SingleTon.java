package org.example.weatherapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleTon {

    private static SingleTon mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private SingleTon(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized SingleTon getInstance(Context context){
        if(mInstance==null){
            mInstance = new SingleTon(context);
        }
        return mInstance;
    }

    public void addToRequestQue(Request request){
        requestQueue.add(request);
    }
}
