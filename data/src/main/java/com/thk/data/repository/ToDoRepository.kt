@file:OptIn(ExperimentalCoroutinesApi::class)

package com.thk.data.repository

import com.thk.data.database.ToDoDao
import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

interface ToDoRepository {
    fun getAllTasks(): Flow<List<ToDoTask>>
    fun getSortedTasks(): Flow<List<ToDoTask>>
    fun getTasksSortByLowPriority(): Flow<List<ToDoTask>>
    fun getTasksSortByHighPriority(): Flow<List<ToDoTask>>
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>
    suspend fun addTask(toDoTask: ToDoTask)
    suspend fun updateTask(toDoTask: ToDoTask)
    suspend fun deleteTask(toDoTask: ToDoTask)
    suspend fun deleteAllTasks()
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>
}

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao,
    private val dataStoreRepository: DataStoreRepository
) : ToDoRepository {
    override fun getAllTasks() = toDoDao.getAllTasks()

    override fun getSortedTasks(): Flow<List<ToDoTask>> =
        toDoDao
            .getAllTasks()
            .combine(dataStoreRepository.readSortState()) { list, priority ->       // TODO: sortState가 갱신될 때마다 리스트도 갱신되는지??
                when (Priority.valueOf(priority)) {
                    Priority.HIGH -> list.sortedBy { it.priority }
                    Priority.LOW -> list.sortedByDescending { it.priority }
                    else -> list
                }
            }

    override fun getTasksSortByLowPriority() = toDoDao.sortByLowPriority()

    override fun getTasksSortByHighPriority() = toDoDao.sortByHighPriority()

    override fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)

    override suspend fun addTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)

    override suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)

    override suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)

    override suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()

    override fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)
}