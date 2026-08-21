package com.diskwalalinks.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

                // Normal website links
                if (url.startsWith("http://")
                        || url.startsWith("https://")) {
                    return false;
                }

                // Diskwala / intent links
                if (url.startsWith("intent://")) {

                    try {
                        Intent intent = Intent.parseUri(
                                url,
                                Intent.URI_INTENT_SCHEME
                        );

                        if (intent.resolveActivity(
                                getPackageManager()) != null) {

                            startActivity(intent);
                            return true;
                        }

                        // Do NOT open Play Store
                        return true;

                    } catch (Exception e) {
                        return true;
                    }
                }

                // Other app links
                try {
                    Intent intent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url)
                    );

                    if (intent.resolveActivity(
                            getPackageManager()) != null) {

                        startActivity(intent);
                    }

                } catch (Exception e) {
                    // Ignore
                }

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
