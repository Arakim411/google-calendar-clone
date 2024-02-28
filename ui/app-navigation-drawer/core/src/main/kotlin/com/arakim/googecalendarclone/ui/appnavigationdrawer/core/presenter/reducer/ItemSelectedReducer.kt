package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.Action
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.ItemClickedAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerSideEffect.ItemInteractionSideEffect.CheckBoxItemChangedSideEffect
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerSideEffect.ItemInteractionSideEffect.ItemClickedSideEffect
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.ReadyState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.SideEffect
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.State
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.CheckBoxItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.AccountItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItem.ClickableItem.IconItem
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.DefaultGroup
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup.GroupWithSelectedItem
import com.arakim.googlecalendarclone.util.compose.ImmutableList
import com.arakim.googlecalendarclone.util.compose.mapToImmutable
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import javax.inject.Inject

class ItemSelectedReducer @Inject constructor() :
    StateReducerWithSideEffect<State, Action, ItemClickedAction, SideEffect>() {

    override fun State.reduce(action: ItemClickedAction): State = when (this) {
        is ReadyState -> reduceItemClickedAction(action)
        else -> logInvalidState()
    }

    private fun ReadyState.reduceItemClickedAction(action: ItemClickedAction): State {
        val updatedGroup = action.group.updatedAfterItemSelected(action.item)
        return withUpdatedGroup(updatedGroup)
    }

    private fun DrawerItemGroup.updatedAfterItemSelected(item: DrawerItem): DrawerItemGroup {
        val updatedItem = item.updatedAfterSelected()
        val updatedItems = items.withUpdatedItem(updatedItem)
        coroutineScope.yielded {
            emitItemInteractionSideEffect(updatedItem)
        }
        return when (this) {
            is DefaultGroup -> copy(items = updatedItems)
            is GroupWithSelectedItem -> copy(
                items = updatedItems as ImmutableList<ClickableItem>,
                selectedItemId = updatedItem.id
            )
        }
    }

    private fun emitItemInteractionSideEffect(updatedItem: DrawerItem) {
        val sideEffect = when (updatedItem) {
            is ClickableItem -> ItemClickedSideEffect(updatedItem)
            is CheckBoxItem -> CheckBoxItemChangedSideEffect(updatedItem)
        }
        emitSideEffect(sideEffect)
    }
}

private fun ReadyState.withUpdatedGroup(group: DrawerItemGroup): ReadyState =
    copy(groups = groups.mapToImmutable { if (it.id == group.id) group else it })

private fun ImmutableList<DrawerItem>.withUpdatedItem(item: DrawerItem) = mapToImmutable {
    if (it.id == item.id) item else it
}

private fun DrawerItem.updatedAfterSelected(): DrawerItem = when (this) {
    is AccountItem, is IconItem -> this
    is CheckBoxItem -> copy(isChecked = !isChecked)
}