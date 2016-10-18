package com.synergyforce.rashel.volleydemo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Estique Ahmed Rashel
 * Singleton Class
 */

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton( Context context ){

        this.context = context;
        requestQueue = getRequestQueue();

    }


    public RequestQueue getRequestQueue(){

        if( requestQueue == null ){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized VolleySingleton getmInstance(Context context){

        if( mInstance == null ){
            mInstance = new VolleySingleton(context);
        }

        return  mInstance;
    }

    public<T> void addRequestQueue(Request<T> request){

        requestQueue.add(request);

    }
}
