package com.arakim.googlecalendarclone.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val calendarPresenter: CalendarPresenter,
) : ViewModel() {
    init {
        calendarPresenter.initialize(viewModelScope)
    }
}