package com.thk.data.di

import android.content.Context
import com.thk.data.datasource.DataStoreSource
import com.thk.data.datasource.DataStoreSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {
    @Provides
    fun provideDataStoreSource(@ApplicationContext context: Context): DataStoreSource =
        DataStoreSourceImpl(context)
}