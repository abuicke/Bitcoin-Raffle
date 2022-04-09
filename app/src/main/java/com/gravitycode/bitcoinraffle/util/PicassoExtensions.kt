package com.gravitycode.bitcoinraffle.util

import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

fun Picasso.load(uri: String?, @DrawableRes backup: Int): RequestCreator =
    if (uri != null) load(uri) else load(backup)