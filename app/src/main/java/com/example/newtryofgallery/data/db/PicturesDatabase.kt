package com.example.newtryofgallery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto
import com.example.newtryofgallery.data.db.dto.relations.PictureTagCrossRefDto

@Database(
    entities = [
        PictureDto :: class,
        TagDto :: class,
        PictureTagCrossRefDto::class
    ],
    version = 1
)
abstract class PicturesDatabase : RoomDatabase() {

    abstract val dao: PicturesDao

    companion object {
        @Volatile
        private var INSTANCE: PicturesDatabase? = null

        fun getInstance(context: Context) : PicturesDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PicturesDatabase::class.java,
                    "picture_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}