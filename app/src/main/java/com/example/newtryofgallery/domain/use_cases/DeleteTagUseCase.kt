package com.example.newtryofgallery.domain.use_cases

import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class DeleteTagUseCase(private val picturesRepository: PicturesRepository) {

    suspend fun execute(tag : Tag) = picturesRepository.deleteTag(tag)
}