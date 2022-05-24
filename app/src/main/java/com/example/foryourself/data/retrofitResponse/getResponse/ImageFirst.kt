package com.example.foryourself.data.retrofitResponse.getResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageFirst(
    val __type: String?,
    val name: String?,
    val url: String?
): Parcelable