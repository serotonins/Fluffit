package com.kiwa.data.api

import com.kiwa.fluffit.model.user.request.NaverLoginRequest
import com.kiwa.fluffit.model.user.request.UserRequest
import com.kiwa.fluffit.model.user.response.TokenResponse
import com.kiwa.fluffit.model.user.response.UserModificationResponse
import com.kiwa.fluffit.model.user.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {
    // api 언제 나와요

    @GET("api/members/refresh")
    suspend fun refreshUserToken(
        @Header("refreshToken") refreshToken: String
//        @Header("Authorization") accessToken: String
    ): TokenResponse

    @POST("api/members/autoLogin")
    suspend fun autoLogin(
        @Header("Authorization") accessToken: String
    ): TokenResponse

    @POST("api/login")
    suspend fun signInNaver(
        @Body naverLoginRequest: NaverLoginRequest
    ): TokenResponse

    @GET("api/members/nickname")
    suspend fun loadUserName(): UserResponse

    @PUT("api/members/nickname")
    suspend fun saveNewUserName(
        @Body nickname : UserRequest
    ) : UserModificationResponse
}
