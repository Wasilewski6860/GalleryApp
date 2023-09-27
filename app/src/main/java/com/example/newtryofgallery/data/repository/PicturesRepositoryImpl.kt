package com.example.newtryofgallery.data.repository

import com.example.newtryofgallery.data.db.PicturesStorage
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
import com.example.newtryofgallery.domain.repositories.PicturesRepository

class PicturesRepositoryImpl(private val picturesStorage: PicturesStorage) : PicturesRepository {


    private fun mapToData(picture: Picture): PictureDto {
        with(picture) {
            return PictureDto(
                pictureId = pictureId,
                url = url,
                title = title,
                description = description
            )
        }
    }
    private fun mapToDomain(pictureDto: PictureDto): Picture {
        with(pictureDto) {
            return Picture(
                pictureId = pictureId,
                url = url,
                title = title,
                description = description
            )
        }
    }

    private fun mapToData(tag: Tag): TagDto {
        with(tag) {
            return TagDto(
                tagId = tagId,
                name = name
            )
        }
    }
    private fun mapToDomain(tagDto: TagDto): Tag {
        with(tagDto) {
            return Tag(
                tagId = tagId,
                name = name
            )
        }
    }

    private fun mapToData(pictureWithTags: PictureWithTags): PictureWithTagsDto {
        with(pictureWithTags) {
            return PictureWithTagsDto(
                pictureDto = mapToData(picture),
                subject =   subject.map { CatDto ->
                    mapToData(CatDto)
                }
            )
        }
    }
    private fun mapToDomain(pictureWithTagsDto: PictureWithTagsDto): PictureWithTags {
        with(pictureWithTagsDto) {
            return PictureWithTags(
                picture = mapToDomain(pictureDto),
                subject =   subject.map { CatDto ->
                    mapToDomain(CatDto)
                }
            )
        }
    }

    private fun mapToData(tagWithPictures: TagWithPictures): TagWithPicturesDto {
        with(tagWithPictures) {
            return TagWithPicturesDto(
                tagDto = mapToData(tag),
                subject =   subject.map { CatDto ->
                    mapToData(CatDto)
                }
            )
        }
    }
    private fun mapToDomain(tagWithPicturesDto: TagWithPicturesDto): TagWithPictures {
        with(tagWithPicturesDto) {
            return TagWithPictures(
                tag = mapToDomain(tagDto),
                subject =   subject.map { CatDto ->
                    mapToDomain(CatDto)
                }
            )
        }
    }

    private fun mapToData(pictureTagCrossRef: PictureTagCrossRef): PictureTagCrossRefDto {
        with(pictureTagCrossRef) {
            return PictureTagCrossRefDto(
                tagId = tagId,
                pictureId = pictureId
            )
        }
    }
    private fun mapToDomain(pictureTagCrossRefDto: PictureTagCrossRefDto): PictureTagCrossRef {
        with(pictureTagCrossRefDto) {
            return PictureTagCrossRef(
                tagId = tagId,
                pictureId = pictureId
            )
        }
    }

    override suspend fun insertPicture(picture: Picture) : Long {
        val mappedCat = mapToData(picture)
        return picturesStorage.insertPicture(mappedCat)
    }

    override suspend fun insertTag(tag: Tag) {
        val mappedCat = mapToData(tag)
        picturesStorage.insertTag(mappedCat)
    }

    override suspend fun getPicture(id: Int): Picture {
        val resultFromData = picturesStorage.getPicture(id)
        return mapToDomain(resultFromData)
    }

    override suspend fun insertPictureTagCrossRef(crossRef: PictureTagCrossRef) {
        val mappedCat = mapToData(crossRef)
        picturesStorage.insertPictureTagCrossRef(mappedCat)
    }

    override suspend fun insertPictureTagCrossRef(picture: Picture, tag: Tag) {
        val mappedCat = mapToData(PictureTagCrossRef(pictureId = picture.pictureId, tagId = tag.tagId))
        picturesStorage.insertPictureTagCrossRef(mappedCat)
    }

    override suspend fun getTagsOfPicture(pictureId: Int): PictureWithTags {
        val resultFromData = picturesStorage.getTagsOfPicture(pictureId)
        return mapToDomain(resultFromData)
    }

    override suspend fun getPicturesOfTag(tagId: Int): TagWithPictures {
        val resultFromData = picturesStorage.getPicturesOfTag(tagId)
        return mapToDomain(resultFromData)
    }

    override suspend fun getPicturesWithTags(tags: List<Tag>): List<Picture> {
        var tagsDto = tags.map { mapToData(it) }
        var result = picturesStorage.getPicturesWithTags(tagsDto)
        return result.map { mapToDomain(it) }
    }

    override suspend fun getAllPictures(): List<Picture> {
        var result = picturesStorage.getAllPictures()
        return result.map { mapToDomain(it) }
    }

    override suspend fun getAllTags(): List<Tag> {
        var result = picturesStorage.getAllTags()
        return result.map { mapToDomain(it) }
    }

    override suspend fun editTag(tag: Tag) {
        val mappedCat = mapToData(tag)
        picturesStorage.editTag(mappedCat)
    }

    override suspend fun deleteTag(tag: Tag) {
        val mappedCat = mapToData(tag)
        picturesStorage.deleteTag(mappedCat)
    }

    override suspend fun deletePictureTagCrossRef(crossRef: PictureTagCrossRef) {
        val mappedCat = mapToData(crossRef)
        picturesStorage.deletePictureTagCrossRef(mappedCat)
    }

    override suspend fun editPicture(picture: Picture) {
        val mappedCat = mapToData(picture)
        picturesStorage.editPicture(mappedCat)
    }

    override suspend fun deletePicture(picture: Picture) {
        val mappedCat = mapToData(picture)
        picturesStorage.editPicture(mappedCat)
    }

    override suspend fun deleteByPictureId(pictureId: Int) {
        picturesStorage.deleteByPictureId(pictureId)
    }

}