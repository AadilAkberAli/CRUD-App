package com.example.testing.data


import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.example.creteapp.postpage

@Dao
interface PostDao {
    
    @Insert(onConflict = REPLACE)
    fun insertAll(vararg users: postpage.Post)

    @Delete
    fun delete(user: postpage.Post)

    @Query("DELETE FROM posts")
    fun deleteAll()

    @Query("SELECT * FROM posts")
    fun getAll(): List<postpage.Post>

    @Query("DELETE FROM posts WHERE id = :id")
    fun deletebyid(id: Int)

    @Query("SELECT * FROM posts WHERE user_id = :userId")
    fun getpostbyid(userId: Int) : List<postpage.Post>

    @Query("UPDATE posts SET title = :title, body = :body WHERE id = :id")
    fun update(id: Int, title: String, body: String)

}