package com.arakim.googlecalendarclone.ui.screen.home

import androidx.lifecycle.ViewModel
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignOutUseCase
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val appDrawerPresenter: AppDrawerPresenter,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    fun signOut() {
        signOutUseCase()
    }
}