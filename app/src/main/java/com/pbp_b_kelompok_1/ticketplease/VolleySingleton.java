package com.pbp_b_kelompok_1.ticketplease;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private VolleySingleton(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if(mVolleySingleton == null){
            mVolleySingleton = new VolleySingleton(context);
        }
        return mVolleySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
