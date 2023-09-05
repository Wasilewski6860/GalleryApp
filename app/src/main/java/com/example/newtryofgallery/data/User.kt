package com.example.newtryofgallery.data

data class User(
    var all_tags : MutableList<String> = mutableListOf<String>(),
    var all_names : MutableList<String> = mutableListOf<String>(),
    var selected_tags : MutableList<String> = mutableListOf<String>(),
    var images : MutableList<Image> = mutableListOf<Image>(),
    var name : String = "Name",
    var email : String = "test@gmail.com"
)