package com.example.mangaupdatetracker.parsers

import android.util.Log
import com.example.mangaupdatetracker.model.AllDataOfManga
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

private const val TAG = "Manganato"
class Manganato(private var doc: Document, private var mangaData: AllDataOfManga, private var latestChapter: String) {

    /**
     * Used to extract info from just Manganato
     */
    fun extractor(){
        try {
            val chapters: Elements = doc.select(".panel-story-chapter-list > .row-content-chapter")
            val aTags = chapters.select("a")
            // checks if a tags exist and sets isURLValid to true or false
            if(aTags.size > 0){
                mangaData.numberOfChapters = aTags.size
                mangaData.title = doc.select(".story-info-right > h1").first()?.text().toString()
                mangaData.imgSource = doc.select(".story-info-left > .info-image > .img-loading")
                    .first()?.attr("src").toString()
                mangaData.newestChapter = aTags.first()?.attr("title").toString()
                mangaData.newestChapterURL = aTags.first()?.attr("href").toString()
                mangaData.lastReadChapter = aTags.select("a[title*=$latestChapter]").attr("title").toString()
                mangaData.lastReadChapterURL = aTags.select("a[title*=$latestChapter]").attr("href").toString()
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }
    }
}