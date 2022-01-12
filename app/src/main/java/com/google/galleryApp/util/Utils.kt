package com.google.galleryApp.util

import android.util.Log


fun log(msg: String) {
    Log.d("zeeshan", "log: $msg")
}

fun log(msg: String, exception: Exception? = null) {
    Log.e("zeeshan", "log: $msg", exception)
}