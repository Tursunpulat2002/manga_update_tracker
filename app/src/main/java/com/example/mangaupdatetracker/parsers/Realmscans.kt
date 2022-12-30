package com.example.mangaupdatetracker.parsers

import android.util.Log
import com.example.mangaupdatetracker.model.AllDataOfManga
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

private const val TAG = "Realmscans"
class Realmscans(url: String, private var mangaData: AllDataOfManga, private var lastChapter: String) {
    //Scraping the url and saving it into doc
    private var doc: Document = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
        .get()

    /**
     * Used to extract info from just Realm Scans
     */
    fun extractor(){
        /**
         * Used to extract info from just Realm scans
         */
        try {
            val chapters: Elements = doc.select("div.eph-num")
            val aTags = chapters.select("a[href*=realmscans.com]")
            // checks if a tags exist and sets isURLValid to true or false
            if(aTags.size > 0){
                mangaData.numberOfChapters = aTags.size
                mangaData.title = doc.select("h1.entry-title").text().toString()
                mangaData.imgSource = doc.select("div.thumb > img").attr("src").toString()
                mangaData.newestChapter = aTags.select("a[href*=chapter]").first()?.select("span.chapternum")
                    ?.text()
                    .toString()
                mangaData.newestChapterURL = aTags.first()?.attr("href").toString()
                mangaData.lastReadChapter = aTags.select("a[href*=$lastChapter]").last()?.select("span.chapternum")
                    ?.text()
                    .toString()
                mangaData.lastReadChapterURL = aTags.select("a[href*=$lastChapter]").last()?.attr("href")
                    .toString()
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }
    }
}