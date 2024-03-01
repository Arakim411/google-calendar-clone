package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.ReadyState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreNavigationDrawerPresenter
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.CheckBoxItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.GroupWithSelectedItem
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.Action
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.CoreStateChangedAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.IdleState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.SideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.State
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerGroupsId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerGroupsId.AccountItemAndChecksGroupId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerItemIds.BirthdaysId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerItemIds.EventsId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerItemIds.HolidaysId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerItemIds.TasksId
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.CalendarRangeType
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import javax.inject.Inject

class CoreDrawerStateUpdatesReducer @Inject constructor() :
    StateReducerWithSideEffect<State, Action, CoreStateChangedAction, SideEffect>() {

    lateinit var coreDrawerPresenter: CoreNavigationDrawerPresenter

    override fun State.reduce(action: CoreStateChangedAction): State =
        handleCoreStateUpdatedAction(action.newState)

    private fun handleCoreStateUpdatedAction(newState: CoreDrawerState): State = when (newState) {
        is CoreDrawerState.IdleState -> IdleState
        is ReadyState -> handleCoreStateReady(newState)
    }

    private fun handleCoreStateReady(readyState: ReadyState): State = try {
        AppDrawerState.ReadyState(
            calendarRangeType = readyState.getSelectedRangeItem(),
            isEventsChecked = readyState.isItemChecked(EventsId),
            isTasksChecked = readyState.isItemChecked(TasksId),
            isBirthdaysChecked = readyState.isItemChecked(BirthdaysId),
            isHolidaysChecked = readyState.isItemChecked(HolidaysId),
        )
    } catch (t: Throwable) {
        AppDrawerState.ErrorState
    }
}

private fun ReadyState.getSelectedRangeItem(): CalendarRangeType {
    val rangeGroups = groups
        .filterIsInstance<GroupWithSelectedItem>()
        .find { it.id == AppDrawerGroupsId.RangeGroupId }

    return CalendarRangeType.entries.find { it.id == rangeGroups?.selectedItemId }!!
}

private fun ReadyState.isItemChecked(itemId: String): Boolean {
    val checkGroup = groups.find { it.id == AccountItemAndChecksGroupId }!!
    val checkBoxItems = checkGroup.items.filterIsInstance<CheckBoxItem>()

    return checkBoxItems.find { it.id == itemId }!!.isChecked
}
