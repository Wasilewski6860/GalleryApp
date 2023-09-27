package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.repositories.PicturesRepository


class GetAllPicturesUseCase(private val picturesRepository: PicturesRepository)  {

    suspend fun execute(): List<Picture> = picturesRepository.getAllPictures()
}