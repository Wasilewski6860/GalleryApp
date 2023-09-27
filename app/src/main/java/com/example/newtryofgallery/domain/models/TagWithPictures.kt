package com.example.newtryofgallery.domain.models

data class TagWithPictures(
    var tag: Tag,
    val subject: List<Picture>
)