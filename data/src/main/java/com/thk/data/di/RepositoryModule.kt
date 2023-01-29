package com.thk.data.di

import android.content.Context
import com.thk.data.database.ToDoDao
import com.thk.data.repository.DataStoreRepository
import com.thk.data.repository.DataStoreRepositoryImpl
import com.thk.data.repository.ToDoRepository
import com.thk.data.repository.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideToDoRepository(
        toDoDao: ToDoDao,
        dataStoreRepository: DataStoreRepository
    ): ToDoRepository =
        ToDoRepositoryImpl(toDoDao, dataStoreRepository)

    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository = DataStoreRepositoryImpl(context)
}