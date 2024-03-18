package com.arakim.googlecalendarclone.ui.navigationdrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignOutUseCase
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppDrawerViewModel @Inject constructor(
    val appDrawerPresenter: AppDrawerPresenter,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    init {
        appDrawerPresenter.initialize(viewModelScope)
    }

    fun signOut() {
        signOutUseCase()
    }
}