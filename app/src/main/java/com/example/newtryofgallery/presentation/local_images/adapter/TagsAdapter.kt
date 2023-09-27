package com.example.newtryofgallery.presentation.local_images.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newtryofgallery.domain.models.Tag
import com.example.taggalleryapp.databinding.TagItemBinding

class TagsAdapter(
    private val context: Context,
    private val actionListener: TagActionListener
) : ListAdapter<Tag, TagsAdapter.TagViewHolder>(DiffCallBack), View.OnClickListener {

    class TagViewHolder(val binding: TagItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TagItemBinding.inflate(inflater, parent, false)

//        binding.root.setOnClickListener(this)
//        binding.favouriteBtnItem.setOnClickListener(this)
        binding.tagCard.setOnClickListener(this)
//        binding.itemLayout.setOnClickListener(this)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            actionListener.onClickItem(item)
        }
        with(holder.binding) {
            tagNameTagItem.text = item.name
        }
    }

    override fun onClick(v: View?) {
        var tag = v?.tag
//        var cat = v?.tag as ProductWithProductOnWarehouse
//        when (v.id) {
//            R.id.product_card ->{
//                actionListener.onClickItem(cat)
//            }
//            else -> actionListener.onClickItem(cat)
//        }
    }

    interface TagActionListener {
        fun onClickItem(tag: Tag)
    }

//    override fun submitList(list: List<Tag>?) {
//        super.submitList(list?.let { ArrayList(it) })
//    }


    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Tag>() {

            override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
                return oldItem == newItem
            }
        }
    }

}