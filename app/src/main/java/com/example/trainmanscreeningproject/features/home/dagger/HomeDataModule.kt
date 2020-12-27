package com.example.trainmanscreeningproject.features.home.dagger

import android.content.Context
import com.example.trainmanscreeningproject.core.application.TrainmanApplication
import com.example.trainmanscreeningproject.features.home.data.entities.GifData
import com.example.trainmanscreeningproject.features.home.data.source.GifDatabase
import com.example.trainmanscreeningproject.features.home.data.source.HomeApiClient
import com.example.trainmanscreeningproject.features.home.data.source.HomeDao
import com.example.trainmanscreeningproject.features.home.data.source.HomeRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HomeDataModule {
    @Provides
    fun providesHomeApiClient(retrofit: Retrofit):HomeApiClient{
        return retrofit.create(HomeApiClient::class.java)
    }

    @Provides
    fun providesHomeDao(context: Context):HomeDao{
        return GifDatabase.getDatabase(context).homeDao()
    }

}