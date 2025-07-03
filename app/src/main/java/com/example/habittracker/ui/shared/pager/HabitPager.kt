package com.example.habittracker.ui.shared.pager

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HabitPager(
    modifier: Modifier = Modifier,
    viewModel: PagerViewModel = viewModel(),
    content: @Composable (PageType) -> Unit
) {
    val state = viewModel.state
    val pagerState = rememberPagerState { state.pagerItems.size }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.targetPage) {
        viewModel.onTabSelected(pagerState.targetPage)
    }

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = state.selectedTabIndex) {
            state.pagerItems.forEachIndexed { index, item ->
                Tab(
                    text = { Text(text = stringResource(item.title)) },
                    selected = index == state.selectedTabIndex,
                    onClick = { viewModel.onTabSelected(index) },
                    icon = {
                        Icon(
                            imageVector = if (index == state.selectedTabIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = stringResource(item.title)
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { index ->
            content(viewModel.getPageType(index))
        }
    }
}
