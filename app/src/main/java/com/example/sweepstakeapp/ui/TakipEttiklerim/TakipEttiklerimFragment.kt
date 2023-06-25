package com.example.sweepstakeapp.ui.TakipEttiklerim

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
import com.example.sweepstakeapp.SweepstakeActivity
import com.example.sweepstakeapp.adapter.DrawAdapter
import com.example.sweepstakeapp.databinding.FragmentTakipEttiklerimBinding
import com.example.sweepstakeapp.databinding.FragmentTatilKazanBinding
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import com.example.sweepstakeapp.room.DrawEntity

class TakipEttiklerimFragment : Fragment() {

    private var _binding: FragmentTakipEttiklerimBinding? = null
    lateinit var listView : ListView
    var list = mutableListOf<DrawEntity>()
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TakipEttiklerimFragment()
    }

    private lateinit var viewModel: TakipEttiklerimViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTakipEttiklerimBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listView = binding.takipEttiklerimListView

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        return root
    }

    override fun onStart() {
        super.onStart()
        list.clear()
        val db: DrawDao = DrawDatabase.getInstance(requireContext())?.drawDao()!!
        val queryList = db.getFavorites(true)
        val hrefList = mutableListOf<String>()
        for(item in queryList){
            if(!hrefList.contains(item.link)){
                list.add(item)
                hrefList.add(item.link)
            }
        }
        val adapter = DrawAdapter(this.requireActivity(),list)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, position, l ->
            val intent = Intent(this.requireContext(), SweepstakeActivity::class.java)
            intent.putExtra("imageURL",list.get(position).imgURL)
            intent.putExtra("title",list.get(position).name)
            intent.putExtra("link",list.get(position).link)
            startActivity(intent)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TakipEttiklerimViewModel::class.java)
        // TODO: Use the ViewModel
    }

}