package com.example.mangaupdatetracker.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.mangaupdatetracker.databinding.FragmentHomeBinding
import com.example.mangaupdatetracker.model.TestURLResponse
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /* handle test url button onclick */
        binding.testUrlButtonId.setOnClickListener{
            view?.hideKeyboard()
            // Taking url from input
            val urlPage = binding.urlId.text.toString()
            // GlobalScope used to launch coroutines
            GlobalScope.launch(Dispatchers.IO) {
                // asynchronously execute testWebUrl method and wait for results
                val answer = async { testWebUrl(urlPage) }
                // used to update element attributes from the main thread
                withContext(Dispatchers.Main) {
                    if(answer.await().isValid){
                        binding.titleId.setText(answer.await().title)
                        binding.addUrlButtonId.isEnabled = true
                        binding.statusId.text = "There are ${answer.await().numberOfChapters} chapters"
                    }else{
                        binding.addUrlButtonId.isEnabled = false
                        binding.statusId.text = "Bad URL"
                        binding.titleId.setText("")
                    }
                }

            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**
     * Checks if the given URL is valid for parsing
     */
    private fun testWebUrl(url:String): TestURLResponse {
        val isURLValid = TestURLResponse()
        try {
            val doc:Document = Jsoup.connect(url).get()
            val chapters = doc.select(".panel-story-chapter-list > .row-content-chapter")
            val aTags = chapters.select("a")
            // checks if a tags exist and sets isURLValid to true or false
            if(aTags.size > 0){
                isURLValid.isValid = true
                isURLValid.numberOfChapters = aTags.size
                isURLValid.title = doc.select(".story-info-right > h1").first()?.text().toString()
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }
        return isURLValid
    }

    // Used to hide keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}