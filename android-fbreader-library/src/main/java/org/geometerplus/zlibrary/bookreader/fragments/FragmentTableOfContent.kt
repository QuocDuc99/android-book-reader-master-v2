package org.geometerplus.zlibrary.bookreader.fragments

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.geometerplus.zlibrary.bookreader.adapter.AttachmentAdapters
import org.geometerplus.zlibrary.bookreader.adapter.TableOfContentAdapters
import org.geometerplus.zlibrary.bookreader.model.Attachments
import org.geometerplus.zlibrary.bookreader.model.TableOfContents
import org.geometerplus.zlibrary.bookreader.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.geometerplus.zlibrary.R
import org.geometerplus.zlibrary.databinding.FragmentTableOfContentBinding

class FragmentTableOfContent : BottomSheetDialogFragment {
  private var listAttachment = mutableListOf<Attachments>()
  private var listTOC = mutableListOf<TableOfContents>()
  private var mTOCAdapter: ReaderFragment.TOCAdapter? = null
  var mMainViewModel: MainViewModel? = null
  private var pathThumb = ""
  private var nameBook = ""
  private var page = ""
  private var attachmentAdapter: AttachmentAdapters? = null
  private var tableOfContentAdapter: TableOfContentAdapters? = null
  var actionSelectTOC: ((Int) -> Unit)? = null
  var actionSelectAttachments: ((Attachments) -> Unit)? = null
  constructor()

  constructor(
    list: MutableList<Attachments>, nameBook: String,
    pathThumb: String, page: String,
    listTOC: MutableList<TableOfContents>
  ) {
    this.listAttachment.clear()
    this.listAttachment.addAll(list)
    this.listTOC.clear()
    this.listTOC.addAll(listTOC)
    this.pathThumb = pathThumb
    this.nameBook = nameBook
    this.page = page
  }

  constructor(
    TocAdapter: ReaderFragment.TOCAdapter, nameBook: String,
    pathThumb: String, page: String,

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
    attachmentAdapter = AttachmentAdapters(requireContext())
    tableOfContentAdapter = TableOfContentAdapters(requireContext())
    attachmentAdapter?.actionSelectTOC = {
      actionSelectTOC?.invoke(it)
    }
    tableOfContentAdapter?.actionSelectTOC = {
      actionSelectTOC?.invoke(it)
    }
    attachmentAdapter?.actionSelectAttachments = {
      actionSelectAttachments?.invoke(it)
    }
    binding.rcMucLuc.layoutManager =
      LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    if (listAttachment.size == 1) {
      binding.rcMucLuc.adapter = tableOfContentAdapter
//      listAttachment[0].listChapter?.let {
//        tableOfContentAdapter?.setDisplayDataChapter(it as MutableList<TableOfContents>)
//      }
      if (listTOC.isNullOrEmpty()) {
        return
      }
      listTOC.filter { it.attachmentId != null }
      //tableOfContentAdapter?.setFullDataChapter(listTOC.toMutableList())
      tableOfContentAdapter?.setDisplayDataChapter(listTOC.toMutableList())
    } else {
      binding.rcMucLuc.adapter = attachmentAdapter
      attachmentAdapter?.setDataBook(listAttachment)
    }

//    if(listAttachment.size>0){
//
//    }
//    tableOfContentAdapter?.setDisplayDataChapter()
    //binding.rcMucLuc.adapter = mTOCAdapter
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
