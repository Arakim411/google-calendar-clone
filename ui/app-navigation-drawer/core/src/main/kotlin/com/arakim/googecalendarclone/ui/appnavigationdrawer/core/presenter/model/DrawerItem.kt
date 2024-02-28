package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model

sealed interface DrawerItem {

    val id: String
    val title: String

    sealed interface ClickableItem : DrawerItem {
        data class IconItem(
            override val id: String,
            override val title: String,
            val iconResId: Int,
        ) : ClickableItem

        data class AccountItem(
            override val id: String,
            override val title: String,
        ) : ClickableItem
    }

    data class CheckBoxItem(
        override val id: String,
        override val title: String,
        val isChecked: Boolean,
    ) : DrawerItem
}
