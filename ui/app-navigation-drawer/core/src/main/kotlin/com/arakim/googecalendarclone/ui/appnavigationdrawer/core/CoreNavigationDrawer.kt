package com.arakim.googecalendarclone.ui.appnavigationdrawer.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.ItemClickedAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.IdleState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.ReadyState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreNavigationDrawerPresenter
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.AccountItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.IconItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.DefaultGroup
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.GroupWithSelectedItem
import com.arakim.googlecalendarclone.util.compose.ImmutableList

@Composable
fun CoreNavigationDrawer(
    corePresenter: CoreNavigationDrawerPresenter,
    drawerState: DrawerState = rememberDrawerState(initialValue = Closed),
    content: @Composable () -> Unit,
    title: @Composable () -> Unit,
) {
    val presenterState = corePresenter.stateFlow.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerTitle(title)
                StateView(
                    drawerState = presenterState.value,
                    onItemClick = { group, item -> corePresenter.onAction(ItemClickedAction(group, item)) },
                )
            }
        },
    ) {
        content()
    }
}

@Composable
private fun DrawerTitle(
    title: @Composable () -> Unit,
) {
    Spacer(modifier = Modifier.height(12.dp))
    Box(
        modifier = Modifier
            .height(40.dp)
            .padding(start = 12.dp)
    ) {
        title()
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun StateView(
    drawerState: CoreDrawerState,
    onItemClick: (group: DrawerItemGroup, item: DrawerItem) -> Unit,
) {
    Column {
        when (drawerState) {
            IdleState -> Unit
            is ReadyState -> GroupsView(groups = drawerState.groups, onItemClick)
        }
    }
}

@Composable
private fun ColumnScope.GroupsView(
    groups: ImmutableList<DrawerItemGroup>,
    onItemClick: (group: DrawerItemGroup, item: DrawerItem) -> Unit,
) {
    groups.forEachIndexed { index, group ->
        if (index > 0) HorizontalDivider()
        SingleGroupView(group = group, onItemClick)
    }
}

@Composable
private fun SingleGroupView(
    group: DrawerItemGroup,
    onItemClick: (group: DrawerItemGroup, item: DrawerItem) -> Unit,
) {
    group.items.forEachIndexed { index, item ->
        DrawerItemView(
            item = item,
            isEntireItemSelected = group.isEntireItemSelected(item),
            onItemClick = { onItemClick(group, item) },
        )
        if (index == group.items.lastIndex && group is GroupWithSelectedItem) {
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun DrawerItemView(
    item: DrawerItem,
    isEntireItemSelected: Boolean,
    onItemClick: () -> Unit,
) {
    val icon = @Composable {
        when (item) {
            is IconItem -> {
                Icon(painterResource(id = item.iconResId), null)
            }

            is AccountItem -> {
                Box(
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = AccountBackgroundColor, radius = this.size.width / 2, center = center
                            )
                        }
                        .padding(10.dp),
                ) {
                    Text(text = "A", color = Color.White, fontSize = 18.sp)
                }
            }

            is DrawerItem.CheckBoxItem -> {
                val color = remember(item.title) { getCheckBoxColor(item.title) }
                Checkbox(
                    checked = item.isChecked,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(checkedColor = color, uncheckedColor = color)
                )
            }
        }
    }

    NavigationDrawerItem(
        label = {
            Text(
                text = item.title,
                fontWeight = getFontWeight(item),
                fontSize = getFontSize(item),
            )
        }, selected = isEntireItemSelected, onClick = onItemClick, icon = icon
    )
}

@Stable
private fun DrawerItemGroup.isEntireItemSelected(item: DrawerItem): Boolean = when (this) {
    is DefaultGroup -> false
    is GroupWithSelectedItem -> selectedItemId == item.id
}

@Composable
private fun getFontWeight(item: DrawerItem): FontWeight = when (item) {
    is IconItem, is DrawerItem.CheckBoxItem -> FontWeight.Medium
    is AccountItem -> FontWeight.Light
}

@Composable
private fun getFontSize(item: DrawerItem): TextUnit = when (item) {
    is IconItem, is DrawerItem.CheckBoxItem -> 14.sp
    is AccountItem -> 12.sp
}

private fun getCheckBoxColor(title: String): Color {
    val firstLetter = title.first()
    val index = firstLetter.code % checkBoxColors.size

    return checkBoxColors[index]
}

@Suppress("MagicNumber")
private val checkBoxColors = listOf(
    Color(0xFF6CC26F),
    Color(0xFF45B6AB),
    Color(0xFF8669BB),
    Color(0xFF72B6BE),
)

@Suppress("MagicNumber")
private val AccountBackgroundColor = Color(0xFF317433)
