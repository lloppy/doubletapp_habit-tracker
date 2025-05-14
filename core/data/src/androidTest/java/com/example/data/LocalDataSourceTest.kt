package com.example.data

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.TestData.testHabits
import com.example.data.local.OfflineDatabase
import com.example.data.local.dao.HabitDao
import com.example.data.local.mappers.toDomain
import com.example.data.local.mappers.toEntity
import com.example.data.remote.mapper.toHexColor
import com.example.model.Habit
import com.example.model.HabitCategory
import com.example.model.HabitPriority
import com.example.model.HabitType
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
class LocalDataSourceTest {

    private lateinit var habitDao: HabitDao
    private lateinit var habitDatabase: OfflineDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        habitDatabase = Room.inMemoryDatabaseBuilder(context, OfflineDatabase::class.java)
            .addTypeConverter(com.example.data.local.mappers.Converters())
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
    fun testMappers() {
        val habit = testHabits[0]
        val entity = habit.toEntity()
        val mappedBack = entity.toDomain()

        assertEquals(habit, mappedBack)
    }

    @Test
    @Throws(Exception::class)
    fun saveHabit_shouldAddHabitToRepository() = runBlocking {
        addSingleHabitToRepository()

        val allItems = habitDao.getAll()
        assertEquals(
            habit1,
            allItems.first()
                .first()
                .toDomain() // первая из бд -> из потока первая -> из entity в habit
        )
    }

    @Test
    @Throws(Exception::class)
    fun getAllHabits_shouldReturnAllHabitsFromRepository() = runBlocking {
        addTwoHabitsToRepository()

        val allItems = habitDao.getAll().first()
        assertEquals(habit1, allItems[0].toDomain())
        assertEquals(habit2, allItems[1].toDomain())
    }

    @Test
    @Throws(Exception::class)
    fun updateHabit_shouldUpdateHabitInRepository() = runBlocking {
        addTwoHabitsToRepository()

        habitDao.insert(testHabits[2].toEntity())
        habitDao.insert(testHabits[3].toEntity())

        val allItems = habitDao.getAll().first()
        assertEquals(testHabits[2], allItems[0].toDomain())
        assertEquals(testHabits[3], allItems[1].toDomain())
    }

    @Test
    @Throws(Exception::class)
    fun deleteHabit_shouldDeletesAllItemsFromDB() = runBlocking {
        addTwoHabitsToRepository()

        habitDao.delete(habit1.toEntity())
        habitDao.delete(habit2.toEntity())

        val allItems = habitDao.getAll().first()
        assertTrue(allItems.isEmpty())
    }


    private suspend fun addSingleHabitToRepository() {
        habitDao.insert(habit1.toEntity())
    }

    private suspend fun addTwoHabitsToRepository() {
        habitDao.insert(habit1.toEntity())
        habitDao.insert(habit2.toEntity())
    }
}

object TestData {
    val testHabits = listOf(
        Habit(
            id = 1,
            name = "Утренняя зарядка",
            description = "Зарядка для улучшения настроения",
            type = HabitType.POSITIVE,
            category = HabitCategory.SPORT,
            priority = HabitPriority.HIGH,
            frequency = "Ежедневно",
            repeatedTimes = 7,
            quantity = 3,
            colorHex = Color.RED.toHexColor()
        ),
        Habit(
            id = 2,
            name = "Чтение перед сном",
            description = "Чтение для расслабления",
            category = HabitCategory.RELAXATION,
            type = HabitType.POSITIVE,
            priority = HabitPriority.MEDIUM,
            frequency = "Раз в неделю",
            repeatedTimes = 4,
            quantity = 1,
            colorHex = Color.BLUE.toHexColor()
        ),
        Habit(
            id = 1,
            name = "Просмотр лекций",
            description = "Обучение на платформе Coursera",
            category = HabitCategory.STUDY,
            type = HabitType.POSITIVE,
            priority = HabitPriority.LOW,
            frequency = "Раз в две недели",
            repeatedTimes = 2,
            quantity = 0,
            colorHex = Color.GREEN.toHexColor()
        ),
        Habit(
            id = 2,
            name = "Планирование дня",
            description = "Планирование задач на день",
            category = HabitCategory.PRODUCTIVITY,
            type = HabitType.POSITIVE,
            priority = HabitPriority.MEDIUM,
            frequency = "Ежедневно",
            repeatedTimes = 10,
            quantity = 5,
            colorHex = Color.YELLOW.toHexColor()
        )
    )
}

