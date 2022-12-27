package com.example.mangaupdatetracker.parsers

import android.util.Log
import com.example.mangaupdatetracker.model.AllDataOfManga
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

private const val TAG = "Leviatanscans"
class Leviatanscans(private var url: String, private var mangaData: AllDataOfManga, private var lastChapter: String) {
    //Scraping the url and saving it into doc
    private var doc: Document = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
        .get()
    private lateinit var doc2: Document

    /**
     * Used to extract info from just Leviatan Scans
     */
    fun extractor(){
        url += "ajax/chapters/"
        //Scraping the url and saving it into doc
        doc2 = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
            .post()

        /**
         * Used to extract info from just Leviatan scans
         */
        try {
            val chapters: Elements = doc2.select(".wp-manga-chapter")
            val aTags = chapters.select("a")
            // checks if a tags exist and sets isURLValid to true or false
            if(aTags.size > 0){
                mangaData.numberOfChapters = aTags.size
                mangaData.title = doc
                    .select(".site-content > .post-117 > #manga-header > .container > .row > .col-12 > #manga-title > h1").first()?.text().toString()
                mangaData.imgSource = doc.select(".profile-manga > .tab-summary > .summary_image > a > .img-responsive").attr("src").toString()
                mangaData.newestChapter = aTags.select("a").first()?.text().toString()
                mangaData.newestChapterURL = aTags.first()?.attr("href").toString()
                mangaData.lastReadChapter = aTags.select("a[href*=$lastChapter]").select("a").text().toString()
                mangaData.lastReadChapterURL = aTags.select("a[href*=$lastChapter]").attr("href").toString()
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }
    }
}