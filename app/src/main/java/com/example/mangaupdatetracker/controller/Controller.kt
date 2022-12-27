package com.example.mangaupdatetracker.controller

import android.util.Log
import com.example.mangaupdatetracker.model.AllDataOfManga
import com.example.mangaupdatetracker.parsers.Manganato
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URI
import java.net.URISyntaxException

private const val TAG = "Controller12"
class Controller(url: String, private var latestChapterTitle: String) {

    //Scraping the url and saving it into doc
    private var doc: Document = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
        .get()//leviatanscans, manganato, zeroscans, realmscans, xcalibrscans, luminousscans, flamescans
    private var mangaData = AllDataOfManga()
    private var domain: String = getDomainName(url)

    /**
     * Extracting important info from specific domains
     */
    fun control(): AllDataOfManga{
        // Switch statement for all the different domains
        when(domain){
            "manganato.com" -> Manganato(doc, mangaData, latestChapterTitle).extractor()
            else -> Log.e(TAG, "Domain does not work")
        }
        return mangaData
    }

    /**
     * Used to get the domain from the url
     */
    @Throws(URISyntaxException::class)
    private fun getDomainName(url: String?): String {
        val uri = URI(url)
        val domain: String = uri.host
        return if (domain.startsWith("www.")) domain.substring(4) else domain
    }
}