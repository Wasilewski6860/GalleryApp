package com.example.newtryofgallery.data.db.dto.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto

data class TagWithPicturesDto(
    @Embedded val tagDto: TagDto,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "pictureId",
        associateBy = Junction(PictureTagCrossRefDto::class)
    )
    val subject: List<PictureDto>
)