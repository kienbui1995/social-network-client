package com.joker.hoclazada.Ultil;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 3/17/17.
 */

public class VolleyHelper {
    private final Context mContext;
    private final RequestQueue mRequestQueue;
//    private final ImageLoader mImageLoader;
    private final String mBaseUrl;
    private final HashMap mhashMap;

    public VolleyHelper(Context c, String baseUrl,HashMap hashMap){
        mContext = c;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mBaseUrl = baseUrl;
        mhashMap =hashMap;
//        mImageLoader = new ImageLoader(mRequestQueue, ImageLoader.ImageCache im);
    }

    private String contructUrl(String method){
        return mBaseUrl + "/" + method;
    }
//
//    public ImageLoader getImageLoader(){
//        return mImageLoader;
//    }

    public void get(String method, JSONObject jsonRequest,
                    Response.Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.GET, contructUrl(method), jsonRequest, listener, errorListener);
        mRequestQueue.add(objRequest);
    }

    public void put(String method, JSONObject jsonRequest,
                    Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.PUT, contructUrl(method), jsonRequest, listener, errorListener);
        mRequestQueue.add(objRequest);
    }

    public void post(String method, JSONObject jsonRequest,
                     Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.POST, contructUrl(method), jsonRequest, listener, errorListener);
        mRequestQueue.add(objRequest);
    }

    public void delete(String method, JSONObject jsonRequest,
                       Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.DELETE, contructUrl(method), jsonRequest, listener, errorListener);
        mRequestQueue.add(objRequest);
    }

    public void Post(String method,
                     Listener<String> listener, ErrorListener errorListener){

        StringRequest stringRequest = new StringRequest(Method.POST, contructUrl(method), listener, errorListener)

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parrams= new HashMap<String,String>();
                parrams = mhashMap;
                return parrams;
            }
        };
        mRequestQueue.add(stringRequest);
    }

}
