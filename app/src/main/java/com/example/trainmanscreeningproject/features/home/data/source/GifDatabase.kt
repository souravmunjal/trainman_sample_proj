package com.example.trainmanscreeningproject.features.home.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trainmanscreeningproject.features.home.data.entities.Gif
import com.example.trainmanscreeningproject.features.home.data.entities.GifData

@Database(entities = [Gif::class],version = 1,exportSchema = false)
abstract class GifDatabase :RoomDatabase() {
    abstract  fun homeDao():HomeDao
    companion object{
        private var INSTANCE:GifDatabase?=null
        fun getDatabase(context: Context):GifDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context,GifDatabase::class.java,"gif_database").build()
                INSTANCE=instance
                return instance
            }
        }
    }
}