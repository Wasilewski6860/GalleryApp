package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.PictureTagCrossRef
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class DeletePictureUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(picture : Picture) = picturesRepository.deletePicture(picture)
}