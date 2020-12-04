package com.farshatov.test.testapplication.ui.contactdetail

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.farshatov.test.testapplication.R
import com.squareup.picasso.Picasso


object DataBindingAdapters {
    @JvmStatic
    @BindingAdapter("app:imageURL")
    fun imageLoader(imageView: ImageView, url: String?) {
        url?.let {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_baseline_tag_faces_24)
                .error(R.drawable.ic_baseline_tag_faces_24)
                .into(imageView)
        }?:imageView.setImageResource(R.drawable.ic_baseline_tag_faces_24)
    }

}