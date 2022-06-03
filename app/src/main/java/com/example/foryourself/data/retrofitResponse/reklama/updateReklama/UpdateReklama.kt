package com.example.foryourself.data.retrofitResponse.reklama.updateReklama

import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Reklama1
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Reklama2
import com.example.foryourself.data.retrofitResponse.reklama.getReklama.Reklama3

data class UpdateReklama(

    val reklama1: Reklama1? = null,
    val reklama2: Reklama2? = null,
    val reklama3: Reklama3? = null,
    )
