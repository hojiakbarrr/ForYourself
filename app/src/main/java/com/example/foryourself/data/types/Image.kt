package com.example.foryourself.data.types

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("__type")
    var type: String?,
    var name: String?,
    var url:String?
)
