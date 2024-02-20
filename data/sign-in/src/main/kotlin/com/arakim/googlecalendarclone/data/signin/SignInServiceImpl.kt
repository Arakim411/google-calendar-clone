package com.arakim.googlecalendarclone.data.signin

import com.arakim.googlecaelndarclone.domain.user.User
import com.arakim.googlecaelndarclone.domain.user.User.AnonymousUser
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.data.auth.fake.FakeSignInMethodService
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.google.GoogleSignInMethodService
import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import com.arakim.googlecalendarclone.domain.user.signin.SignedUserResult
import com.arakim.googlecalendarclone.domain.user.signin.UserResult
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod.FakeMethod
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod.GoogleMethod
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId.Fake
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId.Google
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.map
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
class SignInServiceImpl @Inject constructor(
    private val googleSignInMethodService: GoogleSignInMethodService,
    private val fakeSignInMethodService: FakeSignInMethodService,
    private val authUserRepository: AuthUserRepository,
) : SignInService {
    private val userState by lazy { MutableStateFlow<UserResult>(TypedResult.success(getSavedUser())) }

    override suspend fun signIn(method: SignInMethod): SignedUserResult =
        method.getAuthUser().asDomainUserAndUpdate()

    override fun getLocalUser(): UserResult = userState.value

    override suspend fun getRefreshedUser(): UserResult {
        val authUser = authUserRepository.authUser ?: return TypedResult.success(AnonymousUser)

        val refreshedUser = when (authUser.methodId) {
            Google -> googleSignInMethodService.getRefreshedAuthUser(authUser)
            Fake -> fakeSignInMethodService.getRefreshedAuthUser(authUser)
        }

        return refreshedUser.asDomainUserAndUpdate().map { it }
    }

    override fun userFlow(): Flow<UserResult> = userState

    override fun signOut() {
        authUserRepository.clearUser()
        userState.value = TypedResult.success(AnonymousUser)
    }

    private suspend fun SignInMethod.getAuthUser() = when (this) {
        FakeMethod -> fakeSignInMethodService.getAuthUser(this as FakeMethod)
        is GoogleMethod -> googleSignInMethodService.getAuthUser(this)
    }

    private fun TypedResult<AuthUser, CommonError>.asDomainUserAndUpdate(): SignedUserResult {
        onSuccess { authUserRepository.saveUser(it) }
        return map { it.toDomain() }.also {
            userState.value = it.map { user -> user }
        }
    }

    private fun getSavedUser(): User = authUserRepository.authUser?.toDomain() ?: AnonymousUser
}

fun AuthUser.toDomain(): SignedUser = SignedUser(
    name = name
)