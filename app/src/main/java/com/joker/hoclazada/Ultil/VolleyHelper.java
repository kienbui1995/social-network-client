package com.joker.hoclazada.Ultil;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joker on 3/17/17.
 */

public class VolleyHelper {
    private final Context mContext;
    private final RequestQueue mRequestQueue;
//    private final ImageLoader mImageLoader;
    private final String mBaseUrl;
//    private final HashMap mhashMap;

    public VolleyHelper(Context c, String baseUrl){
        mContext = c;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mBaseUrl = baseUrl;
//        mhashMap =hashMap;
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

//    public void Post(String method,
//                     Listener<String> listener, ErrorListener errorListener){
//
//        StringRequest stringRequest = new StringRequest(Method.POST, contructUrl(method), listener, errorListener)
//
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> parrams= new HashMap<String,String>();
//                parrams = mhashMap;
//                return parrams;
//            }
//        };
//        mRequestQueue.add(stringRequest);
//    }
    public static int checkErrorCode(VolleyError error){
        String json = null;
        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 400:
                    json = new String(response.data);
                    json = trimMessage(json, "code");
                    return Integer.parseInt(json);
                case 401:
                    json = new String(response.data);
                    json = trimMessage(json, "code");
                    return Integer.parseInt(json);
                case 404:
                    json = new String(response.data);
                    json = trimMessage(json, "code");
                    return Integer.parseInt(json);

            }
            //Additional cases
        }
        return 0;
    }
    public static String trimMessage(String json, String key){
        String trimmedString = null;
        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    public static void displayMessage(String toastString){
        Log.d("errorCode",toastString);
    }
//    public static void checkErrorCodeJson(String errorCode,Activity activity)
//    {
//        int code = Integer.parseInt(errorCode);
//        switch (code){
//            case 409:
//                Toast.makeText(activity, "Tên đăng nhập không đúng", Toast.LENGTH_SHORT).show();
//                break;
//            case 410:
//
//        }
//    }
}
