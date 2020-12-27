package com.example.trainmanscreeningproject.features.home.data.source

import com.example.trainmanscreeningproject.features.home.data.entities.Data
import com.example.trainmanscreeningproject.features.home.data.entities.Gif
import com.example.trainmanscreeningproject.features.home.data.entities.GifData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.lang.reflect.Constructor
import javax.inject.Inject

class HomeRepository  @Inject constructor(val homeApiClient: HomeApiClient,val homeDao: HomeDao){
     suspend fun getGifs(): GifData? {
        return homeApiClient.getCurrentVersion(10).body()
    }
    suspend fun getLocalGifs():List<Gif>{
        return homeDao.readAllData()
    }
    suspend fun addGifs(gif: Gif){
       homeDao.addGif(gif)
    }
}