package com.example.sweepstakeapp.ui.Home

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sweepstakeapp.ResultData
import com.example.sweepstakeapp.SweepstakeActivity
import com.example.sweepstakeapp.adapter.DrawAdapter
import com.example.sweepstakeapp.databinding.FragmentHomeBinding
import com.example.sweepstakeapp.models.SweepstakeModel
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import com.example.sweepstakeapp.room.DrawEntity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var listView : ListView
    var list = mutableListOf<DrawEntity>()
    lateinit var db: DrawDao

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listView = binding.cekilisListView
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        db = DrawDatabase.getInstance(requireContext())?.drawDao()!!
        return root
    }

    override fun onStart() {
        list = db.getCategory(0)
        val adapter = DrawAdapter(this.requireActivity(),list)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, position, l ->
            val intent = Intent(this.requireContext(),SweepstakeActivity::class.java)
            intent.putExtra("imageURL",list.get(position).imgURL)
            intent.putExtra("title",list.get(position).name)
            intent.putExtra("link",list.get(position).link)
            startActivity(intent)
        }
        super.onStart()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}