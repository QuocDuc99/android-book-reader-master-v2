package org.bookreader.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.geometerplus.zlibrary.ui.android.R

abstract class BaseDialogFragmentBinding<B : ViewDataBinding, V : ViewModel> :
  DialogFragment {
  protected lateinit var mBinding: B
  protected lateinit var mViewModel: V
  protected abstract val classViewModel: Class<V>
  protected abstract val contentViewLayoutId: Int
  fun context(): Context? {
    return context
  }

  protected abstract fun initListeners()
  protected abstract fun initData()
  protected abstract fun subscribeToViewModel()
  private var typeScreen = 0

  constructor()
  constructor(typeScreen: Int) {
    this.typeScreen = typeScreen
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mBinding = DataBindingUtil.inflate(inflater, contentViewLayoutId, container, false)
    return mBinding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onCreateFragment()
    if (typeScreen == TYPE_FULL_SCREEN) {
      setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)
    }
  }

  override fun onStart() {
    super.onStart()
    if (typeScreen == TYPE_FULL_SCREEN) {
      val dialog = dialog
      if (dialog != null) {
        val widthAndHeight = ViewGroup.LayoutParams.MATCH_PARENT
        if (dialog.window != null) {
          dialog.window!!.setLayout(widthAndHeight, widthAndHeight)
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mViewModel = ViewModelProvider(requireActivity())[classViewModel]
    initListeners()
    initData()
    subscribeToViewModel()
  }

  fun onCreateFragment() {}

  companion object {
    var TYPE_FULL_SCREEN = 1
  }
}
