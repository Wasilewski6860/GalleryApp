package com.example.newtryofgallery.data.db.dto.relations

import androidx.room.Entity

@Entity(tableName = "cross_ref",primaryKeys = ["pictureId", "tagId"])
data class PictureTagCrossRefDto(
    val pictureId: Int,
    val tagId: Int
)