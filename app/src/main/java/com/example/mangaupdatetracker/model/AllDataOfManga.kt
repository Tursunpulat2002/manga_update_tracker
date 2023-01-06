package com.example.mangaupdatetracker.model

import androidx.room.PrimaryKey

/**
 * This class is used for holding data for different manga
 */
open class AllDataOfManga {
    var isUrlValid: Boolean = false
    var mangaURL: String = ""
    var numberOfChapters: Int = 0
    var title: String = ""
    var imgSource: String = ""
    var newestChapter: String = ""
    var newestChapterURL: String = ""
    var lastReadChapter: String = ""
    var lastReadChapterURL: String = ""
    var lastNewestChapter: String = ""
    var lastNewestChapterURL: String = ""
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    override fun toString(): String {
        return "URL Valid: $isUrlValid\nNumber of Chapters: $numberOfChapters\nTitle: $title\n" +
                "Image Source: $imgSource\nNewest Chapter: $newestChapter\nNewest Chapter URL: $newestChapterURL\n" +
                "Last Read Chapter: $lastReadChapter\nLast Read Chapter URL: $lastReadChapterURL"
    }

    /**
     * checks if there is any new chapters
     */
    fun isThereNewChapter(): Boolean{
        // if last read chapter is not the same as the newest chapter then return true
        return lastReadChapter != newestChapter
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = isUrlValid.hashCode()
        result = 31 * result + numberOfChapters
        result = 31 * result + title.hashCode()
        result = 31 * result + imgSource.hashCode()
        result = 31 * result + newestChapter.hashCode()
        result = 31 * result + newestChapterURL.hashCode()
        result = 31 * result + lastReadChapter.hashCode()
        result = 31 * result + lastReadChapterURL.hashCode()
        result = 31 * result + lastNewestChapter.hashCode()
        result = 31 * result + lastNewestChapterURL.hashCode()
        return result
    }
}