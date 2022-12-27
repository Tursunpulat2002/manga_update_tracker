package com.example.mangaupdatetracker.controller

import android.util.Log
import com.example.mangaupdatetracker.model.AllDataOfManga
import com.example.mangaupdatetracker.parsers.Leviatanscans
import com.example.mangaupdatetracker.parsers.Manganato
import java.net.URI
import java.net.URISyntaxException

private const val TAG = "Controller12"

class Controller(private var url: String, private var lastChapterTitle: String) {
    //x leviatanscans, x manganato, zeroscans, realmscans, xcalibrscans, luminousscans, flamescans
    private var mangaData = AllDataOfManga()
    private var domain: String = getDomainName(url)

    /**
     * Extracting important info from specific domains
     */
    fun initialize(): AllDataOfManga {
        // Switch statement for all the different domains
        when (domain) {
            "manganato.com" -> Manganato(url, mangaData, lastChapterTitle).extractor()
            "en.leviatanscans.com" -> Leviatanscans(url, mangaData, lastChapterTitle).extractor()
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