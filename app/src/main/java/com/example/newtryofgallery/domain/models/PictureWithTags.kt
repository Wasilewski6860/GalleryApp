package com.example.newtryofgallery.domain.models

import androidx.room.Embedded

data class PictureWithTags(
    val picture: Picture,
    val subject: List<Tag>
)