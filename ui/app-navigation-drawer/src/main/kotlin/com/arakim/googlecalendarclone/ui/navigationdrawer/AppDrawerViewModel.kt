package com.arakim.googlecalendarclone.ui.navigationdrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppDrawerViewModel @Inject constructor(
    val appDrawerPresenter: AppDrawerPresenter
) : ViewModel() {

    init {
        appDrawerPresenter.initialize(viewModelScope)
    }
}