package com.github.axet.bookreader.fragments

import android.app.Dialog
import android.os.Bundle
import android.webkit.WebView
import com.github.axet.bookreader.custom.webview.WebViewLoadingListener
import com.github.axet.bookreader.dialog.BaseDialogFragmentBinding
import com.github.axet.bookreader.viewmodel.MainViewModel
import org.geometerplus.zlibrary.ui.android.R
import org.geometerplus.zlibrary.ui.android.databinding.FragmentWebviewReadBookBinding

class FragmentReadBookWebView constructor(private val url: String, private val titleBook: String) :
  BaseDialogFragmentBinding<FragmentWebviewReadBookBinding, MainViewModel>(TYPE_FULL_SCREEN),
  WebViewLoadingListener {
  var actionClose: (() -> Unit)? = null

  companion object {
    val TAG = "FragmentReadBookWebView"
  }

  override val contentViewLayoutId: Int
    get() = R.layout.fragment_webview_read_book

  override fun initListeners() {
    mBinding.lifecycleOwner = this
    mBinding.listener = this
    mBinding.imgBack.setOnClickListener {
      actionClose?.invoke()
      dismissAllowingStateLoss()
    }
  }

  override fun initData() {
    registerForContextMenu(mBinding.webview)
    mBinding.zoomable = true
    mBinding.url = url
    mBinding.titleBook = titleBook
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog: Dialog = object : Dialog(requireActivity(), theme) {
      override fun onBackPressed() {
        actionClose?.invoke()
        dismissAllowingStateLoss()
      }
    }
    dialog.setCanceledOnTouchOutside(false)
    return dialog
  }

  override fun subscribeToViewModel() {

  }

  override val classViewModel: Class<MainViewModel>
    get() = MainViewModel::class.java

  override fun onOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
    url?.let {
      webView?.loadUrl(it)
    }
    return true

  }

  override fun onLoadingPageFinish(view: WebView?, url: String?) {

  }

  override fun onLoadingPageStart(view: WebView?, url: String?) {

  }

  override fun onProgressChanged(view: WebView?, progress: Int) {

  }

}