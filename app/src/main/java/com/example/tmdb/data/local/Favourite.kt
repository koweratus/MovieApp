package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Favourite(
    @PrimaryKey val mediaId: Int,
    val favourite: Boolean,
    val mediaType: String,
    val image: String,
    val title: String,
    val releaseDate: String,
    val rating: Float,
    val genres: String,
    val runTime: String,
    val overview: String,
)
