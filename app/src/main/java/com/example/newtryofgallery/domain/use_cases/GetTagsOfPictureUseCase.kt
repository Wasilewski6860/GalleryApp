package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.PictureWithTags
import com.example.newtryofgallery.domain.models.TagWithPictures
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class GetTagsOfPictureUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(pictureId : Int) : PictureWithTags = picturesRepository.getTagsOfPicture(pictureId)
}