package com.example.foryourself.mappers

import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageFirst
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageMain
import com.example.foryourself.data.retrofitResponse.order.getOrder.ImageThird
import com.example.foryourself.data.retrofitResponse.order.getOrder.Result
import com.example.foryourself.db.model.*
import com.example.foryourself.utils.Mapper

class FavoriteCascheToResultCasheMapper : Mapper<FavoritesCache, ResultCache>() {
    override fun map(from: FavoritesCache): ResultCache = from.run {
        ResultCache(
            createdAt = createdAt,
            description = description,
            eighthSize = eighthSize,
            fifthSize = fifthSize,
            fourthSize = fourthSize,
            firstSize = firstSize,
            image_first = ImageFirstCache(
                __type = image_first?.__type,
                name = image_first?.name,
                url = image_first?.url
            ),
            image_main = ImageMainCache(
                __type = image_main?.__type,
                name = image_main?.name,
                url = image_main?.url
            ),
            image_third = ImageThirdCache(
                __type = image_third?.__type,
                name = image_third?.name,
                url = image_third?.url
            ),
            objectId = objectId,
            price = price,
            secondSize = secondSize,
            seventhSize = seventhSize,
            sixthSize = sixthSize,
            thirdSize = thirdSize,
            title = title,
            updatedAt = updatedAt,
            youtubeTrailer = youtubeTrailer,
            putID = putID,
            colors = colors,
            season = season,
            colors1 = colors1,
            colors2 = colors2,
            colors3 = colors3,
            tipy = tipy,
            category = category,
            isFavorite = isFavorite
        )
    }
}