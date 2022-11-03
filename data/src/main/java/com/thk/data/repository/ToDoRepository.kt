package com.thk.data.repository

import com.thk.data.database.ToDoDao
import com.thk.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ToDoRepository {
    fun getAllTasks(): Flow<List<ToDoTask>>
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
    private val toDoDao: ToDoDao
) : ToDoRepository {
    override fun getAllTasks() = toDoDao.getAllTasks()

    override fun getTasksSortByLowPriority() = toDoDao.sortByLowPriority()

    override fun getTasksSortByHighPriority() = toDoDao.sortByHighPriority()

    override fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)

    override suspend fun addTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)

    override suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)

    override suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)

    override suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()

    override fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)
}