package com.example.creteapp

import android.content.Context
import com.example.testing.data.PostDao

class PostRepository (context: Context) {

    var db: PostDao = AppDatabase.getInstance(context)?.PostDao()!!


    //Fetch All the Users
    fun getallposts(): List<postpage.Post> {
        return db.getAll()
    }

    // Insert new user
    fun insertposts(users: postpage.Post) {
        db.insertAll(users)
    }

    fun updateposts(id: Int, title: String, body: String)
    {
        db.update(id, title, body)
    }
    // Delete user
    fun deleteposts(users: postpage.Post) {
        db.delete(users)
    }

    fun deletebyid(id: Int)
    {
        db.deletebyid(id)
    }
    fun getpostbyid(id: Int) : List<postpage.Post>
    {
        return db.getpostbyid(id)
    }
    fun deleteallposts()
    {
        db.deleteAll()
    }

}