package com.example.sweepstakeapp.ui.ArabaKazan

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.sweepstakeapp.R
import com.example.sweepstakeapp.ResultData
import com.example.sweepstakeapp.SweepstakeActivity
import com.example.sweepstakeapp.adapter.DrawAdapter
import com.example.sweepstakeapp.databinding.FragmentArabaKazanBinding
import com.example.sweepstakeapp.databinding.FragmentTelefonKazanBinding
import com.example.sweepstakeapp.models.SweepstakeModel
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import com.example.sweepstakeapp.room.DrawEntity

class ArabaKazanFragment : Fragment() {

    private var _binding: FragmentArabaKazanBinding? = null
    lateinit var listView : ListView
    var list = mutableListOf<DrawEntity>()
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ArabaKazanFragment()
    }

    private lateinit var viewModel: ArabaKazanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArabaKazanBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listView = binding.arabaListView
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val db: DrawDao = DrawDatabase.getInstance(requireContext())?.drawDao()!!
        list = db.getCategory(1)
        val adapter = DrawAdapter(this.requireActivity(),list)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, position, l ->
            val intent = Intent(this.requireContext(), SweepstakeActivity::class.java)
            intent.putExtra("imageURL",list.get(position).imgURL)
            intent.putExtra("title",list.get(position).name)
            intent.putExtra("link",list.get(position).link)
            startActivity(intent)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArabaKazanViewModel::class.java)
        // TODO: Use the ViewModel
    }

}