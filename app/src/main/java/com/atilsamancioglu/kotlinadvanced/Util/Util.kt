package com.atilsamancioglu.kotlinadvanced.Util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.atilsamancioglu.kotlinadvanced.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context):CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 12f
        centerRadius = 45f
        start()
    }

}

//Extension function

fun ImageView.loadImage(uri: String, progressDrawable: CircularProgressDrawable) {
    //Use glide to load image
    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context).setDefaultRequestOptions(option).load(uri).into(this)
}

//Load Image Function for Binding

@BindingAdapter("android:imageUrl")
fun loadImage(view:ImageView, url: String?) {
    url?.let{
        view.loadImage(url, getProgressDrawable(view.context))
    }

}