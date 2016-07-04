package material.danny_jiang.com.xinxingmovie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import material.danny_jiang.com.xinxingmovie.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
