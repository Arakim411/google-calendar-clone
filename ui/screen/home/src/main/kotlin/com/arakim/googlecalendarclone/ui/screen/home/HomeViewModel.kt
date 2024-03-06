package com.arakim.googlecalendarclone.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.useCases.GetUserCalendarInfoUseCase
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignOutUseCase
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    val appDrawerPresenter: AppDrawerPresenter,
    private val getCalendarInfoUseCase: GetUserCalendarInfoUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    fun signOut() {
        signOutUseCase()
    }

    fun getCalendarInfo() {
        viewModelScope.launch {
            getCalendarInfoUseCase(LocalDate.now())
        }
    }
}