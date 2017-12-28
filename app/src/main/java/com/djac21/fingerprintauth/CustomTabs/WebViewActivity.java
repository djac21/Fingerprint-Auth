package com.djac21.fingerprintauth.CustomTabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.djac21.fingerprintauth.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra.url";

    private WebView webView;
    private ProgressBar mProgressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String url = getIntent().getStringExtra(EXTRA_URL);

        webView = findViewById(R.id.webview_content);
        mProgressLoading = findViewById(R.id.progress_loading);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setPageLoadProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        setTitle(url);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setPageLoadProgress(int progress) {
        mProgressLoading.setProgress(progress);
    }

    private void showProgress() {
        mProgressLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mProgressLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showProgress();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hideProgress();
            super.onPageFinished(view, url);
        }
    }
}
