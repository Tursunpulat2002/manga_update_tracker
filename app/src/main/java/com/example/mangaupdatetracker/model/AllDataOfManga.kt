package com.example.mangaupdatetracker.model

/**
 * This class is used for holding data for different manga
 */
open class AllDataOfManga {
    var numberOfChapters: Int = 0
    var title: String = ""
    var imgSource: String = ""
    var newestChapter: String = ""
    var newestChapterURL: String = ""
    var lastReadChapter: String = ""
    var lastReadChapterURL: String = ""
}