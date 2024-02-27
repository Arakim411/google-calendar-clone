package com.arakim.googlecalendarclone.ui.screen.signin.compose

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod.FakeMethod
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction
import com.arakim.googlecalendarclone.ui.screen.signin.SignInViewModel
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInPresenter
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.SigningInState
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

//TODO tests for sign in with google
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignInScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createComposeRule()

    @Inject
    lateinit var presneter: SignInPresenter

    @MockK
    @BindValue
    lateinit var signInService: SignInService

    @MockK
    lateinit var navigate: (NavigationAction) -> Unit

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        hiltRule.inject()
        viewModel = SignInViewModel(presneter)

    }

    @Test
    fun when_sign_in_with_fake_is_clicked_then_sign_in() {
        coEvery { signInService.signIn(FakeMethod) } just awaits
        setContent()

        assertThat(presneter.stateFlow.value).isEqualTo(SignInState.ReadyState)
        composeRule.onNodeWithTag(SignInScreenTestTags.SignInWithFakeButton).performClick()
        assertThat(presneter.stateFlow.value).isEqualTo(SigningInState)
        coVerify(exactly = 1) { signInService.signIn(FakeMethod) }
    }

    private fun setContent() {
        composeRule.setContent {
            SignInScreen(viewModel, navigate)
        }
    }
}