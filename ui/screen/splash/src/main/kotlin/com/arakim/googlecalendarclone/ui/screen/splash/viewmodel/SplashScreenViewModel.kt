package com.arakim.googlecalendarclone.ui.screen.splash.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.googlecaelndarclone.domain.user.User
import com.arakim.googlecaelndarclone.domain.user.User.AnonymousUser
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.domain.user.signin.usecases.GetRefreshedUserUseCase
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashSideEffect.UserNotSignedInSideEffect
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashSideEffect.UserSignedInSideEffect
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ReadyState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.SigningInState
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Stable
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getRefreshedUserUseCase: GetRefreshedUserUseCase
) : ViewModel() {

    val state = MutableStateFlow<SplashState>(ReadyState)
    val sideEffectFlow = MutableSharedFlow<SplashSideEffect>(replay = 1)

    fun tryAutoSignIn() {
        if (state.value == SigningInState) return

        viewModelScope.launch {
            state.value = SigningInState
            getRefreshedUserUseCase()
                .onSuccess { onGetUserSuccess(it) }
                .onFailure { onGetUserFailed(it) }
        }
    }

    private suspend fun onGetUserSuccess(user: User) {

        val sideEffect = when (user) {
            AnonymousUser -> UserNotSignedInSideEffect
            is SignedUser -> UserSignedInSideEffect
        }
        sideEffectFlow.emit(sideEffect)
        state.value = ReadyState
    }

    private fun onGetUserFailed(error: CommonError) {
        state.value = ErrorState(error)
    }
}