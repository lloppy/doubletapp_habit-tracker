package com.example.habittracker.ui.shared.pager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.habittracker.R
import com.example.habittracker.model.PagerItem

class PagerViewModel : ViewModel() {
    var state by mutableStateOf(PagerState(pagerItems = pagerItems))
        private set

    fun onTabSelected(index: Int) {
        state = state.copy(selectedTabIndex = index)
    }

    fun getPageType(index: Int): PageType {
        return when (index) {
            0 -> PageType.ALL
            1 -> PageType.ONLY_POSITIVE
            2 -> PageType.ONLY_NEGATIVE
            else -> PageType.ALL
        }
    }

    companion object {
        private val pagerItems = listOf(
            PagerItem(
                title = R.string.all,
                unselectedIcon = Icons.AutoMirrored.Outlined.ViewList,
                selectedIcon = Icons.AutoMirrored.Filled.ViewList
            ),
            PagerItem(
                title = R.string.positive,
                unselectedIcon = Icons.Outlined.ThumbUp,
                selectedIcon = Icons.Filled.ThumbUp
            ),
            PagerItem(
                title = R.string.negative,
                unselectedIcon = Icons.Outlined.ThumbDown,
                selectedIcon = Icons.Filled.ThumbDown
            )
        )
    }
}