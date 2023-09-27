package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.PictureTagCrossRef
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class AddPictureUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(picture: Picture) : Long = picturesRepository.insertPicture(picture)
}