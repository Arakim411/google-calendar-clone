package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.CheckBoxItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.AccountItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.IconItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.DefaultGroup
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.GroupWithSelectedItem
import com.arakim.googlecalendarclone.ui.appnavigationdrawer.R.drawable
import com.arakim.googlecalendarclone.ui.appnavigationdrawer.R.string
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerGroupsId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerItemIds
import com.arakim.googlecalendarclone.util.compose.StringWrapper.StringResources
import com.arakim.googlecalendarclone.util.compose.StringWrapper.StringValue
import com.arakim.googlecalendarclone.util.compose.immutableListOf

internal fun getDrawerItemsGroups(userName: String) = immutableListOf(
    GroupWithSelectedItem(
        id = AppDrawerGroupsId.RangeGroupId,
        selectedItemId = AppDrawerItemIds.ScheduleId, // TODO take from shared pref
        items = rangeItems(),
    ),
    DefaultGroup(AppDrawerGroupsId.RefreshItemGroupId, refreshItem()),
    DefaultGroup(AppDrawerGroupsId.AccountItemAndChecksGroupId, accountAndCheckBoxItems(userName)),
    DefaultGroup(AppDrawerGroupsId.SettingsGroupId, settingsAndHelpItems())
)

private fun rangeItems() = immutableListOf(
    IconItem(
        iconResId = drawable.ic_schedule,
        id = AppDrawerItemIds.ScheduleId,
        title = StringResources(string.drawer_item_title_schedule),
    ),
    IconItem(
        iconResId = drawable.ic_view_day,
        id = AppDrawerItemIds.DayId,
        title = StringResources(string.drawer_item_title_day),
    ),
    IconItem(
        iconResId = drawable.ic_view_3_days,
        id = AppDrawerItemIds.ThreeDaysId,
        title = StringResources(string.drawer_item_title_3days),
    ),
    IconItem(
        iconResId = drawable.ic_view_week,
        id = AppDrawerItemIds.WeekId,
        title = StringResources(string.drawer_item_title_week),
    ),
    IconItem(
        iconResId = drawable.ic_view_month,
        id = AppDrawerItemIds.MonthId,
        title = StringResources(string.drawer_item_title_month),
    ),
)

private fun refreshItem() = immutableListOf(
    IconItem(
        iconResId = drawable.ic_refresh,
        id = AppDrawerItemIds.RefreshId,
        title = StringResources(string.drawer_item_title_refresh),
    ),
)

private fun accountAndCheckBoxItems(userName: String) = immutableListOf(
    AccountItem(
        id = AppDrawerItemIds.AccountId,
        title = StringValue(userName),
    ),
    CheckBoxItem(
        isChecked = true,
        id = AppDrawerItemIds.EventsId,
        title = StringResources(string.drawer_item_events),
    ),
    CheckBoxItem(
        isChecked = true,
        id = AppDrawerItemIds.TasksId,
        title = StringResources(string.drawer_item_title_tasks),
    ),
    CheckBoxItem(
        isChecked = true,
        id = AppDrawerItemIds.BirthdaysId,
        title = StringResources(string.drawer_item_title_birthdays),
    ),
    CheckBoxItem(
        isChecked = true,
        id = AppDrawerItemIds.HolidaysId,
        title = StringResources(string.drawer_item_title_holidays),
    ),
)

private fun settingsAndHelpItems() = immutableListOf(
    IconItem(
        iconResId = drawable.ic_settings,
        id = AppDrawerItemIds.SettingsId,
        title = StringResources(string.drawer_item_title_settings),
    ),
    IconItem(
        iconResId = drawable.ic_help,
        id = AppDrawerItemIds.HelpFeedbackId,
        title = StringResources(string.drawer_item_title_help),
    ),
)
