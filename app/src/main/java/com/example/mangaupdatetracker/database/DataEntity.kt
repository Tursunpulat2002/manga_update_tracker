package com.example.mangaupdatetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "number_of_chapters") val numberOfChapters: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image_source") val imgSource: String?,
    @ColumnInfo(name = "manga_url") val mangaURL: String?,
    @ColumnInfo(name = "newest_chapter") val newestChapter: String?,
    @ColumnInfo(name = "newest_chapter_url") val newestChapterURL: String?,
    @ColumnInfo(name = "last_read_chapter") val lastReadChapter: String?,
    @ColumnInfo(name = "last_read_chapter_url") val lastReadChapterURL: String?,
    @ColumnInfo(name = "last_newest_chapter") val lastNewestChapter: String?,
    @ColumnInfo(name = "last_newest_chapter_url") val lastNewestChapterURL: String?
)