package com.arakim.googlecalendarclone.ui.screen.signin.presenter.reducer

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.arakim.googlecaelndarclone.domain.user.SignInMethod.FakeMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethod.GoogleMethod
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignInUserUseCase
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithFake
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithGoogle
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignedInErrorAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInSideEffect
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ReadyState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.SigningInState
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.test.CoroutineTest
import com.arakim.googlecalendarclone.util.test.randomString
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@OptIn(ExperimentalCoroutinesApi::class)
class SignInUserReducerTest : CoroutineTest() {

    lateinit var subject: SignInUserReducer

    @MockK
    lateinit var signInUserUseCase: SignInUserUseCase

    @MockK
    lateinit var onAction: (SignInAction) -> Unit

    @MockK(relaxed = true)
    lateinit var logInvalidState: SignInState.() -> SignInState

    @MockK
    lateinit var emitSideEffect: (SignInSideEffect) -> Unit

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { emitSideEffect(any()) } just runs
        subject = SignInUserReducer(signInUserUseCase)
        subject.initialize(
            coroutineScope = coroutineScope,
            onAction = onAction,
            logInvalidState = logInvalidState,
            emitSideEffect = emitSideEffect,
        )
    }

    @Test
    fun `when SignInAction is received in Signing in state then logInvalidState()`() {
        every { logInvalidState(any()) } answers { firstArg() }
        with(subject) {
            SigningInState.reduce(mockk<SignInAction>())
        }
        verify { logInvalidState(SigningInState) }
    }

    @ParameterizedTest
    @MethodSource("statesForSigningInSource")
    fun `when SignInUserWithFakeAction is received and success then signIn with FakeMethod`(
        state: SignInState,
    ) = runTest {
        val user = mockk<SignedUser>()
        coEvery { signInUserUseCase(FakeMethod) } returns TypedResult.success(user)

        with(subject) {
            assertThat(state.reduce(SignInAction.SignInUserWithFake)).isEqualTo(SigningInState)
        }
        advanceUntilIdle()
        verify { emitSideEffect(SignInSideEffect.SignedInSideEffect(user)) }
    }

    @ParameterizedTest
    @MethodSource("statesForSigningInSource")
    fun `when SignInUserWithGoogle is received and success then signIn with GoogleMethod`(
        state: SignInState,
    ) = runTest {
        val user = mockk<SignedUser>()
        val accountName = randomString()
        coEvery { signInUserUseCase(GoogleMethod(accountName)) } returns TypedResult.success(user)

        with(subject) {
            assertThat(state.reduce(SignInUserWithGoogle(accountName))).isEqualTo(SigningInState)
        }
        advanceUntilIdle()
        verify { emitSideEffect(SignInSideEffect.SignedInSideEffect(user)) }
    }

    @ParameterizedTest
    @MethodSource("statesForSigningInSource")
    fun `when signIn failed then emit SignedInErrorAction`(
        state: SignInState,
    ) = runTest {
        val error = mockk<CommonError>()
        coEvery { signInUserUseCase(any()) } returns TypedResult.failure(error)
        every { onAction(SignedInErrorAction(error)) } just runs

        listOf(
            SignInUserWithFake,
            SignInUserWithGoogle(randomString()),
        ).forEach {
            with(subject) { state.reduce(it) }
            advanceUntilIdle()
            verify { onAction(SignedInErrorAction(error)) }
        }
    }

    @ParameterizedTest
    @MethodSource("statesForSigningInSource")
    fun `when SignedInErrorAction is received then goes to ErrorState`(
        state: SignInState,
    ) = runTest {
        val error = mockk<CommonError>()
        with(subject) {
            val newState = state.reduce(SignedInErrorAction(error))
            val expectedState = ErrorState(error)
            assertThat(newState).isEqualTo(expectedState)
        }
    }

    companion object {
        @JvmStatic
        fun statesForSigningInSource() = listOf(
            ErrorState(mockk()),
            ReadyState,
        )
    }
}