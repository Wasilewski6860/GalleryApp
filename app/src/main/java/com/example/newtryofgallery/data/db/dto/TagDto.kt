package com.example.newtryofgallery.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagDto(
    @PrimaryKey(autoGenerate = true) val tagId: Int = 0,
    @ColumnInfo(name = "name") val name: String

)