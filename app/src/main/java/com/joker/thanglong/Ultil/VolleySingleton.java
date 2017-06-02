package com.joker.thanglong.Ultil;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.joker.thanglong.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dlkanth.stethovolley.StethoVolleyStack;

/**
 * Created by joker on 5/19/17.
 */

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private String mBaseUrl;
    private String token;
    private VolleySingleton(Context mCtx) {
        this.mCtx = mCtx;
        mBaseUrl = mCtx.getResources().getString(R.string.url);
        mRequestQueue = getRequestQueue();
        token =PreferenceManager.getDefaultSharedPreferences(mCtx).getString("token","");

    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key. It should not be mCtx context,
            // or else RequestQueue won't last for the lifetime of your app
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(),new StethoVolleyStack());
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    private String contructUrl(String method) {
        return mBaseUrl + "/" + method;
    }

    //Setup method GET,SET,POST,DELTE
    public void get(String method, JSONObject jsonRequest,
                    Response.Listener<JSONObject> listener, final Context activity) {

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, contructUrl(method), jsonRequest, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handlerError(error,activity);
            }
        }) {
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

    public void post(final Context context, String method, JSONObject jsonRequest,
                     Response.Listener<JSONObject> listener) {

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, contructUrl(method), jsonRequest, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handlerError(error,context);
            }
        }){
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

    public void delete(final Context context, String method, JSONObject jsonRequest,
                       Response.Listener<JSONObject> listener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.DELETE, contructUrl(method), jsonRequest, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handlerError(error,context);
            }
        }){
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

    public void put(final Context context, String method, JSONObject jsonRequest,
                    Response.Listener<JSONObject> listener){

        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.PUT, contructUrl(method), jsonRequest, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handlerError(error,context);
            }
        }){
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

//Resolve error code from Json respone
    public int checkErrorCode(VolleyError error){
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
    public String trimMessage(String json, String key){
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
    
    public void handlerError(VolleyError error, Context activity){
        {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                DialogUtil.alert("Không có kết nối mạng, vui lòng kiểm tra lại",activity);
            } else if (error instanceof AuthFailureError) {
                //TODO
                DialogUtil.alert("Lỗi xác thực,vui lòng đăng nhập lại"+error.networkResponse.statusCode,activity);
            } else if (error instanceof ServerError) {
                //TODO
                if (error.networkResponse.statusCode == 204){
                    Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    DialogUtil.alert("Lỗi hệ thống: "+ error.networkResponse.statusCode,activity);
                }
            } else if (error instanceof NetworkError) {
                //TODO
                DialogUtil.alert("Lỗi mạng, xin vui lòng thử lại",activity);
            } else if (error instanceof ParseError) {
                //TODO
//                DialogUtil.alert("Lỗi trong quá trình lấy dữ liệu ",activity);
            }
//                Log.d("findUser",VolleySingleton.getInstance(mCtx).checkErrorCode(error)+"");
        }

    }

}
