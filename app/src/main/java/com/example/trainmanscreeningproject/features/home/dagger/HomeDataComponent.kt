package com.example.trainmanscreeningproject.features.home.dagger

import android.content.Context
import com.example.trainmanscreeningproject.core.dagger.CoreDataComponent
import com.example.trainmanscreeningproject.features.home.ui.HomeFragment
import com.stylabs.collabb.core.dagger.scope.ActivityScope
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(modules = [HomeDataModule::class],dependencies = [CoreDataComponent::class])
interface HomeDataComponent{
    @Component.Builder interface Builder {
        fun build() : HomeDataComponent

        fun coreDataComponent(coreDataComponent: CoreDataComponent) : Builder
        @BindsInstance
        fun activityContext(context: Context) : Builder
    }
    fun inject (homeFragment: HomeFragment)
}