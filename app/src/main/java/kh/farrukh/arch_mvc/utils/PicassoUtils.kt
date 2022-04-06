package kh.farrukh.arch_mvc.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 *Created by farrukh_kh on 4/6/22 6:22 PM
 *kh.farrukh.arch_mvc.utils
 **/
fun ImageView.load(imageUrl: String) {
    Picasso.get().load(imageUrl).into(this)
}