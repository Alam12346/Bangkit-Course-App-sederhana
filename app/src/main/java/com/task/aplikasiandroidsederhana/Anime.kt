package com.task.aplikasiandroidsederhana

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime(
    val name: String,
    val sortdesc: String,
    val personalDescription : String,
    val description: String,
    val photo: Int
) : Parcelable