package com.example.foryourself.mappers

import com.example.foryourself.data.retrofitResponse.getResponse.Result
import com.example.foryourself.db.model.*
import com.example.foryourself.utils.Mapper

class FavoritesToCacheMapper : Mapper<Result, FavoritesCache>() {
    override fun map(from: Result): FavoritesCache = from.run {
        FavoritesCache(
            createdAt = createdAt!!,
            description = description!!,
            eighthSize = eighthSize,
            fifthSize = fifthSize,
            fourthSize = fourthSize,
            firstSize = firstSize,
            image_first = ImageFirstCacheFAV(
                __type = image_first?.__type,
                name = image_first?.name,
                url = image_first?.url
            ),
            image_main = ImageMainCacheFAV(
                __type = image_main?.__type,
                name = image_main?.name,
                url = image_main?.url
            ),
            image_third = ImageThirdCacheFAV(
                __type = image_third?.__type,
                name = image_third?.name,
                url = image_third?.url
            ),
            objectId = objectId!!,
            price = price!!,
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
            tipy = tipy!!,
            category = category
        )
    }

}