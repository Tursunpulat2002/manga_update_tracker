package com.example.mangaupdatetracker.model

/**
 * This class is used for holding data for different manga
 */
open class AllDataOfManga {
    var isUrlValid: Boolean = false
    var numberOfChapters: Int = 0
    var title: String = ""
    var imgSource: String = ""
    var newestChapter: String = ""
    var newestChapterURL: String = ""
    var lastReadChapter: String = ""
    var lastReadChapterURL: String = ""
    override fun toString(): String {
        return "URL Valid: $isUrlValid\nNumber of Chapters: $numberOfChapters\nTitle: $title\n" +
                "Image Source: $imgSource\nNewest Chapter: $newestChapter\nNewest Chapter URL: $newestChapterURL\n" +
                "Last Read Chapter: $lastReadChapter\nLast Read Chapter URL: $lastReadChapterURL"
    }
}