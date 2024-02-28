package com.arakim.googlecalendarclone.ui.navigationdrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.SetGroupsAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.AccountItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.IconItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.DefaultGroup
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.GroupWithSelectedItem
import com.arakim.googlecalendarclone.ui.appnavigationdrawer.R
import com.arakim.googlecalendarclone.ui.appnavigationdrawer.R.drawable
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerPresenter
import com.arakim.googlecalendarclone.util.compose.immutableListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppDrawerViewModel @Inject constructor(
    val appDrawerPresenter: AppDrawerPresenter
) : ViewModel() {

    init {
        appDrawerPresenter.initialize(viewModelScope)
        appDrawerPresenter.corePresenter.onAction(SetGroupsAction(getGroups()))
    }

    //TODO it's only mock, finish implementation
    private fun getGroups() = immutableListOf(
        GroupWithSelectedItem(
            id = "range",
            selectedItemId = "Schedule",
            items = rangeItems(),
        ),
        DefaultGroup("group1", refreshItem()),
        DefaultGroup("group2", accountAndCheckBoxItems()),
        DefaultGroup("group3", settingsAndHelpItems())
    )

    // TODO name
    private fun rangeItems() = immutableListOf(
        IconItem(
            iconResId = R.drawable.ic_schedule,
            id = "Schedule",
            title = "Schedule",
        ),
        IconItem(
            iconResId = drawable.ic_view_day,
            id = "Day",
            title = "Day",
        ),
        IconItem(
            iconResId = R.drawable.ic_view_3_days,
            id = "3 days",
            title = "3 days",
        ),
        IconItem(
            iconResId = R.drawable.ic_view_week,
            id = "week",
            title = "week",
        ),
        IconItem(
            iconResId = R.drawable.ic_view_month,
            id = "Month",
            title = "Month",
        ),
    )

    private fun refreshItem() = immutableListOf(
        IconItem(
            iconResId = R.drawable.ic_refresh,
            id = "Refresh",
            title = "Refresh",
        ),
    )

    private fun accountAndCheckBoxItems() = immutableListOf(
        AccountItem(
            id = "Account",
            title = "Account",
        ),
        DrawerItem.CheckBoxItem(
            isChecked = true,
            id = "Events",
            title = "Events",
        ),
        DrawerItem.CheckBoxItem(
            isChecked = true,
            id = "Tasks",
            title = "Tasks",
        ),
        DrawerItem.CheckBoxItem(
            isChecked = true,
            id = "Brithdays",
            title = "Brithdays",
        ),
        DrawerItem.CheckBoxItem(
            isChecked = true,
            id = "Holidays",
            title = "Holidays",
        ),
    )

    private fun settingsAndHelpItems() = immutableListOf(
        IconItem(
            iconResId = R.drawable.ic_settings,
            id = "settings",
            title = "Settings",
        ),
        IconItem(
            iconResId = R.drawable.ic_help,
            id = "Help & feedback",
            title = "Help & feedback",
        ),
    )
}