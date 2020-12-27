package com.example.trainmanscreeningproject.features.home.data.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainmanscreeningproject.features.home.data.entities.Gif
import com.example.trainmanscreeningproject.features.home.data.entities.GifData
import com.example.trainmanscreeningproject.features.home.data.source.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(val homeRepository: HomeRepository): ViewModel() {
    init {

    }
    fun getGifs():LiveData<GifData>{
        val liveData= MutableLiveData<GifData>();
        viewModelScope.launch(Dispatchers.IO) {
            val data = homeRepository.getGifs()
            withContext(Dispatchers.Main){
                liveData.value = data
            }
        }
        return liveData
    }


    fun addGifinDb(gif:Gif){
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.addGifs(gif)
        }
    }


    fun getLocalGifs():LiveData<List<Gif>>{
        val liveData = MutableLiveData<List<Gif>>();
        viewModelScope.launch (Dispatchers.IO){
            val data  = homeRepository.getLocalGifs()
            withContext(Dispatchers.Main){
                liveData.value=data
            }
        }
        return liveData
    }


}