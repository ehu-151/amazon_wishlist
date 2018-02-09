package com.example.ehu.amazon_wishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String URL = intent.getStringExtra("url");

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //レシポンシブデザインがうまく映るように
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl(URL);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String URL) {
                super.onPageFinished(view, URL);

//                String script = "javascript:document.getElementByName('UserName').value='" + id + "';" +
//                        "document.getElementByName('Password').value='" + pass + "';" +
//                        "document.getElementByName('Connect').click();" +
//                        "window.onload = function(){location.reload()}";
//                webView.loadUrl(script);
            }
        });
    }

}
