package com.example.newtryofgallery.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto
import com.example.newtryofgallery.data.db.dto.relations.PictureTagCrossRefDto
import com.example.newtryofgallery.data.db.dto.relations.PictureWithTagsDto
import com.example.newtryofgallery.data.db.dto.relations.TagWithPicturesDto

interface PicturesStorage {

    suspend fun insertPicture(pictureDto: PictureDto) : Long
    suspend fun insertTag(tagDto: TagDto) : Long
    suspend fun getPicture(id: Int): PictureDto
    suspend fun insertPictureTagCrossRef(crossRef: PictureTagCrossRefDto)  : Long
    suspend fun getTagsOfPicture(pictureId : Int) : PictureWithTagsDto
    suspend fun getPicturesOfTag(tagId : Int) : TagWithPicturesDto
    suspend fun getPicturesWithTags(tags: List<TagDto>): List<PictureDto>
    suspend fun getAllPictures(): List<PictureDto>
    suspend fun getAllTags(): List<TagDto>

    suspend fun editTag(tagDto: TagDto)
    suspend fun deleteTag(tagDto: TagDto)
    suspend fun deletePictureTagCrossRef(crossRef: PictureTagCrossRefDto)
    suspend fun editPicture(pictureDto: PictureDto)
    suspend fun deletePicture(pictureDto: PictureDto)
    suspend fun deleteByPictureId(pictureId: Int)
}