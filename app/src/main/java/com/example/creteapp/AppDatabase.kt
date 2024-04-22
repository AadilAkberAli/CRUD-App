package com.example.creteapp
import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

import com.example.testing.data.PostDao

@Database(entities = [postpage.Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    private var instance: postpage.Post? = null

    // below line is to create
    // abstract variable for dao.
    abstract fun PostDao(): PostDao?
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "Post_Data.db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}