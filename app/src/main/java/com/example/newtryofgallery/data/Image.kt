package com.example.newtryofgallery.data

data class Image(
    var name : String = "",
    var uri : String = "",
    var tags : MutableList<String> = mutableListOf<String>()
)