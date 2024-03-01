package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.ReadyState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreNavigationDrawerPresenter
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.CheckBoxItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.GroupWithSelectedItem
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.domain.calendarsetup.usecases.SaveCalendarSetUpUseCase
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
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import javax.inject.Inject

class CoreDrawerStateUpdatesReducer @Inject constructor(
    private val saveCalendarSetUp: SaveCalendarSetUpUseCase,
) : StateReducerWithSideEffect<State, Action, CoreStateChangedAction, SideEffect>() {

    lateinit var coreDrawerPresenter: CoreNavigationDrawerPresenter

    override fun State.reduce(action: CoreStateChangedAction): State =
        handleCoreStateUpdatedAction(action.newState)

    private fun handleCoreStateUpdatedAction(newState: CoreDrawerState): State = when (newState) {
        is CoreDrawerState.IdleState -> IdleState
        is ReadyState -> handleCoreStateReady(newState)
    }

    private fun handleCoreStateReady(readyState: ReadyState): State = try {
        val setUp = readyState.getCalendarSetUp()
        saveCalendarSetUp(setUp)
        AppDrawerState.ReadyState(
            calendarRangeType = setUp.rangeType,
            isEventsChecked = setUp.isEventsChecked,
            isTasksChecked = setUp.isTasksChecked,
            isBirthdaysChecked = setUp.isBirthdaysChecked,
            isHolidaysChecked = setUp.isHolidaysChecked,
        )
    } catch (t: Throwable) {
        AppDrawerState.ErrorState
    }
}

private fun ReadyState.getCalendarSetUp(): CalendarSetUp = CalendarSetUp(
    rangeType = getSelectedRangeItem(),
    isEventsChecked = isItemChecked(EventsId),
    isTasksChecked = isItemChecked(TasksId),
    isBirthdaysChecked = isItemChecked(BirthdaysId),
    isHolidaysChecked = isItemChecked(HolidaysId),
)

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
