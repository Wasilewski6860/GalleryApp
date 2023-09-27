package com.example.newtryofgallery.presentation.local_images.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newtryofgallery.domain.models.Picture
import com.example.taggalleryapp.databinding.ImageItemBinding

class ImagesAdapter(
    private val context: Context,
    private val actionListener: ImageActionListener
) : ListAdapter<Picture, ImagesAdapter.PictureViewHolder>(DiffCallBack), View.OnClickListener {

    class PictureViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)

//        binding.root.setOnClickListener(this)
//        binding.favouriteBtnItem.setOnClickListener(this)
        binding.imageCard.setOnClickListener(this)
//        binding.itemLayout.setOnClickListener(this)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            actionListener.onClickItem(item)
        }
        with(holder.binding) {

//            Picasso.get().load(item.url).into(cocktailImage)

            Glide.with(context)
                .load(item.url)
                .into(pictureImage)


        }
    }

    override fun onClick(v: View?) {
//        var cat = v?.tag as Cat
//        when (v.id) {
//            R.id.cocktail_card ->{
//                actionListener.onClickItem(cat)
//            }
//            R.id.favourite_btn_item -> {
//                actionListener.onClickLike(cat)
//            }
//            else -> actionListener.onClickItem(cat)
//        }
    }

    interface ImageActionListener {
        fun onClickItem(picture: Picture)
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Picture>() {

            override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
                return oldItem == newItem
            }
        }
    }

}