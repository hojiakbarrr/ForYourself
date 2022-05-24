package com.example.foryourself.data.retrofitResponse.getResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageThird(
    val __type: String?,
    val name: String?,
    val url: String?
): Parcelable