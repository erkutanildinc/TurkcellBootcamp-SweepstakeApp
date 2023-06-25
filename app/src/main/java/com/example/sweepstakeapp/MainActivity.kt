package com.example.sweepstakeapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sweepstakeapp.databinding.ActivityMainBinding
import com.example.sweepstakeapp.models.SweepstakeModel
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import com.example.sweepstakeapp.room.DrawEntity
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var list = mutableListOf<SweepstakeModel>()
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        sharedPreferences = this.getSharedPreferences("HourInfo", Context.MODE_PRIVATE)

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)+3
        val lastHour = sharedPreferences.getInt("lastHour",0)
        if(lastHour==0 || (hour-lastHour)>=3){
            addAllSweepstake()
        }
        val editor = sharedPreferences.edit()
        editor.putInt("lastHour",hour).apply()

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.arabaKazanFragment,
                R.id.bedavaKatilim,
                R.id.tatilKazanFragment,
                R.id.telefonKazanFragment,
                R.id.bedavaKatilim,
                R.id.yeniBaslayanlarFragment,
                R.id.takipEttiklerimFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun loadSweepstakeCategoryToDatabase(category : Int,list: MutableList<SweepstakeModel>,db: DrawDao){
        for (drawItem in list) {
            val drawEntity = DrawEntity(
                nid = null,
                name = drawItem.name,
                imgURL = drawItem.imgURL,
                link = drawItem.link,
                deadline = drawItem.deadline,
                totalPrizeCount = drawItem.totalPrizeCount,
                condition = drawItem.condition,
                category = category,
                clicked = false
            )
            if(!db.isSaved(drawEntity.link,drawEntity.category)){
                db.insert(drawEntity)
            }
        }
    }

    fun addAllSweepstake(){
        val db: DrawDao = DrawDatabase.getInstance(this)?.drawDao()!!
        val result = ResultData()
        list = result.cekilisler()
        loadSweepstakeCategoryToDatabase(0,list,db)
        list = result.getSectionData("/cekilisler/araba-kazan")
        loadSweepstakeCategoryToDatabase(1,list,db)
        list = result.getSectionData("/cekilisler/bedava-katilim")
        loadSweepstakeCategoryToDatabase(2,list,db)
        list = result.getSectionData("/cekilisler/tatil-kazan")
        loadSweepstakeCategoryToDatabase(3,list,db)
        list = result.getSectionData("/cekilisler/telefon-tablet-kazan")
        loadSweepstakeCategoryToDatabase(4,list,db)
        list = result.getSectionData("/cekilisler/yeni-baslayanlar")
        loadSweepstakeCategoryToDatabase(5,list,db)
    }

}