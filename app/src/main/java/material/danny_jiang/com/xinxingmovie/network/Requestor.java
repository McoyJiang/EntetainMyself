package material.danny_jiang.com.xinxingmovie.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import material.danny_jiang.com.xinxingmovie.bean.MovieDetailBean;
import material.danny_jiang.com.xinxingmovie.bean.NewComingBean;

/**
 * axing created
 */
public class Requestor {
    public static void requestNewComingMovie(RequestQueue requestQueue, String url, final Handler handler) {
        JSONObject response = null;
        //RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", "onResponse: " + response.toString());
                        List<NewComingBean> newComingBeans = Parser.parseNewComingJSON(response);
                        Message msg = Message.obtain();
                        msg.obj = newComingBeans;
                        handler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        Message msg = Message.obtain();
                        msg.obj = error;
                        handler.sendMessage(msg);
                    }
                });

        requestQueue.add(request);
    }

    public static void requestMovieDetail(RequestQueue requestQueue, String url, final Handler handler) {
        JSONObject response = null;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        MovieDetailBean movieDetailBean = gson.fromJson(response.toString(), MovieDetailBean.class);
                        Message msg = Message.obtain();
                        msg.obj = movieDetailBean;
                        handler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        Message msg = Message.obtain();
                        msg.obj = error;
                        handler.sendMessage(msg);
                    }
                });

        requestQueue.add(request);
    }

    public static void requestJsonObject(String url, final Handler handler, final Class tClass){
        JSONObject response = null;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", "onResponse: " + response.toString());
                        Gson gson = new Gson();
                        Object fromJson = gson.fromJson(response.toString(), tClass);
                        Message msg = Message.obtain();
                        msg.obj = fromJson;
                        handler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        Message msg = Message.obtain();
                        msg.obj = error;
                        handler.sendMessage(msg);
                    }
                });

        VolleySingleTon.newInstance().getRequestQueue().add(request);
    }

    public static void requestJsonArray(String url, final Handler handler, final Class tClass){
        JSONObject response = null;

//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
//                url,
//                (String) null,
//                new Response.Listener<JsonArray>() {
//                    @Override
//                    public void onResponse(JsonArray response) {
//                        Log.e("TAG", "onResponse: " + response.toString());
//                        Gson gson = new Gson();
//                        Object fromJson = gson.fromJson(response.toString(), tClass);
//                        Message msg = Message.obtain();
//                        msg.obj = fromJson;
//                        handler.sendMessage(msg);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
//                        Message msg = Message.obtain();
//                        msg.obj = error;
//                        handler.sendMessage(msg);
//                    }
//                });

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("TAG", "onResponse: " + response.toString());

                        List<Object> objs = new ArrayList<>();
                        Gson gson = new Gson();
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response.toString());
                        JsonArray jsonArray = null;
                        if(jsonElement.isJsonArray()){
                            jsonArray = jsonElement.getAsJsonArray();
                        }

                        Iterator it = jsonArray.iterator();
                        while(it.hasNext()){
                            JsonElement e = (JsonElement)it.next();
                            objs.add(gson.fromJson(e, tClass));
                        }

                        //Object fromJson = gson.fromJson(response.toString(), tClass);
                        Message msg = Message.obtain();
                        msg.obj = objs;
                        handler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse: " + error.getMessage());
                Message msg = Message.obtain();
                msg.obj = error;
                handler.sendMessage(msg);
            }
        });

        VolleySingleTon.newInstance().getRequestQueue().add(jsonArrayRequest);
    }
}
