package org.geometerplus.zlibrary.bookreader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.geometerplus.zlibrary.bookreader.model.TableOfContents
import org.geometerplus.zlibrary.R
import org.geometerplus.zlibrary.databinding.ItemChaptersBinding

/**
 * Created by DucNA on 11/04/2024.
 * QIG
 */

class TableOfContentAdapters constructor(val context: Context) :
  RecyclerView.Adapter<TableOfContentAdapters.ChapterHolder>() {
  private var listItem: MutableList<TableOfContents> = mutableListOf()
  private var listFullItem: MutableList<TableOfContents> = mutableListOf()
  var actionSelectTOC: ((Int) -> Unit)? = null

  fun setDisplayDataChapter(listItem: MutableList<TableOfContents>) {
    this.listItem = listItem
    notifyDataSetChanged()
  }

  fun setFullDataChapter(listItem: MutableList<TableOfContents>) {
    this.listFullItem = listItem
  }

  fun getDataChapter(): List<TableOfContents> {
    return this.listItem.toList()
  }

  fun getFullDataChapter(): List<TableOfContents> {
    return this.listFullItem.toList()
  }

  class ChapterHolder(val binding: ItemChaptersBinding) : RecyclerView.ViewHolder(binding.root)


  override fun getItemViewType(position: Int): Int {
    return position
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
    return ChapterHolder(
      ItemChaptersBinding.inflate(
        LayoutInflater.from(
          parent.context
        ), parent, false
      )
    )
  }

  override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
    setUpChildChapter(holder.binding)
    val item = listItem[position]
    holder.binding.chapter = item

    if (item.listChildChapter.isNullOrEmpty()) {
      holder.binding.imageViewArrow.setImageResource(R.drawable.ic_books)
    } else {
      holder.binding.imageViewArrow.setImageResource(if (item.isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
      (holder.binding.rvChapter.adapter as ChildChapterAdapters).setDisplayDataChapter(item.listChildChapter!!.toMutableList())
    }

    holder.binding.clickHandler = View.OnClickListener {
      when (it.id) {
        R.id.layoutName -> {
          item.isExpanded = !item.isExpanded
          notifyItemChanged(position)
        }
      }
      item.pageIndex?.let { index ->
        actionSelectTOC?.invoke(index)
      }
    }

  }

  override fun getItemCount(): Int = listItem.size

  /**
   * Set up chapter
   *
   * @param binding
   */
  private fun setUpChildChapter(binding: ItemChaptersBinding) {
    val adapter = ChildChapterAdapters(context)
    binding.rvChapter.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    binding.rvChapter.adapter = adapter
    adapter?.actionSelectTOC = {
      actionSelectTOC?.invoke(it)
    }
  }
}