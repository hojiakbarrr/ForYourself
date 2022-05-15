package com.example.foryourself.mappers

import com.example.foryourself.data.retrofitResponse.getResponse.ImageFirst
import com.example.foryourself.data.retrofitResponse.getResponse.ImageMain
import com.example.foryourself.data.retrofitResponse.getResponse.ImageThird
import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.model.ResultCache
import com.example.foryourself.utils.Mapper

class ResultCascheToResultMapper: Mapper<ResultCache, Result>() {
    override fun map(from: ResultCache): Result = from.run {
        Result(
            createdAt = createdAt,
            description = description,
            eighthSize = eighthSize,
            fifthSize = fifthSize,
            fourthSize = fourthSize,
            firstSize = firstSize,
            image_first = ImageFirst(
                __type = image_first.__type,
                name = image_first.name,
                url = image_first.url!!
            ),
            image_main = ImageMain(
                __type = image_main.__type,
                name = image_main.name,
                url = image_main.url!!
            ),
            image_third = ImageThird(
                __type = image_third.__type,
                name = image_third.name,
                url = image_third.url!!
            ),
            objectId = objectId,
            price = price,
            secondSize = secondSize,
            seventhSize = seventhSize,
            sixthSize = sixthSize,
            thirdSize = thirdSize,
            title = title,
            updatedAt = updatedAt,
            youtubeTrailer = youtubeTrailer
        )
    }
}