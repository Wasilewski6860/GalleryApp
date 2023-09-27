package com.example.newtryofgallery.data.db.dto.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto

data class PictureWithTagsDto(
    @Embedded val pictureDto: PictureDto,
    @Relation(
        parentColumn = "pictureId",
        entityColumn = "tagId",
        associateBy = Junction(PictureTagCrossRefDto::class)
    )
    val subject: List<TagDto>
)