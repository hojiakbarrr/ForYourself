package com.example.foryourself.data.retrofitResponse.order.getOrder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageFirst(
    val __type: String?,
    val name: String?,
    val url: String?
): Parcelable