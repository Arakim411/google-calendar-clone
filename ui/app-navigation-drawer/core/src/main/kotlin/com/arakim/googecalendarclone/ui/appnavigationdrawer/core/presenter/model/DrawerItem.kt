package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model

import com.arakim.googlecalendarclone.util.compose.StringWrapper
import com.arakim.googlecalendarclone.util.compose.StringWrapper.StringResources

sealed interface DrawerItem {

    val id: String
    val title: StringWrapper

    sealed interface ClickableItem : DrawerItem {
        data class IconItem(
            override val id: String,
            override val title: StringResources,
            val iconResId: Int,
        ) : ClickableItem

        data class AccountItem(
            override val id: String,
            override val title: StringWrapper,
        ) : ClickableItem
    }

    data class CheckBoxItem(
        override val id: String,
        override val title: StringWrapper,
        val isChecked: Boolean,
    ) : DrawerItem
}
