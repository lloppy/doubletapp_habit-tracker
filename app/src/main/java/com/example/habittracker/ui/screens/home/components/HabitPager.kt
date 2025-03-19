package com.example.habittracker.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.habittracker.ui.navigation.PagerItem

enum class HabitPageType {
    ALL,
    ONLY_POSITIVE,
    ONLY_NEGATIVE
}

@Composable
fun HabitPager(
    pagerItems: List<PagerItem>,
    content: (@Composable (HabitPageType) -> Unit),
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { pagerItems.size }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedTabIndex) { pagerState.animateScrollToPage(selectedTabIndex) }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            pagerItems.forEachIndexed { index, item ->
                Tab(
                    text = { Text(text = stringResource(item.title)) },
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = stringResource(item.title)
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) { index ->
            val pageType = when (index) {
                0 -> HabitPageType.ALL
                1 -> HabitPageType.ONLY_POSITIVE
                2 -> HabitPageType.ONLY_NEGATIVE
                else -> HabitPageType.ALL
            }
            content(pageType)
        }
    }
}
