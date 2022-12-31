package com.example.mangaupdatetracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataAccessObject {
    // get everything in the database
    @Query("SELECT * FROM DataEntity")
    fun getAll(): List<DataEntity>
    // get everything with uid that is in dataIds
    @Query("SELECT * FROM DataEntity WHERE uid IN (:dataIds)")
    fun loadAllByIds(dataIds: IntArray): List<DataEntity>
    // get everything with matching title
    @Query("SELECT * FROM DataEntity WHERE title LIKE :title")
    fun findByName(title: String): List<DataEntity>
    // get everything by descending order
    @Query("SELECT * FROM DataEntity ORDER BY number_of_chapters DESC")
    fun findByNumberOfChaptersDESC(): List<DataEntity>

    @Insert
    fun insertAll(vararg manga: DataEntity)

    @Delete
    fun delete(manga: DataEntity)
}