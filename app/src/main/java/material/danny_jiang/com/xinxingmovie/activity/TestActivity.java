package material.danny_jiang.com.xinxingmovie.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.utils.SquareUtils;
import material.danny_jiang.com.xinxingmovie.view.PieImageView;
import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    @Bind(R.id.iv_detailed_card)
    PieImageView ivDetailedCard;

    private Picasso largeImagepicasso;

    private SquareUtils.ProgressListener listener = new SquareUtils.ProgressListener() {
        @Override public void update(@IntRange(from = 0, to = 100) final int percent) {
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    ivDetailedCard.setProgress(percent);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        largeImagepicasso = SquareUtils.getPicasso(this, listener);

        largeImagepicasso.load("http://konachan.net/sample/861bbf537404c174b310ec62a3b00be8/Konachan.com%20-%20224363%20sample.jpg")
                .placeholder(ivDetailedCard.getDrawable())
                        //fix oom
                .config(Bitmap.Config.ARGB_4444)
                .into(ivDetailedCard);
    }
}
