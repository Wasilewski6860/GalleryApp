package com.example.newtryofgallery.data.db.impl

import com.example.newtryofgallery.data.db.PicturesDatabase
import com.example.newtryofgallery.data.db.PicturesStorage
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto
import com.example.newtryofgallery.data.db.dto.relations.PictureTagCrossRefDto
import com.example.newtryofgallery.data.db.dto.relations.PictureWithTagsDto
import com.example.newtryofgallery.data.db.dto.relations.TagWithPicturesDto

class PictureStorageImpl(picturesDatabase: PicturesDatabase): PicturesStorage {

    private val picturesDao = picturesDatabase.dao

    override suspend fun insertPicture(pictureDto: PictureDto) : Long = picturesDao.insertPicture(pictureDto)
    override suspend fun insertTag(tagDto: TagDto) : Long = picturesDao.insertTag(tagDto)
    override suspend fun getPicture(id: Int): PictureDto = picturesDao.getPicture(id)

    override suspend fun insertPictureTagCrossRef(crossRef: PictureTagCrossRefDto)  : Long = picturesDao.insertPictureTagCrossRef(crossRef)
    override suspend fun getTagsOfPicture(pictureId: Int): PictureWithTagsDto  = picturesDao.getTagsOfPicture(pictureId)

    override suspend fun getPicturesOfTag(tagId: Int): TagWithPicturesDto  = picturesDao.getPicturesOfTag(tagId)
    override suspend fun getPicturesWithTags(tags: List<TagDto>): List<PictureDto> {
        var tagIds = tags.map { it.tagId }
        var size = tagIds.size
        return picturesDao.getPicturesWithTags(tagIds, size)
    }

    override suspend fun getAllPictures(): List<PictureDto>  = picturesDao.getAllPictures()
    override suspend fun getAllTags(): List<TagDto>  = picturesDao.getAllTags()

    override suspend fun editTag(tagDto: TagDto) = picturesDao.editTag(tagDto)
    override suspend fun deleteTag(tagDto: TagDto)  = picturesDao.deleteTag(tagDto)
    override suspend fun deletePictureTagCrossRef(crossRef: PictureTagCrossRefDto)  = picturesDao.deletePictureTagCrossRef(crossRef)
    override suspend fun editPicture(pictureDto: PictureDto)  = picturesDao.editPicture(pictureDto)
    override suspend fun deletePicture(pictureDto: PictureDto)  = picturesDao.deletePicture(pictureDto)
    override suspend fun deleteByPictureId(pictureId: Int)  = picturesDao.deleteByPictureId(pictureId)

}