package com.github.axet.bookreader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.axet.bookreader.model.TableOfContents
import org.geometerplus.zlibrary.ui.android.databinding.ItemChildChaptersBinding


/**
 * Created by DucNA on 12/04/2024.
 * QIG
 */
class ChildChapterAdapters constructor(val context: Context) :
    RecyclerView.Adapter<ChildChapterAdapters.ChapterHolder>() {
    private var listItem: MutableList<TableOfContents> = mutableListOf()
    private var listFullItem: MutableList<TableOfContents> = mutableListOf()
    var actionSelectTOC: ((Int) -> Unit)? = null
    fun setDisplayDataChapter(listItem: MutableList<TableOfContents>) {
        this.listItem = listItem
        notifyDataSetChanged()
    }

    class ChapterHolder(val binding: ItemChildChaptersBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        return ChapterHolder(
            ItemChildChaptersBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        holder.binding.chapter = listItem[position]
        holder.binding.root.setOnClickListener {
            listItem[position].pageIndex?.let {
                actionSelectTOC?.invoke(it)
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

}