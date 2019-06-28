package com.app.lifegames.utils

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}


fun <T> androidLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)


fun ImageView.loadImg(url: String, activity: Context) {
    Glide.with(activity)
            .load(url)
            .into(this)
}
val Context.appContext: Application
    get() = applicationContext as Application
