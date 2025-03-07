package com.example.habittracker

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habittracker.TestData.testHabits
import com.example.habittracker.data.HabitDao
import com.example.habittracker.data.OfflineDatabase
import com.example.habittracker.model.Converters
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitPriority
import com.example.habittracker.model.HabitType
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
class HabitsRepositoryTest {

    private lateinit var habitDao: HabitDao
    private lateinit var habitDatabase: OfflineDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        habitDatabase = Room.inMemoryDatabaseBuilder(context, OfflineDatabase::class.java)
            .addTypeConverter(Converters())
            .allowMainThreadQueries()
            .build()
        habitDao = habitDatabase.habitDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        habitDatabase.close()
    }


    val habit1 = testHabits[0]
    val habit2 = testHabits[1]


    @Test
    @Throws(Exception::class)
    fun insertHabit_shouldAddHabitToRepository() = runBlocking {
        addSingleHabitToRepository()

        val allItems = habitDao.getAllHabits().first()
        assertEquals(habit1, allItems.first())
    }

    @Test
    @Throws(Exception::class)
    fun getAllHabits_shouldReturnAllHabitsFromRepository() = runBlocking {
        addTwoHabitsToRepository()

        val allItems = habitDao.getAllHabits().first()
        assertEquals(habit1, allItems[0])
        assertEquals(habit2, allItems[1])
    }

    @Test
    @Throws(Exception::class)
    fun updateHabit_shouldUpdateHabitInRepository() = runBlocking {
        addTwoHabitsToRepository()

        habitDao.update(testHabits[2])
        habitDao.update(testHabits[3])

        val allItems = habitDao.getAllHabits().first()
        assertEquals(testHabits[2], allItems[0])
        assertEquals(testHabits[3], allItems[1])
    }

    @Test
    @Throws(Exception::class)
    fun deleteHabit_shouldDeletesAllItemsFromDB() = runBlocking {
        addTwoHabitsToRepository()

        habitDao.delete(habit1)
        habitDao.delete(habit2)

        val allItems = habitDao.getAllHabits().first()
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
        Habit(
            id = 1,
            name = "Утренняя зарядка",
            description = "Зарядка для улучшения настроения",
            type = HabitType.SPORT,
            priority = HabitPriority.HIGH,
            frequency = "Ежедневно",
            repeatedTimes = 7,
            quantity = 3,
            color = Color.Red
        ),
        Habit(
            id = 2,
            name = "Чтение перед сном",
            description = "Чтение для расслабления",
            type = HabitType.RELAXATION,
            priority = HabitPriority.MEDIUM,
            frequency = "Раз в неделю",
            repeatedTimes = 4,
            quantity = 1,
            color = Color.Blue
        ),
        Habit(
            id = 1,
            name = "Просмотр лекций",
            description = "Обучение на платформе Coursera",
            type = HabitType.STUDY,
            priority = HabitPriority.LOW,
            frequency = "Раз в две недели",
            repeatedTimes = 2,
            quantity = 0,
            color = Color.Green
        ),
        Habit(
            id = 2,
            name = "Планирование дня",
            description = "Планирование задач на день",
            type = HabitType.PRODUCTIVITY,
            priority = HabitPriority.MEDIUM,
            frequency = "Ежедневно",
            repeatedTimes = 10,
            quantity = 5,
            color = Color.Yellow
        )
    )
}

