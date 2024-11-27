package org.bookreader.custom.webview;

import android.webkit.WebView;

/**
 * Created by chuongvd on 3/15/19.
 */
public interface WebViewLoadingListener {

    boolean onOverrideUrlLoading(WebView webView, String url);

    void onLoadingPageFinish(WebView view, String url);

    void onLoadingPageStart(WebView view, String url);

    void onProgressChanged(WebView view, int progress);
}