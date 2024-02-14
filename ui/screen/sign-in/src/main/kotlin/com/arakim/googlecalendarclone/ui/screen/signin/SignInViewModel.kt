package com.arakim.googlecalendarclone.ui.screen.signin

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Stable
@HiltViewModel
class SignInViewModel @Inject constructor(
    val presenter: SignInPresenter,
) : ViewModel() {
    init {
        presenter.initialize(viewModelScope)
    }
}