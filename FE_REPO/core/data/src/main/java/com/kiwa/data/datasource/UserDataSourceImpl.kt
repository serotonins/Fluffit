package com.kiwa.data.datasource

import com.kiwa.data.api.AuthService
import com.kiwa.data.api.NaverAuthService
import com.kiwa.data.api.NaverLoginService
import com.kiwa.fluffit.model.user.request.NaverLoginRequest
import com.kiwa.fluffit.model.user.request.UserRequest
import com.kiwa.fluffit.model.user.response.AutoLoginResponse
import com.kiwa.fluffit.model.user.response.Tokens
import com.kiwa.fluffit.model.user.response.UserModificationResponse
import com.kiwa.fluffit.model.user.response.UserResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val naverAuthService: NaverAuthService,
    private val naverLoginService: NaverLoginService
) : UserDataSource {
    override suspend fun autoLogin(accessToken: String): Result<AutoLoginResponse> = runCatching {
        authService.autoLogin(accessToken)
    }

    override suspend fun getNaverLoginId(accessToken: String): Result<String> = runCatching {
        naverLoginService.getNaverUserId("Bearer $accessToken").response.id
    }

    override suspend fun signInNaver(
        userCode: String,
        signature: String
    ): Result<Tokens> = runCatching {
        authService.signInNaver(NaverLoginRequest(userCode, signature))
    }

    override suspend fun signOutNaver(
        naverClientId: String,
        naverSecret: String,
        accessToken: String
    ): Result<Unit> = runCatching {
        naverAuthService.signOutWithNaver(
            naverClientId,
            naverSecret,
            accessToken
        )
    }

    override suspend fun loadUserName(accessToken: String): Result<UserResponse> =
        runCatching {
            authService.loadUserName("Bearer $accessToken")
        }

    override suspend fun updateUserName(
        accessToken: String,
        name: String
    ): Result<UserModificationResponse> =
        runCatching {
            authService.updateUserName(accessToken = accessToken, nickname = UserRequest(name))
        }
}
