package com.diskwalalinks.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    private static final String HOME_URL =
            "https://appireddym332-droid.github.io/diskwala-links/";

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
                    WebView view,
                    WebResourceRequest request) {

                if (request == null || request.getUrl() == null) {
                    return false;
                }

                return handleUrl(
                        request.getUrl().toString()
                );
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url) {

                return handleUrl(url);
            }

            private boolean handleUrl(String url) {

                if (url == null) {
                    return false;
                }

                // =====================================
                // DISKWALA APP URL
                // =====================================

                if (url.startsWith(
                        "https://www.diskwala.com/app/")) {

                    try {

                        Intent intent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(url)
                        );

                        startActivity(intent);

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    return true;
                }

                // =====================================
                // INTENT:// URL
                // =====================================

                if (url.startsWith("intent://")) {

                    try {

                        Intent intent =
                                Intent.parseUri(
                                        url,
                                        Intent.URI_INTENT_SCHEME
                                );

                        if (intent.resolveActivity(
                                getPackageManager()) != null) {

                            startActivity(intent);

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    return true;
                }

                // =====================================
                // NORMAL HTTPS WEBSITE
                // Keep inside WebView
                // =====================================

                if (url.startsWith("https://")
                        || url.startsWith("http://")) {

                    return false;
                }

                return false;
            }
        });

        // Open GitHub website inside Android app
        webView.loadUrl(HOME_URL);
    }

    @Override
    public void onBackPressed() {

        if (webView != null && webView.canGoBack()) {

            webView.goBack();

        } else {

            super.onBackPressed();

        }
    }
}
