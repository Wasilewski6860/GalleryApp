package com.example.newtryofgallery.domain.repositories

import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto
import com.example.newtryofgallery.data.db.dto.relations.PictureTagCrossRefDto
import com.example.newtryofgallery.data.db.dto.relations.PictureWithTagsDto
import com.example.newtryofgallery.data.db.dto.relations.TagWithPicturesDto
import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.PictureTagCrossRef
import com.example.newtryofgallery.domain.models.PictureWithTags
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.models.TagWithPictures

interface PicturesRepository {

    suspend fun insertPicture(picture: Picture) : Long
    suspend fun insertTag(tag: Tag)
    suspend fun getPicture(id : Int) : Picture
    suspend fun insertPictureTagCrossRef(crossRef: PictureTagCrossRef)
    suspend fun insertPictureTagCrossRef(picture: Picture, tag: Tag)
    suspend fun getTagsOfPicture(pictureId : Int) : PictureWithTags
    suspend fun getPicturesOfTag(tagId : Int) : TagWithPictures
    suspend fun getPicturesWithTags(tags : List<Tag>): List<Picture>

    suspend fun getAllPictures(): List<Picture>
    suspend fun getAllTags(): List<Tag>

    suspend fun editTag(tag: Tag)
    suspend fun deleteTag(tag: Tag)
    suspend fun deletePictureTagCrossRef(crossRef: PictureTagCrossRef)
    suspend fun editPicture(picture : Picture)
    suspend fun deletePicture(picture : Picture)
    suspend fun deleteByPictureId(pictureId: Int)
}