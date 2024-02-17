package com.arakim.googlecalendarclone.ui.screen.home

import androidx.lifecycle.ViewModel
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    fun signOut() {
        signOutUseCase()
    }
}