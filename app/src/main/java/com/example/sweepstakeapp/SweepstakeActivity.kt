package com.example.sweepstakeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.sweepstakeapp.databinding.ActivityMainBinding
import com.example.sweepstakeapp.databinding.ActivitySweepstakeBinding
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import com.squareup.picasso.Picasso

class SweepstakeActivity : AppCompatActivity() {

    lateinit var imgURL : String
    lateinit var title : String
    lateinit var link : String
    lateinit var imgView : ImageView
    lateinit var titleTextView : TextView
    lateinit var baslangicTextView : TextView
    lateinit var sonTextView : TextView
    lateinit var cekilisTextView : TextView
    lateinit var ilanTextView : TextView
    lateinit var minHarcamaTextView : TextView
    lateinit var toplamHediyeTutarıTextView : TextView
    lateinit var toplamHediyeSayısıTextView : TextView
    lateinit var followBtn : Button
    private lateinit var binding: ActivitySweepstakeBinding
    var tableDetailList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySweepstakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgURL = intent.getStringExtra("imageURL").toString()
        title = intent.getStringExtra("title").toString()
        link = intent.getStringExtra("link").toString()

        followBtn = binding.followBtn
        titleTextView = binding.sweepstakeDetailTitleTextView
        imgView = binding.sweepstakeDetailImageView
        baslangicTextView = binding.baslangicTextView
        sonTextView = binding.sonKatilimTextView
        cekilisTextView = binding.cekilisTextView
        ilanTextView = binding.ilanTextView
        minHarcamaTextView = binding.minHarcamaTutarTextView
        toplamHediyeSayısıTextView = binding.toplamHediyeSaySTextView
        toplamHediyeTutarıTextView = binding.toplamHediyeDegeriTextView

        Picasso.get().load(imgURL).into(imgView)
        titleTextView.text = title

        val result = ResultData()
        tableDetailList = result.getTableDetails(link)

        baslangicTextView.text = tableDetailList.get(0)
        sonTextView.text = tableDetailList.get(1)
        cekilisTextView.text = tableDetailList.get(2)
        ilanTextView.text = tableDetailList.get(3)
        minHarcamaTextView.text = tableDetailList.get(4)
        toplamHediyeTutarıTextView.text = tableDetailList.get(5)
        toplamHediyeSayısıTextView.text = tableDetailList.get(6)

        val db: DrawDao = DrawDatabase.getInstance(this)?.drawDao()!!
        val list = db.getItem(link)
        val item = list.get(0)

        if(item.clicked==false){
            followBtn.text = "TAKİP ET"
        }
        else{
            followBtn.text = "TAKİPTEN ÇIKAR"
        }

        followBtn.setOnClickListener {

            if(item.clicked==false){
                db.update(true,link)
                followBtn.text = "TAKİPTEN ÇIKAR"
            }
            else{
                db.update(false,link)
                followBtn.text = "TAKİP ET"
            }
        }
    }
}