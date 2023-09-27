package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class DeleteCrossRefsByPictureIdUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(id : Int) = picturesRepository.deleteByPictureId(id)
}