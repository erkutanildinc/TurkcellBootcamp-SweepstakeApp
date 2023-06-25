package com.example.sweepstakeapp.room

import androidx.room.*

@Dao
interface DrawDao {

    @Insert
    fun insert(draw : DrawEntity)

    @Query("select * from draw where category =:category")
    fun getCategory(category : Int) : MutableList<DrawEntity>

    @Query("DELETE FROM draw")
    fun deleteAllData()

    @Query("SELECT EXISTS (SELECT 1 FROM draw WHERE link =:link AND category =:category)")
    fun isSaved(link : String,category: Int) : Boolean

    @Query("SELECT * FROM draw WHERE link =:link")
    fun getItem(link : String) : MutableList<DrawEntity>

    @Query("UPDATE draw SET clicked=:clicked WHERE link = :link")
    fun update(clicked: Boolean, link: String)

    @Query("SELECT * FROM draw WHERE clicked =:clicked")
    fun getFavorites(clicked : Boolean) : MutableList<DrawEntity>

}