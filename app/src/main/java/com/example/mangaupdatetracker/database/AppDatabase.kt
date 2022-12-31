package com.example.mangaupdatetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataAccessObject
}