package com.example.newtryofgallery.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto
import com.example.newtryofgallery.data.db.dto.relations.PictureTagCrossRefDto
import com.example.newtryofgallery.data.db.dto.relations.PictureWithTagsDto
import com.example.newtryofgallery.data.db.dto.relations.TagWithPicturesDto

@Dao
interface PicturesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(pictureDto: PictureDto)  : Long

    @Query("SELECT * FROM pictures WHERE pictureId =:id")
    suspend fun getPicture(id: Int): PictureDto

    @Update
    suspend fun editPicture(pictureDto: PictureDto)

    @Delete
    suspend fun deletePicture(pictureDto: PictureDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagDto: TagDto)  : Long

    @Update
    suspend fun editTag(tagDto: TagDto)

    @Delete
    suspend fun deleteTag(tagDto: TagDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureTagCrossRef(crossRef: PictureTagCrossRefDto)  : Long

    @Delete
    suspend fun deletePictureTagCrossRef(crossRef: PictureTagCrossRefDto)

    @Query("DELETE FROM cross_ref WHERE pictureId = :pictureId")
    suspend fun deleteByPictureId(pictureId: Int)

    @Transaction
    @Query("SELECT * FROM pictures WHERE pictureId = :pictureId  LIMIT 1")
    suspend fun getTagsOfPicture(pictureId : Int) : PictureWithTagsDto

    @Query("SELECT * FROM pictures")
    suspend fun getAllPictures(): List<PictureDto>

    @Query("SELECT * FROM tags")
    suspend fun getAllTags(): List<TagDto>

    @Transaction
    @Query("SELECT * FROM tags WHERE tagId = :tagId LIMIT 1")
    suspend fun getPicturesOfTag(tagId : Int) : TagWithPicturesDto

//    @Query(
//        "SELECT * FROM pictures " +
//                "INNER JOIN cross_ref ON pictures.pictureId = cross_ref.pictureId " +
//                "WHERE cross_ref.tagId IN (:tagIds) " +
//                "GROUP BY pictures.pictureId " +
//                "HAVING COUNT(DISTINCT cross_ref.tagId) = :tagCount"
//    )
//    suspend fun getPicturesWithTags(tagIds: List<Int>, tagCount: Int): List<PictureDto>

    @Query(
//        "SELECT p.* FROM pictures p WHERE p.pictureId IN ( SELECT DISTINCT cr.pictureId FROM cross_ref cr WHERE cr.tagId IN (:tagIds) GROUP BY cr.pictureId HAVING COUNT(DISTINCT cr.tagId) = :tagCount)"
//        "SELECT p.* FROM pictures p WHERE NOT EXISTS ( SELECT t.tagId  FROM tags t WHERE t.tagId IN (:tagIds) EXCEPT SELECT cr.tagId FROM cross_ref cr WHERE cr.pictureId = p.pictureId)"
                "SELECT p.* FROM pictures p WHERE ( SELECT COUNT(DISTINCT tagId) FROM cross_ref cr WHERE cr.pictureId = p.pictureId) >= :tagCount AND NOT EXISTS ( SELECT t.tagId FROM tags t WHERE t.tagId IN (:tagIds) EXCEPT SELECT cr.tagId FROM cross_ref cr WHERE cr.pictureId = p.pictureId)"

    )
    suspend fun getPicturesWithTags(tagIds: List<Int>, tagCount  : Int): List<PictureDto>
//    @Transaction
//    @Query("SELECT * FROM subject WHERE subjectName = :subjectName")
//    suspend fun getStudentsOfSubject(subjectName: String) : List<SubjectWithStudents>
//
//    @Transaction
//    @Query("SELECT * FROM student WHERE studentName = :studentName")
//    suspend fun getSubjectsOfStudent(studentName: String) : List<StudentWithSubjects>

}