package com.kiwa.data.repository

import com.kiwa.data.datasource.FlupetDataSource
import com.kiwa.data.model.FlupetStatusException
import com.kiwa.domain.repository.FlupetRepository
import com.kiwa.fluffit.model.flupet.FlupetStatus
import com.kiwa.fluffit.model.flupet.response.BasicResponse
import com.kiwa.fluffit.model.flupet.response.MyFlupets
import com.kiwa.fluffit.model.main.FullnessUpdateInfo
import com.kiwa.fluffit.model.main.HealthUpdateInfo
import com.kiwa.fluffit.model.main.MainUIModel
import com.kiwa.fluffit.model.main.response.toMainUIModel
import javax.inject.Inject

class FlupetRepositoryImpl @Inject constructor(
    private val flupetDataSource: FlupetDataSource
) :
    FlupetRepository {

    override suspend fun getMainUIInfo(): Result<MainUIModel> =
        flupetDataSource.fetchMainUIInfo().fold(
            onSuccess = {
                Result.success(it.toMainUIModel())
            },
            onFailure = { Result.failure(it) }
        )

    override suspend fun updateFullness(): Result<FullnessUpdateInfo> =
        flupetDataSource.fetchFullness().fold(
            onSuccess = {
                Result.success(it.copy(flupetStatus = FlupetStatus.Alive))
            },
            onFailure = {
                when (it) {
                    is FlupetStatusException.FlupetIsDead -> Result.success(
                        FullnessUpdateInfo(
                            0,
                            0L,
                            false,
                            FlupetStatus.Dead
                        )
                    )

                    is FlupetStatusException.FlupetNotExist -> Result.success(
                        FullnessUpdateInfo(
                            0,
                            0L,
                            false,
                            FlupetStatus.None
                        )
                    )

                    else -> Result.failure(it)
                }
            }
        )

    override suspend fun updateHealth(): Result<HealthUpdateInfo> =
        flupetDataSource.fetchHealth().fold(
            onSuccess = {
                Result.success(it.copy(flupetStatus = FlupetStatus.Alive))
            },
            onFailure = {
                when (it) {
                    is FlupetStatusException.FlupetIsDead -> Result.success(
                        HealthUpdateInfo(
                            0,
                            0L,
                            false,
                            FlupetStatus.Dead
                        )
                    )

                    is FlupetStatusException.FlupetNotExist -> Result.success(
                        HealthUpdateInfo(
                            0,
                            0L,
                            false,
                            FlupetStatus.None
                        )
                    )

                    else -> Result.failure(it)
                }
            }
        )

    override suspend fun getNewEgg(): Result<MainUIModel> = flupetDataSource.createNewEgg().fold(
        onSuccess = {
            Result.success(it.toMainUIModel())
        },
        onFailure = { Result.failure(it) }
    )

    override suspend fun editFlupetNickname(nickname: String): Result<BasicResponse> =
        flupetDataSource.editFlupetNickname(nickname).fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = { Result.failure(it) }
        )

    override suspend fun evolve(): Result<MainUIModel> = flupetDataSource.evolve().fold(
        onSuccess = {
            Result.success(it.toMainUIModel())
        },
        onFailure = { Result.failure(it) }
    )

    override suspend fun getHistory(): Result<List<MyFlupets>> =
        flupetDataSource.loadHistory().fold(
            onSuccess = { response ->
                Result.success(
                    response.flupets.map {
                        MyFlupets(
                            it.species,
                            it.name,
                            it.imageUrl,
                            it.birthDay,
                            it.endDay,
                            it.age,
                            it.steps
                        )
                    }
                )
            },
            onFailure = {
                Result.failure(it)
            }
        )
}
