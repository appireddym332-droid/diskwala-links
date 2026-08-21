package com.diskwalalinks.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request) {

                return handleUrl(view, request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url) {

                return handleUrl(view, url);
            }
        });

        webView.loadUrl("https://appireddym332-drops.github.io/diskwala-links/");
    }

    private boolean handleUrl(WebView view, String url) {

        if (url == null) {
            return false;
        }

        // Normal web links
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return false;
        }

        // Open intent links directly in the installed app
        if (url.startsWith("intent://")) {

            try {
                Intent intent = Intent.parseUri(
                        url,
                        Intent.URI_INTENT_SCHEME
                );

                // Try opening the target app directly
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    return true;
                }

                // If app is not installed, use the web fallback
                Uri data = intent.getData();

                if (data != null &&
                        (data.toString().startsWith("http://")
                        || data.toString().startsWith("https://"))) {

                    view.loadUrl(data.toString());
                    return true;
                }

            } catch (Exception e) {
                // Do nothing - don't open Play Store
            }

            return true;
        }

        // Other app links
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        } catch (Exception e) {
            // Ignore
        }

        return true;
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
