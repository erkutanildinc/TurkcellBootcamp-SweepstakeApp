package com.example.sweepstakeapp.ui.YeniBaslayanlar

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.sweepstakeapp.ResultData
import com.example.sweepstakeapp.SweepstakeActivity
import com.example.sweepstakeapp.adapter.DrawAdapter
import com.example.sweepstakeapp.databinding.FragmentYeniBaslayanlarBinding
import com.example.sweepstakeapp.models.SweepstakeModel
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import com.example.sweepstakeapp.room.DrawEntity

class YeniBaslayanlarFragment : Fragment() {

    private var _binding: FragmentYeniBaslayanlarBinding? = null
    lateinit var listView : ListView
    var list = mutableListOf<DrawEntity>()
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = YeniBaslayanlarFragment()
    }

    private lateinit var viewModel: YeniBaslayanlarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYeniBaslayanlarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listView = binding.yeniBaslayanlarListView
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val db: DrawDao = DrawDatabase.getInstance(requireContext())?.drawDao()!!
        list = db.getCategory(5)
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
        viewModel = ViewModelProvider(this).get(YeniBaslayanlarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}