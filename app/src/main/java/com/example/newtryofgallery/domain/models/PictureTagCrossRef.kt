package com.example.newtryofgallery.domain.models

import androidx.room.Entity

data class PictureTagCrossRef(
    val pictureId: Int,
    val tagId: Int
)