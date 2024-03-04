package com.arakim.googlecalendarclone.domain.calendarinfo.usecases

import com.arakim.googlecaelndarclone.domain.user.SignInMethodId
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.domain.calendarinfo.CalendarInfoRepository
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarInfo
import com.arakim.googlecalendarclone.domain.user.signin.usecases.GetLocalUserUseCase
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.CommonError.OtherError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.getOrNull
import javax.inject.Inject

class GetCalendarInfoUseCase @Inject constructor(
    private val calendarInfoRepository: Map<SignInMethodId, @JvmSuppressWildcards CalendarInfoRepository>,
    private val localUser: GetLocalUserUseCase,
) {

    suspend operator fun invoke(): TypedResult<CalendarInfo, CommonError> {
        val user = localUser()
        val methodId = (user.getOrNull() as? SignedUser)?.signInMethodId ?: return userNotSignedFailure

        return calendarInfoRepository[methodId]?.getCalendarInfo() ?: repositoryNotFoundFailure(methodId)
    }

    private val userNotSignedFailure: TypedResult<CalendarInfo, CommonError>
        get() = TypedResult.failure(OtherError(Exception("user must be sign in")))

    private fun repositoryNotFoundFailure(
        signInMethodId: SignInMethodId,
    ): TypedResult<CalendarInfo, CommonError> =
        TypedResult.failure(OtherError(Exception("repository not found for $signInMethodId")))
}