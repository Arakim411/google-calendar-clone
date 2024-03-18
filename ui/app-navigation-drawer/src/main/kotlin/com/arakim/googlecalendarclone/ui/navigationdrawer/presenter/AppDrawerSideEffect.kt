package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter

sealed interface AppDrawerSideEffect {
    sealed interface ItemClickedSideEffect : AppDrawerSideEffect {
        data object RefreshClickedSideEffect : ItemClickedSideEffect
        data object AccountClickedSideEffect : ItemClickedSideEffect
        data object SettingsClickedSideEffect : ItemClickedSideEffect
        data object HelpFeedbackClickedSideEffect : ItemClickedSideEffect
        data object SignOutSideEffect : ItemClickedSideEffect
    }
}