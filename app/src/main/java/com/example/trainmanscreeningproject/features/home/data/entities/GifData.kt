package com.example.trainmanscreeningproject.features.home.data.entities

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class GifData(
    val data:List<Data>?=null
) : Parcelable

@Parcelize
class Data(
    val title:String?=null,
    val images:Images?=null
) : Parcelable

@Parcelize
class Images(
    val downsized:Link?=null,
    val downsized_small:Link?=null
) : Parcelable

@Parcelize
class Link(
    val url:String?=null
):Parcelable

@Parcelize
@Entity(tableName = "gif_table")
data class Gif(
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val title:String?=null
) : Parcelable

