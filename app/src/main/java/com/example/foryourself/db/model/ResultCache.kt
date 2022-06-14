package com.example.foryourself.db.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull
import java.io.Serializable


@Parcelize
@Entity(tableName = "products_table")
data class ResultCache(
    @ColumnInfo(name = "createdAt")
    val createdAt: String,
    val description: String?,
    val eighthSize: String?,
    val fifthSize: String? ,
    val firstSize: String?,
    val fourthSize: String?,
    val image_first: ImageFirstCache? ,
    val image_main: ImageMainCache? ,
    val image_third: ImageThirdCache?,
    @PrimaryKey
    @NonNull
    val objectId: String,
    val price: String?,
    val secondSize: String? ,
    val seventhSize: String? ,
    val sixthSize: String? ,
    val thirdSize: String? ,
    val title: String?,
    val updatedAt: String? ,
    val youtubeTrailer: String?,
    val putID: String ?,
    val colors: String ?,
    val season: String ?,
    val colors1: String ?,
    val colors2: String ?,
    val colors3: String ?,
    @NonNull
    val tipy: String ,
    val category: String?,
    val isFavorite:Boolean

) : Parcelable


@Parcelize
data class ImageFirstCache(
    val __type: String ?,
    val name: String?  ,
    val url: String?
): Parcelable

@Parcelize
data class ImageMainCache(
    val __type: String? ,
    val name: String?,
    val url: String?
): Parcelable
@Parcelize
data class ImageThirdCache(
    val __type: String? ,
    val name: String? ,
    val url: String?
): Parcelable