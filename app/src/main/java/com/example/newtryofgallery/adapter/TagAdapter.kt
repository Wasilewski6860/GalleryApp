package com.example.newtryofgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newtryofgallery.R
import com.example.newtryofgallery.data.Tag
import com.example.newtryofgallery.databinding.TagItemBinding


class TagAdapter : ListAdapter<Tag, TagAdapter.Holder>(Comparator()) {

    private var onClickListener: OnClickListener? = null

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = TagItemBinding.bind(view)

        fun bind(tag: Tag)= with(binding){
            tagTextViewTagItem.text = tag.tag
        }
    }

    class Comparator : DiffUtil.ItemCallback<Tag>(){
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.tag.equals(newItem.tag)
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tag_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, getItem(position) )
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Tag)
    }
}