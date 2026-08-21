package com.diskwalalinks.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {

                if (url == null) {
                    return false;
                }

                // Always keep normal web pages inside this app
                if (url.startsWith("https://")
        || url.startsWith("http://")) {

    try {
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
        );

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            return true;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
                }

                // Open Diskwala app for intent:// links
                if (url.startsWith("intent://")) {
                    try {
                        Intent intent = Intent.parseUri(
                                url,
                                Intent.URI_INTENT_SCHEME
                        );

                        intent.setPackage("com.diskwalaapp");

                        if (intent.resolveActivity(
                                getPackageManager()) != null) {

                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        // Do nothing
                    }

                    return true;
                }

                // Ignore all other schemes
                return true;
            }
        });

        webView.loadUrl(
                "https://appireddym332-droid.github.io/diskwala-links/"
        );
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
