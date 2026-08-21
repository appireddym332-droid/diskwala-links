package com.diskwalalinks.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient() {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url == null) {
            return false;
        }

        // Normal web links
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return false;
        }

        // Handle intent:// links
if (url.startsWith("intent://")) {
    try {
        Intent intent = Intent.parseUri(
                url,
                Intent.URI_INTENT_SCHEME
        );

        // Open directly in Diskwala app
        intent.setPackage("com.diskwalaapp");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            return true;
        }

        // App not available - do not open Play Store
        return true;

    } catch (Exception e) {
        return true;
    }
}
        return true;
    }
});

        webView.loadUrl("https://appireddym332-droid.github.io/diskwala-links/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
