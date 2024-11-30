package org.bookreader.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.bookreader.model.Attachments
import org.geometerplus.zlibrary.R
import org.geometerplus.zlibrary.databinding.ItemAttactachmentParentsBinding


/**
 * Created by DucNA on 11/04/2024.
 * QIG
 */

class AttachmentAdapters constructor(val context: Context) :
  RecyclerView.Adapter<AttachmentAdapters.AttachmentHolder>() {
  private var listItem: MutableList<Attachments> = mutableListOf()
  var actionSelectTOC: ((Int) -> Unit)? = null
  var actionSelectAttachments: ((Attachments) -> Unit)? = null
  fun setDataBook(listItem: MutableList<Attachments>) {
    this.listItem = listItem
    notifyDataSetChanged()
  }

  class AttachmentHolder(val binding: ItemAttactachmentParentsBinding) :
    RecyclerView.ViewHolder(binding.root) {

  }


  override fun getItemViewType(position: Int): Int {
    return position
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentHolder {
    return AttachmentHolder(
      ItemAttactachmentParentsBinding.inflate(
        LayoutInflater.from(
          parent.context
        ), parent, false
      )
    )
  }

  override fun onBindViewHolder(holder: AttachmentHolder, position: Int) {
    setUpChapter(holder.binding)
    val item = listItem[position]
    holder.binding.attachment = item
//    if (position == 0) {
//      holder.binding.layoutName.background =
//        context.resources.getDrawable(R.drawable.bg_attachment_parent)
//    } else if (position == itemCount - 1) {
//
//      holder.binding.layoutName.background =
//        context.resources.getDrawable(if (!item.isExpand) R.drawable.bg_attachment_parent_bottom else R.drawable.bg_attachment_parent_middle)
//    } else {
//      holder.binding.layoutName.background =
//        context.resources.getDrawable(R.drawable.bg_attachment_parent_middle)
//    }
    if (item.listChapter.isNullOrEmpty()) {
      holder.binding.imageViewArrow.setImageResource(R.drawable.ic_books)
    } else {
      holder.binding.imageViewArrow.setImageResource(if (item.isExpand) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
      (holder.binding.rvChapter.adapter as TableOfContentAdapters).setDisplayDataChapter(item.listChapter!!.toMutableList())
    }

    holder.binding.clickHandler = View.OnClickListener {
      when (it.id) {
        R.id.layoutName -> {
          item.isExpand = !item.isExpand
          notifyItemChanged(position)
        }
      }
//      item.listChapter?.let { list ->
//        if (list.isEmpty()) {
//          actionSelectAttachments?.invoke(item)
//        }
//      }
      actionSelectAttachments?.invoke(item)
    }


  }

  override fun getItemCount(): Int = listItem.size

  /**
   * Set up chapter
   *
   * @param binding
   */
  private fun setUpChapter(binding: ItemAttactachmentParentsBinding) {
    val adapter = TableOfContentAdapters(context)
    binding.rvChapter.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    binding.rvChapter.adapter = adapter
    adapter?.actionSelectTOC = {
      actionSelectTOC?.invoke(it)
    }
  }
}