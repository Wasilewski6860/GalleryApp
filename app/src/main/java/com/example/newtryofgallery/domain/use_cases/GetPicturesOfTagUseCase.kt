package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.TagWithPictures
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class GetPicturesOfTagUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(tagId : Int) : TagWithPictures = picturesRepository.getPicturesOfTag(tagId)
}