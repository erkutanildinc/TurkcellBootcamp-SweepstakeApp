package com.example.sweepstakeapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "draw")
data class DrawEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "nid")
    val nid : Int?,

    val name : String,
    val imgURL : String,
    val link : String,
    val deadline : String,
    val totalPrizeCount : String,
    val condition : String,
    val category: Int,
    val clicked : Boolean

)
