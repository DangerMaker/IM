package com.ez08.im.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;


import com.ez08.im.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/14.
 */
public class NewsDetailActivity extends BackBaseActivity {

    @Bind(R.id.webview)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setCustomTitle("详细内容");

        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
