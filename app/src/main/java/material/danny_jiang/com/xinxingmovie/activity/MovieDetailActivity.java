package material.danny_jiang.com.xinxingmovie.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import material.danny_jiang.com.xinxingmovie.R;
import material.danny_jiang.com.xinxingmovie.bean.MovieDetailBean;
import material.danny_jiang.com.xinxingmovie.network.Requestor;
import material.danny_jiang.com.xinxingmovie.network.VolleySingleTon;
import material.danny_jiang.com.xinxingmovie.utils.BitmapThumbnailHelper;
import material.danny_jiang.com.xinxingmovie.utils.MyConstans;
import material.danny_jiang.com.xinxingmovie.view.MoreTextView;

public class MovieDetailActivity extends AppCompatActivity {

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("TAG", "handleMessage: ");

            Object obj = msg.obj;
            if (obj instanceof MovieDetailBean) {
                Log.e("TAG", "handleMessage: 11111");
                MovieDetailBean movieDetailBean = (MovieDetailBean) obj;

                String reason = movieDetailBean.getReason();
                if (MyConstans.QUERY_SUCCEED.equals(reason)) {
                    Log.e("TAG", "handleMessage: 2222");
                    MovieDetailBean.ResultBean result = movieDetailBean.getResult();
                    if (result != null) {
                        instantiateResult(result);
                    }
                }
            }
        }
    };

    @Bind(R.id.movieDetailImage)
    ImageView movieDetailImage;
    @Bind(R.id.movieDetailTitle)
    TextView movieDetialTitle;
    @Bind(R.id.movieDetailCountry)
    TextView movieDetialCountry;
    @Bind(R.id.movieDetailType)
    TextView movieDetialType;
    @Bind(R.id.movieDetailDirector)
    TextView movieDetialDirector;
    @Bind(R.id.movieDetailTime)
    TextView movieDetialTime;
    @Bind(R.id.movieDetailActs)
    TextView movieDetialActs;
    private LinearLayout playLinkLayout;
    private HorizontalScrollView playLinkScrollView;
    private LinearLayout actorLinearLayout;
    private LinearLayout recommendLayout;
    private MoreTextView movieDetailDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_scroll_movie_detail);
        //View container = getLayoutInflater().inflate(R.layout.activity_movie_detail, null);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        initViews();

        getNetworkData(getIntent().getStringExtra("movieName"));
    }

    private void instantiateResult(MovieDetailBean.ResultBean result) {
        movieDetialTitle.setText(result.getTitle());
        movieDetialCountry.setText(result.getArea());
        movieDetialType.setText(result.getTag());
        movieDetialTime.setText(result.getYear() + "上映");
        movieDetialDirector.setText("导演：" + result.getDir());
        movieDetialActs.setText("主演：" + result.getAct());
        Picasso.with(MovieDetailActivity.this)
                .load(result.getCover())
                .into(movieDetailImage);

        String desc = result.getDesc();
        movieDetailDes.setText(desc);

        List<String> playlinks = result.getPlaylinks();
        initPlayLinks(playlinks);

        List<MovieDetailBean.ResultBean.ActSBean> act_s = result.getAct_s();
        initActorsLayout(act_s);

        List<MovieDetailBean.ResultBean.VideoRecBean> video_rec = result.getVideo_rec();
        initRecommendVideo(video_rec);
    }

    private void initViews() {
        movieDetailDes = ((MoreTextView) findViewById(R.id.movieDetailDes));

        playLinkLayout = ((LinearLayout) findViewById(R.id.playLinksLayout));
        playLinkScrollView = ((HorizontalScrollView) findViewById(R.id.playLinksScrollView));

        actorLinearLayout = ((LinearLayout) findViewById(R.id.actors_linear_layout));

        recommendLayout = ((LinearLayout) findViewById(R.id.recommend_linear_layout));
    }

    private void getNetworkData(String movieName) {
        Requestor.requestMovieDetail(VolleySingleTon.newInstance().getRequestQueue(),
                MyConstans.getMovieDetailUrl(movieName), handler);
    }

    private void initRecommendVideo(List<MovieDetailBean.ResultBean.VideoRecBean> video_rec) {
        if (video_rec == null || video_rec.size() == 0) {
            return;
        }

        for (MovieDetailBean.ResultBean.VideoRecBean videoRecBean : video_rec) {
            String title = videoRecBean.getTitle();
            final String imageUrl = videoRecBean.getCover();
            final String detail_url = videoRecBean.getDetail_url();

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams linearParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(linearParam);

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 200);
            lp.setMargins(0, 10, 10, 0);
            imageView.setLayoutParams(lp);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showWebView(detail_url);
                }
            });

            TextView textView = new TextView(this);
            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textParam.gravity = Gravity.CENTER;
            textView.setLayoutParams(textParam);
            textView.setText(title);

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            recommendLayout.addView(linearLayout);

            Picasso.with(this).load(imageUrl)
                    .placeholder(getDrawable(R.drawable.ic_launcher))
                    .into(imageView);
        }
    }

    private void showWebView(String detail_url) {
        Intent webIntent = new Intent(this, WebViewActivity.class);
        webIntent.putExtra("url", detail_url);
        startActivity(webIntent);
    }

    private void initPlayLinks(List<String> playlinks) {
        Log.e("TAG", "initPlayLinks: " + playlinks.size());

        if (playlinks == null || playlinks.size() == 0) {
            return;
        } else {
            playLinkScrollView.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < playlinks.size(); i++) {
            String playLink = playlinks.get(i);
            Bitmap videoThumbnail = BitmapThumbnailHelper.createVideoThumbnail(playLink);

            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(100, 150);
            imageView.setLayoutParams(lp);
            imageView.setImageBitmap(videoThumbnail);

            playLinkLayout.addView(imageView);
        }
    }

    private void initActorsLayout(List<MovieDetailBean.ResultBean.ActSBean> act_s) {
        if (act_s == null || act_s.size() == 0) {
            return;
        }

        for (MovieDetailBean.ResultBean.ActSBean actSBean : act_s) {
            String name = actSBean.getName();
            final String imageUrl = actSBean.getImage();

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams linearParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(linearParam);

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 200);
            lp.setMargins(0, 10, 10, 0);
            imageView.setLayoutParams(lp);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPhoto(imageUrl);
                }
            });

            TextView textView = new TextView(this);
            LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textParam.gravity = Gravity.CENTER;
            textView.setLayoutParams(textParam);
            textView.setText(name);

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            actorLinearLayout.addView(linearLayout);

            Picasso.with(this).load(imageUrl)
                    .placeholder(getDrawable(R.drawable.ic_launcher))
                    .into(imageView);
        }
    }

        /**
         * 此方法用于点击图片查看大图
         */
        private void showPhoto(String url) {
            final AlertDialog imageDialog = new AlertDialog.Builder(this).create();
            imageDialog.show();
            Window window = imageDialog.getWindow();
            window.setContentView(R.layout.dialog_image_info);
            ImageView imageView = (ImageView) window.findViewById(R.id.imageView);
            LinearLayout linearLayout = (LinearLayout) window.findViewById(R.id.linearLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageDialog.hide();
                }
            });
            Picasso.with(this)
                    .load(url)
                    .into(imageView);
        }
}
