package com.arakim.googlecalendarclone.ui.screen.splash

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.arakim.googlecalendarclone.ui.common.ErrorViewTestTag
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.HomeDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SignInDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.toAction
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashScreenViewModel
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashSideEffect
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ReadyState
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.test.allDerivedSealedClassesRecursive
import com.arakim.googlecalendarclone.util.test.asMocksOrObjects
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    var viewModel: SplashScreenViewModel = mockk()
    val navigate: (NavigationAction) -> Unit = mockk(relaxed = true)

    val showNativeSplash = mutableStateOf(false)

    val state = MutableStateFlow<SplashState>(ReadyState)
    val sideEffect = MutableSharedFlow<SplashSideEffect>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { viewModel.state } returns state
        every { viewModel.sideEffectFlow } returns sideEffect
        every { viewModel.tryAutoSignIn() } just runs
    }

    @Test
    fun when_UserNotSignedInSideEffect_then_navigate_to_SignInDestination() = runTest {
        setContent()

        sideEffect.emit(SplashSideEffect.UserNotSignedInSideEffect)
        verify { navigate(SignInDestination.toAction(removeAllDestinations = true)) }
    }

    @Test
    fun when_UserSignedInSideEffect_then_navigate_to_HomeDestination() = runTest {
        setContent()

        sideEffect.emit(SplashSideEffect.UserSignedInSideEffect)
        verify { navigate(HomeDestination.toAction(removeAllDestinations = true)) }
    }

    @Test
    fun when_state_is_SigningInState_then_showSplash_is_true_otherwise_false() = runTest {
        setContent()
        SplashState::class.allDerivedSealedClassesRecursive().asMocksOrObjects().forEach { state ->
            viewModel.state.value = state
            assertThat(showNativeSplash.value).isEqualTo(state == SplashState.SigningInState)
        }
    }

    @Test
    fun when_state_is_ErrorState_then_show_error_view() {
        setContent()

        composeRule.onNodeWithTag(ErrorViewTestTag).assertDoesNotExist()
        viewModel.state.value = ErrorState(CommonError.UnknownError)
        composeRule.onNodeWithTag(ErrorViewTestTag).assertExists()
    }

    private fun setContent() {
        composeRule.setContent {
            SplashScreen(
                viewModel = viewModel,
                navigate = navigate,
                showNativeSplash = showNativeSplash
            )
        }
    }
}