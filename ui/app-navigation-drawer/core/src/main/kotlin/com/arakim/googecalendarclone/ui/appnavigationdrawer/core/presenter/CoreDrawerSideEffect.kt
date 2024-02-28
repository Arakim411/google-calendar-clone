package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.CheckBoxItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem

sealed interface CoreDrawerSideEffect {

    sealed interface ItemInteractionSideEffect : CoreDrawerSideEffect {
        data class ItemClickedSideEffect(val item: ClickableItem) : ItemInteractionSideEffect
        data class CheckBoxItemChangedSideEffect(val item: CheckBoxItem) : ItemInteractionSideEffect
    }
}