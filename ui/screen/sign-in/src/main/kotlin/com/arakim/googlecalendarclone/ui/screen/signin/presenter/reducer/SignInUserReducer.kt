package com.arakim.googlecalendarclone.ui.screen.signin.presenter.reducer

import com.arakim.googlecaelndarclone.domain.user.SignInMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethod.FakeMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethod.GoogleMethod
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignInUserUseCase
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.Action
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SideEffect
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithFake
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithGoogle
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignedInErrorAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInSideEffect
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.SigningInState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.State
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import javax.inject.Inject

class SignInUserReducer @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
) : StateReducerWithSideEffect<State, Action, SignInAction, SideEffect>() {

    override fun State.reduce(action: SignInAction): State = when (this) {
        SigningInState -> logInvalidState()
        else -> reduceSignInAction(action)
    }

    private fun State.reduceSignInAction(action: SignInAction): State = when (action) {
        SignInUserWithFake -> signIn(FakeMethod)
        is SignInUserWithGoogle -> signIn(GoogleMethod(action.accountName))
        is SignedInErrorAction -> ErrorState(action.error)
    }

    private fun State.signIn(method: SignInMethod): State {
        coroutineScope.yielded {
            signInUserUseCase(method)
                .onSuccess { user ->
                    emitSideEffect(SignInSideEffect.SignedInSideEffect(user))
                }.onFailure {
                    onAction(SignedInErrorAction(it))
                }
        }

        return SigningInState
    }
}