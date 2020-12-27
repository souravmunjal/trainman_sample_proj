package com.example.trainmanscreeningproject.core.dagger

import android.content.Context
import com.example.trainmanscreeningproject.core.dagger.modules.CoreDataModule
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreDataModule::class])
interface  CoreDataComponent{
    @Component.Builder interface Builder {
        fun build(): CoreDataComponent

        @BindsInstance
        fun applicationContext(context: Context): Builder
    }
    fun providesRetrofit(): Retrofit
}