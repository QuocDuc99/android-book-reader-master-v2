package org.bookreader.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.lifecycle.ViewModelProvider
import org.bookreader.activities.BookActivity
import org.bookreader.activities.BookActivity.OnBackPressed
import org.bookreader.custom.webview.WebViewLoadingListener
import org.bookreader.dialog.BaseDialogFragmentBinding
import org.bookreader.viewmodel.MainViewModel
import org.geometerplus.zlibrary.ui.android.R
import org.geometerplus.zlibrary.ui.android.databinding.FragmentWebviewReadBookBinding

class FragmentReadBookWebView constructor(private val url: String, private var titleBook: String = "VieLib") :
  BaseDialogFragmentBinding<FragmentWebviewReadBookBinding, MainViewModel>(TYPE_FULL_SCREEN),
  WebViewLoadingListener, OnBackPressed {
  var mMainViewModel: MainViewModel? = null
  var actionClickMucLuc: (() -> Unit)? = null
  private val DOMAIN_URL = "https://view.officeapps.live.com/op/embed.aspx?src="
  var actionClose: (() -> Unit)? = null

  companion object {
    val TAG = "FragmentReadBookWebView"
  }

  override val contentViewLayoutId: Int
    get() = R.layout.fragment_webview_read_book

  override fun initListeners() {
    mMainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    mBinding.lifecycleOwner = this
    mBinding.listener = this
    mBinding.imgBack.setOnClickListener {
      actionClose?.invoke()
      dismissAllowingStateLoss()
    }
    (requireActivity() as BookActivity).setOnBackPressed { this.onBackPressed() }
    mBinding.imgMucLuc2.setOnClickListener {
      actionClickMucLuc?.invoke()
    }
  }

  override fun initData() {
    registerForContextMenu(mBinding.webview)
    mBinding.zoomable = true
    mBinding.webview.settings.javaScriptEnabled = true
    mBinding.url = "https://view.officeapps.live.com/op/embed.aspx?src=${url}"
    if(titleBook.isEmpty()) titleBook = "VieLib"
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
    mMainViewModel?.eventShowMucLuc?.observe(this) {
      mBinding.layoutPanel.visibility = if (it) {
        View.VISIBLE
      } else {
        View.GONE
      }
    }
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
    mBinding.progressBar.visibility = View.INVISIBLE
  }

  override fun onLoadingPageStart(view: WebView?, url: String?) {

  }

  override fun onProgressChanged(view: WebView?, progress: Int) {

  }

  override fun onBackPressed(): Boolean {
    (requireActivity() as BookActivity).refreshData(0, 0)
    return false
  }
}