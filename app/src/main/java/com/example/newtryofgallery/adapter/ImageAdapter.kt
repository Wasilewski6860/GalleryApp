package com.example.newtryofgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newtryofgallery.R
import com.example.newtryofgallery.data.Image
import com.example.newtryofgallery.databinding.ImageItemBinding
import com.squareup.picasso.Picasso

class ImageAdapter : ListAdapter<Image, ImageAdapter.Holder>(Comparator()) {

    private var onClickListener: OnClickListener? = null

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ImageItemBinding.bind(view)

        fun bind(image: Image)= with(binding){
            Picasso.get().load(image.uri).into(imageViewImageItem)
            var string = "Таги: "
            var i : Int = 0
            while (i<image.tags.size-1){
                string+=image.tags[i]+"; "
                i++
            }
            i=image.tags.size-1
            string+=image.tags[i];
            tagTextViewImageItem.text="Название: "+image.name
            string+="\n \n Нажмите для подробной информации..."
            tags.text = string
        }
    }

    class Comparator : DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
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
        fun onClick(position: Int, model: Image)
    }
}