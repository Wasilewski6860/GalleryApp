package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class GetPicturesWithTagsUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(tags: List<Tag>) : List<Picture> = picturesRepository.getPicturesWithTags(tags)


}