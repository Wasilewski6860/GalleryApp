package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.PictureTagCrossRef
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.models.TagWithPictures
import com.example.newtryofgallery.domain.repositories.PicturesRepository


class AddPictureTagCrossRefUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(crossRef: PictureTagCrossRef) = picturesRepository.insertPictureTagCrossRef(crossRef)
    suspend fun execute(picture: Picture, tag: Tag) = picturesRepository.insertPictureTagCrossRef(picture, tag)
}