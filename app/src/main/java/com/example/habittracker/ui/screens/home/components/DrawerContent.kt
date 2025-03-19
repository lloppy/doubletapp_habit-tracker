package com.example.habittracker.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.habittracker.R
import com.example.habittracker.ui.navigation.DrawerItem
import com.example.habittracker.ui.navigation.drawerItems

@Composable
fun DrawerContent(
    onDrawerClick: (DrawerItem) -> Unit,
    onClickCloseDrawer: () -> Unit
) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        Image(painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )
    }

    HorizontalDivider()

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    drawerItems.forEachIndexed { index, item ->
        NavigationDrawerItem(
            label = { Text(text = item.title) },
            selected = index == selectedItemIndex,
            onClick = {
                onDrawerClick(item)
                selectedItemIndex = index
                onClickCloseDrawer.invoke()
            },
            icon = {
                Icon(
                    imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.title
                )
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}
