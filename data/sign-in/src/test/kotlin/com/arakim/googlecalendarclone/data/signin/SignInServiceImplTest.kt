package com.arakim.googlecalendarclone.data.signin

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.arakim.googlecaelndarclone.domain.user.User
import com.arakim.googlecalendarclone.data.auth.fake.FakeSignInMethodService
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.SignInMethodService
import com.arakim.googlecalendarclone.data.signin.google.GoogleSignInMethodService
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId.Fake
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId.Google
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.getOrThrow
import com.arakim.googlecalendarclone.util.kotlin.isFailure
import com.arakim.googlecalendarclone.util.test.CoroutineTest
import com.arakim.googlecalendarclone.util.test.randomString
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import java.util.stream.Stream
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SignInServiceImplTest : CoroutineTest() {

    private lateinit var subject: SignInServiceImpl

    @MockK
    lateinit var googleSignInMethodService: GoogleSignInMethodService

    @MockK
    lateinit var fakeSignInMethodService: FakeSignInMethodService

    @MockK
    lateinit var authRepository: AuthUserRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { authRepository.authUser } returns null
        subject = SignInServiceImpl(
            googleSignInMethodService, fakeSignInMethodService, authRepository
        )
    }

    @ParameterizedTest
    @MethodSource("allPossibleAuthUserAndNullSource")
    fun `initial user is obtained from auth repository`(
        initialAuthUser: AuthUser?,
    ) = runTest {
        every { authRepository.authUser } returns initialAuthUser
        val expectedDomainUser = when (initialAuthUser) {
            null -> User.AnonymousUser
            else -> initialAuthUser.toDomain()
        }

        assetLocalUser(expectedDomainUser)
    }

    @Test
    fun `when getRefreshedUser() is invoked and authUser is null then return Anonymous`() = runTest {
        every { authRepository.authUser } returns null

        assertThat(subject.getRefreshedUser().getOrThrow()).isEqualTo(User.AnonymousUser)
        assetLocalUser(User.AnonymousUser)
    }

    @ParameterizedTest
    @MethodSource("allPossibleAuthUserSource")
    fun `when getRefreshedUser() is invoked and AuthUser exists then returns user from correct auth service`(
        currentAuthUser: AuthUser,
    ) = runTest {
        val refreshedUser = fakeAuthUser(methodId = currentAuthUser.methodId)
        val signInMethodService = currentAuthUser.methodId.getExpectedService()

        every { authRepository.authUser } returns currentAuthUser
        every { authRepository.saveUser(refreshedUser) } just runs
        coEvery { signInMethodService.getRefreshedAuthUser(currentAuthUser) } returns TypedResult.success(
            refreshedUser
        )

        assertThat(subject.getRefreshedUser().getOrThrow()).isEqualTo(refreshedUser.toDomain())
        coVerify { signInMethodService.getRefreshedAuthUser(currentAuthUser) }
        verify { authRepository.saveUser(refreshedUser) }
        assetLocalUser(refreshedUser.toDomain())
    }

    @Test
    fun `when signOut() is invoked then update localUser`() = runTest {
        val initialUser = fakeAuthUser()
        every { authRepository.authUser } returns initialUser
        every { authRepository.clearUser() } just runs

        assetLocalUser(initialUser.toDomain())

        subject.signOut()

        assetLocalUser(User.AnonymousUser)
        verify { authRepository.clearUser() }
    }

    @ParameterizedTest
    @MethodSource("allPossibleSignInMethods")
    fun `when signIn is invoked then sign in with correct service`(
        method: SignInMethod,
    ) = runTest {
        val authUser = fakeAuthUser(methodId = method.id)
        val signInMethodService = method.id.getExpectedService()

        coEvery { signInMethodService.getAuthUser(method) } returns TypedResult.success(authUser)
        every { authRepository.saveUser(authUser) } just runs

        assetLocalUser(User.AnonymousUser)
        assertThat(subject.signIn(method).getOrThrow()).isEqualTo(authUser.toDomain())
        assetLocalUser(authUser.toDomain())

        verify { authRepository.saveUser(authUser) }
    }

    @ParameterizedTest
    @MethodSource("allPossibleSignInMethods")
    fun `when signIn is invoked and failed then update local user state, but not update auth user`(
        method: SignInMethod,
    ) = runTest {
        val signInMethodService = method.id.getExpectedService()

        coEvery { signInMethodService.getAuthUser(method) } returns TypedResult.failure(mockk())

        assetLocalUser(User.AnonymousUser)
        assertThat(subject.signIn(method).isFailure()).isTrue()
        assertThat(subject.getLocalUser().isFailure()).isTrue()
        subject.userFlow().test { assertThat(awaitItem().isFailure()).isTrue() }
    }

    private suspend fun assetLocalUser(domainUser: User) {
        assertThat(subject.getLocalUser().getOrThrow()).isEqualTo(domainUser)
        subject.userFlow().test {
            assertThat(awaitItem().getOrThrow()).isEqualTo(domainUser)
        }
    }

    private fun SignInMethodId.getExpectedService(): SignInMethodService<SignInMethod> = when (this) {
        Google -> googleSignInMethodService as SignInMethodService<SignInMethod>
        Fake -> fakeSignInMethodService as SignInMethodService<SignInMethod>
    }

    companion object {
        @JvmStatic
        fun allPossibleAuthUserSource(): Stream<Arguments> = Stream.of(
            Arguments.of(fakeAuthUser(methodId = Google)),
            Arguments.of(fakeAuthUser(methodId = Fake)),
        )

        @JvmStatic
        fun allPossibleAuthUserAndNullSource(): Stream<Arguments> = Stream.of(
            *allPossibleAuthUserSource().toList().toTypedArray(), Arguments.of(null)
        )

        @JvmStatic
        fun allPossibleSignInMethods(): Stream<Arguments> = Stream.of(
            Arguments.of(SignInMethod.GoogleMethod(randomString())),
            Arguments.of(SignInMethod.FakeMethod),
        )

        fun fakeAuthUser(
            name: String = randomString(),
            methodId: SignInMethodId = SignInMethodId.entries.random(),
            authToken: String = randomString()
        ): AuthUser = AuthUser(name, methodId, authToken)
    }
}