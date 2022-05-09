package com.example.foryourself.data.types

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("__type")
    var type: String? = null,
    var name: String? = null,
    var url:String? = null
)
