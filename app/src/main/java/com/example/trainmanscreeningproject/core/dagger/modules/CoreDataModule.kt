package com.example.trainmanscreeningproject.core.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


@Module
class CoreDataModule {

    @Provides
    fun provideRetrofit(): Retrofit{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val url = chain.request().url.newBuilder().addQueryParameter("api_key", "DQfEIsDGTT4QJ8Eu5N4i00yLt1ZsMw3J").build()
                    val request = chain.request().newBuilder().url(url).build()

                    val response = chain.proceed(request)
                    response
                }
                .build()


        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}