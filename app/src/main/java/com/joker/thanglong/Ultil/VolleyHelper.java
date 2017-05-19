package com.joker.thanglong.Ultil;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.joker.thanglong.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import Entity.EntityUserProfile;

/**
 * Created by joker on 3/17/17.
 */

public class VolleyHelper {
    private final Context mContext;
    private final RequestQueue mRequestQueue;
//    private final ImageLoader mImageLoader;
    private final String mBaseUrl;
//    private final HashMap mhashMap;
    private String token;

    public VolleyHelper(Context c, String baseUrl){
        mContext = c;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mBaseUrl = baseUrl;
        EntityUserProfile profile = ProfileInstance.getProfileInstance(c).getProfile();
        token=profile.getToken();
//        mhashMap =hashMap;
//        mImageLoader = new ImageLoader(mRequestQueue, ImageLoader.ImageCache im);
    }
    public VolleyHelper(Context c, String baseUrl,boolean isLogin){
        mContext = c;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mBaseUrl = baseUrl;
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

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.GET, contructUrl(method), jsonRequest, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("token", token);
                return map;
            }
        };
        mRequestQueue.add(objRequest);
    }

    public void put(String method, JSONObject jsonRequest,
                    Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.PUT, contructUrl(method), jsonRequest, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("token", MainActivity.token);
                return map;
            }
        };
        mRequestQueue.add(objRequest);
    }

    public void post(String method, JSONObject jsonRequest,
                     Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.POST, contructUrl(method), jsonRequest, listener, errorListener);
        objRequest.setShouldCache(true);
        mRequestQueue.add(objRequest);

    }

    public void delete(String method, JSONObject jsonRequest,
                       Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.DELETE, contructUrl(method), jsonRequest, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("token", MainActivity.token);
                return map;
            }
        };
        mRequestQueue.add(objRequest);
    }


    public void postHeader(String method, JSONObject jsonRequest,
                     Listener<JSONObject> listener, ErrorListener errorListener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Method.POST, contructUrl(method), jsonRequest, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("token", MainActivity.token);
                return map;
            }
        };
        objRequest.setShouldCache(true);
        mRequestQueue.add(objRequest);

    }

    public JSONObject getAsync(String method)
    {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Method.GET,contructUrl(method), new JSONObject(), future, future){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("token", MainActivity.token);
                Log.d("tokenMap",MainActivity.token);
                return map;
            }
        };
        mRequestQueue.add(request);
        JSONObject response = null;
        try {
             response = future.get(30, TimeUnit.SECONDS); // this will block
        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            // exception handling
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return response;
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
