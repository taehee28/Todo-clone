@file:OptIn(ExperimentalCoroutinesApi::class)

package com.thk.data.repository

import com.thk.data.database.ToDoDao
import com.thk.data.datasource.DataStoreSource
import com.thk.data.models.Priority
import com.thk.data.models.ToDoTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface ToDoRepository {
    fun getAllTasks(): Flow<List<ToDoTask>>
    fun getSortedTasks(): Flow<List<ToDoTask>>
    suspend fun changeSort(priority: Priority)
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
    private val dataStoreSource: DataStoreSource
) : ToDoRepository {
    override fun getAllTasks() = toDoDao.getAllTasks()

    override fun getSortedTasks(): Flow<List<ToDoTask>> =
        toDoDao
            .getAllTasks()
            .combine(dataStoreSource.readSortState()) { list, priority ->
                when (Priority.valueOf(priority)) {
                    Priority.HIGH -> list.sortedBy { it.priority }
                    Priority.LOW -> list.sortedByDescending { it.priority }
                    else -> list
                }
            }

    override suspend fun changeSort(priority: Priority) = dataStoreSource.persistSortState(priority)

    override fun getTasksSortByLowPriority() = toDoDao.sortByLowPriority()

    override fun getTasksSortByHighPriority() = toDoDao.sortByHighPriority()

    override fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)

    override suspend fun addTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)

    override suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)

    override suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)

    override suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()

    override fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)
}