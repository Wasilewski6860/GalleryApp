package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.PictureTagCrossRef
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class DeletePictureTagCrossRefUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(pictureTagCrossRef: PictureTagCrossRef) = picturesRepository.deletePictureTagCrossRef(pictureTagCrossRef)
}