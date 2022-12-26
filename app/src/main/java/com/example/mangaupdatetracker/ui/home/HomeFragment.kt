package com.example.mangaupdatetracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mangaupdatetracker.databinding.FragmentHomeBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var TAG = "HomeFragment"
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.testUrlButtonId.setOnClickListener{
            val urlPage = binding.urlId.text.toString()
            GlobalScope.launch{
                val answer = async { getWeb(urlPage) }
                //.d(TAG, "HTML: ${answer.await()}")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    suspend fun getWeb(url:String): String {
        var htmlOutPut = ""
        try {
            val doc:Document = Jsoup.connect(url).get()
            val chapters = doc.select(".panel-story-chapter-list > .row-content-chapter")
            val aTags = chapters.select("a")
            val img = doc.select(".story-info-left > .info-image > .img-loading").first()?.attr("src")
            Log.d(TAG, "IMAGE $img")

            for (a in aTags){
                val links: String = a.attr("href")
                val titles: String = a.attr("title")
                Log.d(TAG, titles)
                Log.d(TAG, links)
            }

        }catch (e:Exception){
            Log.e("shamsi", e.toString())
        }
        return htmlOutPut
    }
}