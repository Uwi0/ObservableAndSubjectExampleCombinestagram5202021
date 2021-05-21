package com.kakapo.observableandsubjectexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakapo.observableandsubjectexample.databinding.ListItemPhotoBinding

class PhotosAdapter(
    private val photos: List<Photo>,
    private val photoListener: PhotoListener
) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>(){

    inner class ViewHolder(
        view: ListItemPhotoBinding
    ): RecyclerView.ViewHolder(view.root), View.OnClickListener{
        private lateinit var photo: Photo

        private val photoImageView = view.photo

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            photoListener.photoClicked(this.photo)
        }

        fun bind(photo: Photo){
            this.photo = photo
            val bitmap =
                BitmapFactory.decodeResource(photoImageView.context.resources, photo.drawable)
            photoImageView.setImageDrawable(BitmapDrawable(photoImageView.context.resources, bitmap))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ListItemPhotoBinding =
            ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    interface PhotoListener{
        fun photoClicked(photo: Photo)
    }
}