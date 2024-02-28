package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem
import com.arakim.googlecalendarclone.util.compose.ImmutableList

sealed interface DrawerItemGroup {
    val id: String
    val items: ImmutableList<DrawerItem>

    data class DefaultGroup(
        override val id: String,
        override val items: ImmutableList<DrawerItem>
    ) : DrawerItemGroup

    data class GroupWithSelectedItem(
        override val id: String,
        val selectedItemId: String,
        override val items: ImmutableList<ClickableItem>,
    ) : DrawerItemGroup
}