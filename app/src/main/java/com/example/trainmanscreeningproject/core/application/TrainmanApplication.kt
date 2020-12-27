package com.example.trainmanscreeningproject.core.application

import android.app.Application
import android.content.Context
import com.example.trainmanscreeningproject.core.dagger.CoreDataComponent
import com.example.trainmanscreeningproject.core.dagger.DaggerCoreDataComponent

class TrainmanApplication :Application(){
    private val coreDataComponent: CoreDataComponent by lazy {
        DaggerCoreDataComponent
            .builder()
            .applicationContext(this)
            .build()
    }

    companion object {
        @JvmStatic
        fun coreDataComponent(context: Context) =
            (context.applicationContext as TrainmanApplication).coreDataComponent

        @get:Synchronized
        lateinit var instance: TrainmanApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
    }

}