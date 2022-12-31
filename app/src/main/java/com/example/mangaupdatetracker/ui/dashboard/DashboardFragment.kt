package com.example.mangaupdatetracker.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mangaupdatetracker.database.AppDatabase
import com.example.mangaupdatetracker.databinding.FragmentDashboardBinding
import kotlinx.coroutines.*

private const val TAG = "DashboardFragment"
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val db = Room.databaseBuilder(
            root.context,
            AppDatabase::class.java, "manga-storage"
        ).build()

        binding.printId.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {

            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}