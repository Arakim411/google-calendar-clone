package com.arakim.googlecalendarclone.ui.screen.splash

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.arakim.googlecaelndarclone.domain.user.User
import com.arakim.googlecalendarclone.domain.user.signin.usecases.GetRefreshedUserUseCase
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashScreenViewModel
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashSideEffect
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ReadyState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.SigningInState
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.test.CoroutineTest
import com.arakim.googlecalendarclone.util.test.randomString
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.stream.Stream
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SplashViewModelTest : CoroutineTest() {
    private lateinit var subject: SplashScreenViewModel

    val getRefreshedUserUseCase = mockk<GetRefreshedUserUseCase>()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        subject = SplashScreenViewModel(getRefreshedUserUseCase)
    }

    @Test
    fun `initial state is ready`() {
        assertThat(subject.state.value).isEqualTo(ReadyState)
    }

    @Test
    fun `when tryAutoSignIn is invoked and failed then go to ErrorState`() = runTest {
        val error = mockk<CommonError>()
        coEvery { getRefreshedUserUseCase() } returns TypedResult.failure(error)
        subject.tryAutoSignIn()
        subject.state.test {
            awaitItem() // skip first ReadyState
            assertThat(awaitItem()).isEqualTo(SigningInState)
            assertThat(awaitItem()).isEqualTo(ErrorState(error))
        }
    }

    @ParameterizedTest
    @MethodSource("userSource")
    fun `when tryAutoSignIn is invoked and success then emit side effect`(
        user: User,
    ) = runTest {
        coEvery { getRefreshedUserUseCase() } returns TypedResult.success(user)

        subject.tryAutoSignIn()
        subject.state.test {
            awaitItem() // skip first ReadyState
            assertThat(awaitItem()).isEqualTo(SigningInState)
            assertThat(awaitItem()).isEqualTo(ReadyState)
        }

        subject.sideEffectFlow.test { assertThat(awaitItem()).isEqualTo(user.getExpectedSideEffect()) }
        coVerify { getRefreshedUserUseCase() }
    }

    private fun User.getExpectedSideEffect(): SplashSideEffect = when (this) {
        is User.AnonymousUser -> SplashSideEffect.UserNotSignedInSideEffect
        is User.SignedUser -> SplashSideEffect.UserSignedInSideEffect
    }

    companion object {
        @JvmStatic
        fun userSource() = Stream.of(
            Arguments.of(User.AnonymousUser),
            Arguments.of(User.SignedUser(randomString()))
        )
    }
}