package com.example.habittracker.ui.shared.pager

data class PagerState(
    val selectedTabIndex: Int = 0,
    val pagerItems: List<PagerItem>
)

enum class PageType {
    ALL,
    ONLY_POSITIVE,
    ONLY_NEGATIVE
}