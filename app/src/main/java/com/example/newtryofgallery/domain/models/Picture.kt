package com.example.newtryofgallery.domain.models

import androidx.room.PrimaryKey

data class Picture(
    var pictureId: Int = 0,
    var url: String,
    var title : String,
    var description : String

)