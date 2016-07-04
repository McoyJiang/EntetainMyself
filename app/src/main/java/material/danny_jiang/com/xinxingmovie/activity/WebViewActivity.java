package material.danny_jiang.com.xinxingmovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import material.danny_jiang.com.xinxingmovie.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = ((WebView) findViewById(R.id.webView));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);

        url = getIntent().getStringExtra("url");

        webView.loadUrl(url);
    }
}
