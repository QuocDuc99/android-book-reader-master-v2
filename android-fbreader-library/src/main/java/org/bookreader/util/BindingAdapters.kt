package org.bookreader.util

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import org.bookreader.custom.webview.WebViewLoadingListener
import org.geometerplus.zlibrary.R

object BindingAdapters {
  @JvmStatic
  @BindingAdapter(value = ["url", "listener", "supportZoom"], requireAll = false)
  fun setContent(
    webView: WebView, url: String, listerner: WebViewLoadingListener, isSupportZoom: Boolean
  ) {
    val context = webView.context
    webView.settings.javaScriptCanOpenWindowsAutomatically = true
    webView.setInitialScale(1)
    webView.settings.javaScriptEnabled = true
    webView.settings.domStorageEnabled = true
    webView.settings.loadsImagesAutomatically = true
    webView.settings.useWideViewPort = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      webView.settings.allowFileAccessFromFileURLs = true
      webView.settings.allowUniversalAccessFromFileURLs = true
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
    }
    webView.webChromeClient = WebChromeClient()
    webView.webChromeClient = object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (listerner != null) {
          listerner.onProgressChanged(view, newProgress)
        }
      }
    }
    if (isSupportZoom) {
      webView.settings.setSupportZoom(true)
      webView.settings.displayZoomControls = false
      webView.settings.builtInZoomControls = true
    }
    webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return if (listerner != null) {
          listerner.onOverrideUrlLoading(view, url)
        } else {
          view.loadUrl(url)
          true
        }
      }

      override fun onReceivedError(
        view: WebView, errorCode: Int, description: String,
        failingUrl: String
      ) {
        /*if (context == EnetvietApplication.getInstance().getCurrentActiveActivity()) {
                    DialogUtils.showDialog(context, description, null);
                }*/
      }

      @TargetApi(Build.VERSION_CODES.M)
      override fun onReceivedError(
        view: WebView, request: WebResourceRequest,
        error: WebResourceError
      ) {
        /*if (context == EnetvietApplication.getInstance().getCurrentActiveActivity()) {
                    DialogUtils.showDialog(context, error.getDescription().toString(), null);
                }*/
      }

      override fun onReceivedSslError(
        view: WebView, handler: SslErrorHandler,
        error: SslError
      ) {
        var message = "SSL Certificate error."
        when (error.primaryError) {
          SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
          SslError.SSL_EXPIRED -> message = "The certificate has expired."
          SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
          SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
        }
        message += context.getString(R.string.message_continue_confirm)
//        if (context == VieLibApp.applicationContext()) {
//          DialogUtil.showDialog(
//            context, "SSL Certificate Error", message,
//            context.getString(R.string.continue_common),
//            { dialog, which -> handler.proceed() },
//            context.getString(R.string.btndong)
//          ) { dialog, which -> handler.cancel() }
//        }
      }

      override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (listerner != null) {
          listerner.onLoadingPageFinish(view, url)
        }
      }

      override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (listerner != null) {
          listerner.onLoadingPageStart(view, url)
        }
      }
    }
    webView.loadUrl(url!!)
  }
  @BindingAdapter(value = ["isGone"], requireAll = false)
  @JvmStatic
  fun setGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
  }
}