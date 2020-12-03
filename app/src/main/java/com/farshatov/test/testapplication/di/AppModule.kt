package com.farshatov.test.testapplication.di

import android.content.Context
import com.farshatov.test.testapplication.data.ContactDataSource
import com.farshatov.test.testapplication.data.local.ContactLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context, dispatcher: CoroutineDispatcher): ContactDataSource {
        return ContactLocalDataSource(context, dispatcher)
    }
}