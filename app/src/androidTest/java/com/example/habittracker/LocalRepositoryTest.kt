package com.example.habittracker

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habittracker.TestData.testHabits
import com.example.habittracker.data.OfflineDatabase
import com.example.data.local.dao.HabitDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalRepositoryTest {

    private lateinit var habitDao: HabitDao
    private lateinit var habitDatabase: OfflineDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        habitDatabase = Room.inMemoryDatabaseBuilder(context, OfflineDatabase::class.java)
            .addTypeConverter(com.example.model.domain.Converters())
            .allowMainThreadQueries()
            .build()
        habitDao = habitDatabase.habitDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        habitDatabase.close()
    }


    private val habit1 = testHabits[0]
    private val habit2 = testHabits[1]


    @Test
    @Throws(Exception::class)
    fun saveHabit_shouldAddHabitToRepository() = runBlocking {
        addSingleHabitToRepository()

        val allItems = habitDao.getAll().first()
        assertEquals(habit1, allItems.first())
    }

    @Test
    @Throws(Exception::class)
    fun getAllHabits_shouldReturnAllHabitsFromRepository() = runBlocking {
        addTwoHabitsToRepository()

        val allItems = habitDao.getAll().first()
        assertEquals(habit1, allItems[0])
        assertEquals(habit2, allItems[1])
    }

    @Test
    @Throws(Exception::class)
    fun updateHabit_shouldUpdateHabitInRepository() = runBlocking {
        addTwoHabitsToRepository()

        habitDao.update(testHabits[2])
        habitDao.update(testHabits[3])

        val allItems = habitDao.getAll().first()
        assertEquals(testHabits[2], allItems[0])
        assertEquals(testHabits[3], allItems[1])
    }

    @Test
    @Throws(Exception::class)
    fun deleteHabit_shouldDeletesAllItemsFromDB() = runBlocking {
        addTwoHabitsToRepository()

        habitDao.delete(habit1)
        habitDao.delete(habit2)

        val allItems = habitDao.getAll().first()
        assertTrue(allItems.isEmpty())
    }


    private suspend fun addSingleHabitToRepository() {
        habitDao.insert(habit1)
    }

    private suspend fun addTwoHabitsToRepository() {
        habitDao.insert(habit1)
        habitDao.insert(habit2)
    }
}

object TestData {

    val testHabits = listOf(
        com.example.model.domain.Habit(
            id = 1,
            name = "Утренняя зарядка",
            description = "Зарядка для улучшения настроения",
            category = com.example.model.domain.HabitCategory.SPORT,
            type = com.example.model.domain.HabitType.POSITIVE,
            priority = com.example.model.domain.HabitPriority.HIGH,
            frequency = "Ежедневно",
            repeatedTimes = 7,
            quantity = 3,
            color = Color.Red
        ),
        com.example.model.domain.Habit(
            id = 2,
            name = "Чтение перед сном",
            description = "Чтение для расслабления",
            category = com.example.model.domain.HabitCategory.RELAXATION,
            type = com.example.model.domain.HabitType.POSITIVE,
            priority = com.example.model.domain.HabitPriority.MEDIUM,
            frequency = "Раз в неделю",
            repeatedTimes = 4,
            quantity = 1,
            color = Color.Blue
        ),
        com.example.model.domain.Habit(
            id = 1,
            name = "Просмотр лекций",
            description = "Обучение на платформе Coursera",
            category = com.example.model.domain.HabitCategory.STUDY,
            type = com.example.model.domain.HabitType.POSITIVE,
            priority = com.example.model.domain.HabitPriority.LOW,
            frequency = "Раз в две недели",
            repeatedTimes = 2,
            quantity = 0,
            color = Color.Green
        ),
        com.example.model.domain.Habit(
            id = 2,
            name = "Планирование дня",
            description = "Планирование задач на день",
            category = com.example.model.domain.HabitCategory.PRODUCTIVITY,
            type = com.example.model.domain.HabitType.POSITIVE,
            priority = com.example.model.domain.HabitPriority.MEDIUM,
            frequency = "Ежедневно",
            repeatedTimes = 10,
            quantity = 5,
            color = Color.Yellow
        )
    )
}

