package com.arakim.googlecalendarclone.domain.calendarinfo.useCases

import com.arakim.googlecaelndarclone.domain.user.SignInMethodId
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.domain.calendarinfo.UserCalendarRepository
import com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo
import com.arakim.googlecalendarclone.domain.user.signin.usecases.GetLocalUserUseCase
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.CommonError.OtherError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.getOrNull
import java.time.LocalDate
import javax.inject.Inject

class GetUserCalendarInfoUseCase @Inject constructor(
    private val calendarInfoRepository: Map<SignInMethodId, @JvmSuppressWildcards UserCalendarRepository>,
    private val localUser: GetLocalUserUseCase,
) {

    suspend operator fun invoke(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<UserCalendarInfo, CommonError> {
        val user = localUser()
        val methodId = (user.getOrNull() as? SignedUser)?.signInMethodId ?: return userNotSignedFailure

        return calendarInfoRepository[methodId]?.getUserCalendarInfo(
            fromDate = fromDate,
            toDate = toDate,
        ) ?: repositoryNotFoundFailure(methodId)
    }

    private val userNotSignedFailure: TypedResult<UserCalendarInfo, CommonError>
        get() = TypedResult.failure(OtherError(Exception("user must be sign in")))

    private fun repositoryNotFoundFailure(
        signInMethodId: SignInMethodId,
    ): TypedResult<UserCalendarInfo, CommonError> =
        TypedResult.failure(OtherError(Exception("repository not found for $signInMethodId")))
}