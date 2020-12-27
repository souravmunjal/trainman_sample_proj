package com.example.trainmanscreeningproject.features.home.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trainmanscreeningproject.features.home.data.entities.Gif
import com.example.trainmanscreeningproject.features.home.data.entities.GifData


@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGif(gif: Gif)

    @Query("SELECT * FROM gif_table ORDER BY id ASC")
    suspend fun readAllData():List<Gif>
}