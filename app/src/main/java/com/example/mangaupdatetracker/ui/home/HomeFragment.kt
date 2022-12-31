package com.example.mangaupdatetracker.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.mangaupdatetracker.controller.Controller
import com.example.mangaupdatetracker.database.AppDatabase
import com.example.mangaupdatetracker.database.DataEntity
import com.example.mangaupdatetracker.databinding.FragmentHomeBinding
import com.example.mangaupdatetracker.model.AllDataOfManga
import kotlinx.coroutines.*

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var inputData: AllDataOfManga

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

        val db = Room.databaseBuilder(
            root.context,
            AppDatabase::class.java, "manga-storage"
        ).build()

        /* handle test url button onclick */
        binding.testUrlButtonId.setOnClickListener {
            view?.hideKeyboard()
            // Taking url from input
            val urlPage = binding.urlId.text.toString()
            // GlobalScope used to launch coroutines
            GlobalScope.launch(Dispatchers.IO) {
                // asynchronously execute testWebUrl method and wait for results
                val answer = async { testWebUrl(urlPage) }
                // used to update element attributes from the main thread
                withContext(Dispatchers.Main) {
                    if (answer.await().isUrlValid) {
                        binding.addUrlButtonId.isEnabled = true
                        binding.statusId.text = buildString {
                            append("There are ")
                            append(answer.await().numberOfChapters)
                            append(" chapters")
                        }
                        inputData = answer.await()
                    } else {
                        binding.addUrlButtonId.isEnabled = false
                        binding.statusId.text = buildString {
                            append("Bad URL")
                        }
                    }
                }

            }
        }

        binding.addUrlButtonId.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                val dataEntity = async { convertData(inputData) }
                db.dataDao().insertAll(dataEntity.await())
            }
        }
        return root
    }

    /**
     * Checks if the given URL is valid for parsing
     */
    private fun testWebUrl(url: String): AllDataOfManga {
        val controller = Controller(url, binding.lastReadChapterTitleId.text.toString())
        val mangaData = controller.initialize()

        if (mangaData.numberOfChapters > 0) {
            mangaData.isUrlValid = true
        }

        return mangaData
    }

    // Used to hide keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // Converts manga data to data entity
    private fun convertData(inputData:AllDataOfManga): DataEntity{
        return DataEntity(
            0,
            inputData.numberOfChapters,
            inputData.title,
            inputData.imgSource,
            inputData.newestChapter,
            inputData.newestChapterURL,
            inputData.lastReadChapter,
            inputData.lastReadChapterURL
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}