package com.example.newtryofgallery.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures")
data class PictureDto(
    @PrimaryKey(autoGenerate = true) val pictureId: Int = 0,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "description") var description : String
)