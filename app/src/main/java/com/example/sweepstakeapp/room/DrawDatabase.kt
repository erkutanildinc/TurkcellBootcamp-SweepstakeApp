package com.example.sweepstakeapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DrawEntity::class], version = 3)
abstract class DrawDatabase : RoomDatabase(){
    abstract fun drawDao() : DrawDao

    companion object {
        @Volatile
        private var INSTANCE: DrawDatabase? = null

        fun getInstance(context: Context): DrawDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrawDatabase::class.java,
                    "drawDatabase"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE=instance
                instance
            }
        }
    }
}

