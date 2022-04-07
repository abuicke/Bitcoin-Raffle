package com.gravitycode.bitcoinraffle.util

import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso

fun Picasso.load(uri: String?, @DrawableRes backup: Int) =
    if (uri != null) load(uri) else load(backup)