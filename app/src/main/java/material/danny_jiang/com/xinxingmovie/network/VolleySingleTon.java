package material.danny_jiang.com.xinxingmovie.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import material.danny_jiang.com.xinxingmovie.MyApplication;

/**
 * Created by axing on 16/6/26.
 */
public class VolleySingleTon {
    private static VolleySingleTon instance;
    private final RequestQueue requestQueue;

    public VolleySingleTon() {
        requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static VolleySingleTon newInstance() {
        if (instance == null) {
            instance = new VolleySingleTon();
        }
        return instance;
    }
}
