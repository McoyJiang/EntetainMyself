package material.danny_jiang.com.xinxingmovie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Method;

import material.danny_jiang.com.xinxingmovie.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
                Log.e("TAG", "onMenuOpened: " + e.getMessage());
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.startMovie:
                Intent intentMovie = new Intent(this, MovieActivity.class);
                startActivity(intentMovie);
                break;
            case R.id.startVideo:
                Intent intentVideo = new Intent(this, FunnyVideoActivity.class);
                startActivity(intentVideo);
                break;
            case R.id.startTest:
                Intent intentTest = new Intent(this, TestActivity.class);
                startActivity(intentTest);
                break;
        }
    }
}
