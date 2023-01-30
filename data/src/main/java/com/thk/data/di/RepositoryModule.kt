package com.thk.data.di

import com.thk.data.database.ToDoDao
import com.thk.data.datasource.DataStoreSource
import com.thk.data.repository.ToDoRepository
import com.thk.data.repository.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideToDoRepository(
        toDoDao: ToDoDao,
        dataStoreSource: DataStoreSource
    ): ToDoRepository = ToDoRepositoryImpl(toDoDao, dataStoreSource)
}