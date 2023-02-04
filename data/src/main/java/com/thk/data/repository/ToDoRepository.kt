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
    /**
     * 저장된 sort에 맞춰 Task 리스트를 반환
     */
    fun getSortedTasks(): Flow<List<ToDoTask>>

    /**
     * DataStore에 저장된 sort 변경
     */
    suspend fun changeSort(priority: Priority)

    /**
     * 전달받은 id를 가지는 Task 한개 반환
     */
    fun getSelectedTask(taskId: Int): Flow<ToDoTask?>

    /**
     * 새로운 Task 추가
     */
    suspend fun addTask(toDoTask: ToDoTask)

    /**
     * 기존의 Task 수정
     */
    suspend fun updateTask(toDoTask: ToDoTask)

    /**
     * 하나의 Task 삭제
     */
    suspend fun deleteTask(toDoTask: ToDoTask)

    /**
     * 전체 Task 삭제
     */
    suspend fun deleteAllTasks()

    /**
     * 전달받은 string이 포함된 Task들을 DB에서 검색
     */
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Deprecated("더이상 사용하지 않음")
    fun getAllTasks(): Flow<List<ToDoTask>>
    @Deprecated("더이상 사용하지 않음")
    fun getTasksSortByLowPriority(): Flow<List<ToDoTask>>
    @Deprecated("더이상 사용하지 않음")
    fun getTasksSortByHighPriority(): Flow<List<ToDoTask>>
}

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao,
    private val dataStoreSource: DataStoreSource
) : ToDoRepository {
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

    override fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)

    override suspend fun addTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)

    override suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)

    override suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)

    override suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()

    override fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)

    override fun getAllTasks() = toDoDao.getAllTasks()

    override fun getTasksSortByHighPriority() = toDoDao.sortByHighPriority()

    override fun getTasksSortByLowPriority() = toDoDao.sortByLowPriority()
}