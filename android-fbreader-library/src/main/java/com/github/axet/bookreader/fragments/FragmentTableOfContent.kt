package com.github.axet.bookreader.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.axet.bookreader.util.Util.getHeightNavigationBar
import com.github.axet.bookreader.util.Util.getScreenHeight
import com.github.axet.bookreader.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.geometerplus.fbreader.bookmodel.TOCTree
import org.geometerplus.zlibrary.ui.android.R
import org.geometerplus.zlibrary.ui.android.databinding.FragmentTableOfContentBinding

class FragmentTableOfContent : BottomSheetDialogFragment {
  private val mTOCTree: TOCTree? = null
  private val mTOCTreeList: List<TOCTree>? = null
  private var mTOCAdapter: ReaderFragment.TOCAdapter? = null
  var mMainViewModel: MainViewModel? = null
  private var pathThumb = ""
  private var nameBook = ""
  private var page = ""

  constructor()
  constructor(
    TocAdapter: ReaderFragment.TOCAdapter, nameBook: String,
    pathThumb: String, page: String
  ) {
    mTOCAdapter = TocAdapter
    this.pathThumb = pathThumb
    this.nameBook = nameBook
    this.page = page
  }

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  private lateinit var binding: FragmentTableOfContentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(
      inflater, R.layout.fragment_table_of_content, container,
      false
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mMainViewModel = ViewModelProvider(requireActivity()).get(
      MainViewModel::class.java
    )
    Handler().postDelayed({
      setUpAdapter()
      binding.prLoading.visibility = View.INVISIBLE
    }, 300)
    observerData()
    binding.imgClose.setOnClickListener { dismissAllowingStateLoss() }
    Glide.with(requireContext())
      .asBitmap()
      .load(pathThumb)
      .error(R.drawable.ic_book_defaultl)
      .into(binding.imgThumb)
    binding.title.text = nameBook
    binding.page.text = "Trang $page"
  }

  private fun observerData() {
    mMainViewModel!!.eventCloseBookMark.observe(viewLifecycleOwner) { aBoolean ->
      if (aBoolean && isAdded && isVisible) {
        mMainViewModel!!.eventCloseBookMark.value = false
        dismissAllowingStateLoss()
      }
    }
  }

  private fun setUpAdapter() {
    binding.rcMucLuc.adapter = mTOCAdapter
  }

  override fun onStart() {
    super.onStart()
    val displayRectangle = Rect()
    val window: Window = requireActivity().window
    window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
    val minimumHeight =
      (getScreenHeight(requireActivity()) - getHeightNavigationBar(requireActivity()) - (getScreenHeight(
        requireActivity()
      ) * 0.05f)).toInt()
    dialog?.also {
      val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      bottomSheet?.layoutParams?.height = minimumHeight
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.peekHeight = minimumHeight
      view?.requestLayout()
    }
  }

  private fun getScreenHeight(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return metrics.heightPixels
  }

  private fun getHeightNavigationBar(activity: Activity): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      var insets: WindowInsets? = null
      insets = activity.windowManager.currentWindowMetrics.windowInsets
      //int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
      return insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    } else {
      val resources = activity.resources
      @SuppressLint("InternalInsetResource", "DiscouragedApi") val resourceId =
        resources.getIdentifier("navigation_bar_height", "dimen", "android")
      if (resourceId > 0) {
        return resources.getDimensionPixelSize(resourceId)
      }
    }
    return 0
  }
}
