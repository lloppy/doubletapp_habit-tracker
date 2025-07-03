package com.example.habittracker

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/** https://www.youtube.com/watch?v=nDCCwyS0_MQ */

class HabitTrackerTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestHabitTrackerApplication::class.java.name, context)
    }
}