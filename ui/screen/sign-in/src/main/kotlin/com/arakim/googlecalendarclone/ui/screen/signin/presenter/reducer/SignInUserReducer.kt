package com.arakim.googlecalendarclone.ui.screen.signin.presenter.reducer

import com.arakim.googlecaelndarclone.domain.user.SignInMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethod.FakeMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethod.GoogleMethod
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignInUserUseCase
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.Action
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SideEffect
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.RetrySignIn
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithFake
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithGoogle
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignedInErrorAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInSideEffect
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ReadyState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.SigningInState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.State
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.CommonError.OtherError
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import com.google.android.gms.auth.UserRecoverableAuthException
import javax.inject.Inject

class SignInUserReducer @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
) : StateReducerWithSideEffect<State, Action, SignInAction, SideEffect>() {

    override fun State.reduce(action: SignInAction): State = reduceSignInAction(action)

    private fun State.reduceSignInAction(action: SignInAction): State = when (action) {
        SignInUserWithFake -> signIn(FakeMethod, action)
        is SignInUserWithGoogle -> signIn(GoogleMethod(action.accountName), action)
        is SignedInErrorAction -> ErrorState(action.error)
        RetrySignIn -> reduceRetrySignIn()
    }

    private fun State.signIn(method: SignInMethod, action: SignInAction): State {
        coroutineScope.yielded {
            signInUserUseCase(method)
                .onSuccess { user ->
                    emitSideEffect(SignInSideEffect.SignedInSideEffect(user))
                }.onFailure {
                    it.getUserRecoverableAuthException()?.let { exception ->
                        emitSideEffect(SignInSideEffect.UserRecoverableAuthExceptionSideEffect(exception))
                    } ?: onAction(SignedInErrorAction(it))
                }
        }

        return SigningInState(action)
    }

    @Suppress("BracesOnWhenStatements")
    private fun State.reduceRetrySignIn(): State = when (this) {
        is SigningInState -> {
            onAction(this.action)
            this
        }

        else -> ReadyState
    }
}

private fun CommonError.getUserRecoverableAuthException(): UserRecoverableAuthException? =
    (this as? OtherError)?.error as? UserRecoverableAuthException
