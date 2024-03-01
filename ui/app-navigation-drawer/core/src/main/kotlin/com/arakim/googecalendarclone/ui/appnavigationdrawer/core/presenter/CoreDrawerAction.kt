package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup
import com.arakim.googlecalendarclone.util.compose.ImmutableList

sealed interface CoreDrawerAction {

    data class SetGroupsAction(val groups: ImmutableList<DrawerItemGroup>) : CoreDrawerAction
    data class ItemClickedAction(val group: DrawerItemGroup, val item: DrawerItem) : CoreDrawerAction
    data object ReleaseAction : CoreDrawerAction
}