package material.danny_jiang.com.xinxingmovie;

import android.app.Application;
import android.content.Context;

/**
 * Created by axing on 16/6/26.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
