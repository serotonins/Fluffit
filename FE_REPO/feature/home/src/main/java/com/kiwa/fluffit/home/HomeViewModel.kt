package com.kiwa.fluffit.home

import androidx.lifecycle.viewModelScope
import com.kiwa.domain.usecase.EditFlupetNicknameUseCase
import com.kiwa.domain.usecase.EvolveFlupetUseCase
import com.kiwa.domain.usecase.GetMainUIInfoUseCase
import com.kiwa.domain.usecase.GetNewEggUseCase
import com.kiwa.domain.usecase.UpdateFullnessUseCase
import com.kiwa.domain.usecase.UpdateHealthUseCase
import com.kiwa.fluffit.base.BaseViewModel
import com.kiwa.fluffit.model.flupet.FlupetStatus
import com.kiwa.fluffit.model.main.FullnessUpdateInfo
import com.kiwa.fluffit.model.main.HealthUpdateInfo
import com.kiwa.fluffit.model.main.MainUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMainUIInfoUseCase: GetMainUIInfoUseCase,
    private val updateFullnessUseCase: UpdateFullnessUseCase,
    private val updateHealthUseCase: UpdateHealthUseCase,
    private val getNewEggUseCase: GetNewEggUseCase,
    private val editFlupetNicknameUseCase: EditFlupetNicknameUseCase,
    private val evolveFlupetUseCase: EvolveFlupetUseCase
) : BaseViewModel<HomeViewState, HomeViewEvent>() {
    override fun createInitialState(): HomeViewState =
        HomeViewState.Default()

    override fun onTriggerEvent(event: HomeViewEvent) {
        setEvent(event)
    }

    private lateinit var updateFullnessJob: Job
    private lateinit var updateHealthJob: Job

    private val fullnessUpdateState: MutableStateFlow<FullnessUpdateInfo> =
        MutableStateFlow(FullnessUpdateInfo(0, 0L, false, FlupetStatus.None))

    private val healthUpdateState: MutableStateFlow<HealthUpdateInfo> =
        MutableStateFlow(HealthUpdateInfo(0, 0L, false, FlupetStatus.None))

    init {
        viewModelScope.launch {
            fullnessUpdateState.collect {
                setState { updateFullness(it) }
            }
        }
        viewModelScope.launch {
            healthUpdateState.collect {
                setState { updateHealth(it) }
            }
        }
        handleUIEvent()
        getMainUIInfo()
        viewModelScope.launch {
            updateFullnessUseCase().fold(
                onSuccess = {
                    fullnessUpdateState.emit(it)
                },
                onFailure = {
                }
            )
        }

        viewModelScope.launch {
            updateHealthUseCase().fold(
                onSuccess = {
                    healthUpdateState.emit(it)
                },
                onFailure = {
                }
            )
        }
    }

    private fun handleUIEvent() {
        viewModelScope.launch {
            uiEvent.collect { event ->
                when (event) {
                    is HomeViewEvent.OnClickConfirmEditButton -> onConfirmName(event.name)
                    HomeViewEvent.OnClickPencilButton -> setState { onStartEditName() }
                    is HomeViewEvent.OnUpdateFullness -> enqueueNewRequest(
                        event.stat,
                        uiState.value.nextFullnessUpdateTime
                    )

                    is HomeViewEvent.OnUpdateHealth -> enqueueNewRequest(
                        event.stat,
                        uiState.value.nextHealthUpdateTime
                    )

                    HomeViewEvent.OnClickNewEggButton -> getNewEgg()
                    HomeViewEvent.OnClickTombStone -> setState { showEmptyEgg() }
                    HomeViewEvent.OnDismissSnackBar -> setState { resetMessage() }
                    HomeViewEvent.OnClickEvolutionButton -> evolveFlupet()
                    HomeViewEvent.OnEndEvolutionAnimation -> setState { onEndEvolutionAnimation() }
                    HomeViewEvent.OnUpdateCoin -> getMainUIInfo()
                }
            }
        }
    }

    private fun HomeViewState.onEndEvolutionAnimation(): HomeViewState =
        when (this) {
            is HomeViewState.Default -> this.copy(evolution = false, beforeImage = "")
            is HomeViewState.FlupetNameEdit -> this
        }

    private suspend fun evolveFlupet() {
        evolveFlupetUseCase().fold(
            onSuccess = {
                setState { evolve(it) }
            },
            onFailure = {}
        )
    }

    private fun HomeViewState.evolve(mainUIModel: MainUIModel): HomeViewState = when (this) {
        is HomeViewState.Default -> this.copy(
            flupet = mainUIModel.flupet.copy(
                birthDay = this.flupet.birthDay,
                age = this.flupet.age
            ),
            nextFullnessUpdateTime = mainUIModel.nextFullnessUpdateTime,
            nextHealthUpdateTime = mainUIModel.nextHealthUpdateTime,
            flupetStatus = mainUIModel.flupetStatus,
            evolution = true,
            beforeImage = flupet.imageUrls.nodding.ifEmpty { this.flupet.imageUrls.standard }
        )

        is HomeViewState.FlupetNameEdit -> this
    }

    private fun HomeViewState.resetMessage(): HomeViewState =
        when (this) {
            is HomeViewState.Default -> this.copy(message = "")
            is HomeViewState.FlupetNameEdit -> this.copy(message = "")
        }

    private fun HomeViewState.showEmptyEgg(): HomeViewState =
        HomeViewState.Default(
            this.coin,
            this.flupet,
            this.nextFullnessUpdateTime,
            this.nextHealthUpdateTime,
            "",
            FlupetStatus.None
        )

    private suspend fun getNewEgg() {
        getNewEggUseCase().fold(
            onSuccess = {
                setState { showNewEgg(it) }
            },
            onFailure = {
            }
        )
    }

    private fun HomeViewState.updateFullness(fullnessUpdateInfo: FullnessUpdateInfo):
        HomeViewState =
        when (this) {
            is HomeViewState.Default -> this.copy(
                nextFullnessUpdateTime = fullnessUpdateInfo.nextUpdateTime,
                flupet = this.flupet.copy(
                    fullness = fullnessUpdateInfo.fullness,
                    evolutionAvailable = fullnessUpdateInfo.isEvolutionAvailable
                ),
                flupetStatus = fullnessUpdateInfo.flupetStatus
            )

            is HomeViewState.FlupetNameEdit -> this.copy(
                nextFullnessUpdateTime = fullnessUpdateInfo.nextUpdateTime,
                flupet = this.flupet.copy(
                    fullness = fullnessUpdateInfo.fullness,
                    evolutionAvailable = fullnessUpdateInfo.isEvolutionAvailable
                ),
                flupetStatus = fullnessUpdateInfo.flupetStatus
            )
        }

    private fun HomeViewState.updateHealth(healthUpdateInfo: HealthUpdateInfo): HomeViewState =
        when (this) {
            is HomeViewState.Default -> this.copy(
                nextHealthUpdateTime = healthUpdateInfo.nextUpdateTime,
                flupet = this.flupet.copy(
                    health = healthUpdateInfo.health,
                    evolutionAvailable = healthUpdateInfo.isEvolutionAvailable
                ),
                flupetStatus = healthUpdateInfo.flupetStatus
            )

            is HomeViewState.FlupetNameEdit -> this.copy(
                nextHealthUpdateTime = healthUpdateInfo.nextUpdateTime,
                flupet = this.flupet.copy(
                    health = healthUpdateInfo.health,
                    evolutionAvailable = healthUpdateInfo.isEvolutionAvailable
                ),
                flupetStatus = healthUpdateInfo.flupetStatus
            )
        }

    private fun getMainUIInfo() {
        viewModelScope.launch {
            getMainUIInfoUseCase().fold(
                onSuccess = {
                    healthUpdateState.tryEmit(
                        HealthUpdateInfo(
                            it.flupet.health,
                            it.nextHealthUpdateTime,
                            it.flupet.evolutionAvailable,
                            it.flupetStatus
                        )
                    )
                    fullnessUpdateState.tryEmit(
                        FullnessUpdateInfo(
                            it.flupet.fullness,
                            it.nextFullnessUpdateTime,
                            it.flupet.evolutionAvailable,
                            it.flupetStatus
                        )
                    )
                    setState {
                        showMainUIInfo(it)
                    }
                },
                onFailure = {}
            )
        }
    }

    private fun showMainUIInfo(mainUIModel: MainUIModel): HomeViewState =
        HomeViewState.Default(
            coin = mainUIModel.coin,
            flupet = mainUIModel.flupet,
            nextFullnessUpdateTime = mainUIModel.nextFullnessUpdateTime,
            nextHealthUpdateTime = mainUIModel.nextHealthUpdateTime,
            flupetStatus = mainUIModel.flupetStatus
        )

    private fun HomeViewState.showNewEgg(mainUIModel: MainUIModel): HomeViewState =
        HomeViewState.Default(
            coin = this.coin,
            flupet = mainUIModel.flupet,
            nextFullnessUpdateTime = mainUIModel.nextFullnessUpdateTime,
            nextHealthUpdateTime = mainUIModel.nextHealthUpdateTime,
            flupetStatus = mainUIModel.flupetStatus
        )

    private fun HomeViewState.onStartEditName(): HomeViewState =
        when (this) {
            is HomeViewState.Default -> HomeViewState.FlupetNameEdit(
                coin = this.coin,
                flupet = this.flupet,
                nextFullnessUpdateTime = this.nextFullnessUpdateTime,
                nextHealthUpdateTime = this.nextHealthUpdateTime,
                message = this.message,
                flupetStatus = this.flupetStatus
            )

            is HomeViewState.FlupetNameEdit -> this
        }

    private fun HomeViewState.onSuccessEditFlupetNickname(name: String): HomeViewState =
        when (this) {
            is HomeViewState.Default -> this
            is HomeViewState.FlupetNameEdit -> HomeViewState.Default(
                coin = this.coin,
                flupet = this.flupet.copy(name = name),
                nextFullnessUpdateTime = this.nextFullnessUpdateTime,
                nextHealthUpdateTime = this.nextHealthUpdateTime,
                flupetStatus = this.flupetStatus,
                message = "플러펫 닉네임이 변경되었습니다"
            )
        }

    private suspend fun onConfirmName(name: String) {
        editFlupetNicknameUseCase(name).fold(
            onSuccess = {
                setState { onSuccessEditFlupetNickname(name) }
            },
            onFailure = {
                setState { onUpdateMessage(it.message.toString()) }
            }
        )
    }

    private fun HomeViewState.onUpdateMessage(message: String): HomeViewState =
        when (this) {
            is HomeViewState.Default -> this.copy(message = message)
            is HomeViewState.FlupetNameEdit -> this.copy(message = message)
        }

    private suspend fun enqueueNewRequest(tag: String, nextUpdateTime: Long) {
        when (tag) {
            "fullness" -> {
                if (::updateFullnessJob.isInitialized) {
                    updateFullnessJob.cancel()
                    updateFullnessJob.join()
                }
                updateFullnessJob = viewModelScope.launch(Dispatchers.IO) {
                    val delayTime = nextUpdateTime - System.currentTimeMillis()
                    if (delayTime > 0) {
                        delay(delayTime)
                        updateFullnessUseCase().fold(
                            onSuccess = { fullnessUpdateState.emit(it) },
                            onFailure = {}
                        )
                    }
                }
            }

            "health" -> {
                if (::updateHealthJob.isInitialized) {
                    updateHealthJob.cancel()
                    updateHealthJob.join()
                }
                updateHealthJob = viewModelScope.launch(Dispatchers.IO) {
                    val delayTime = nextUpdateTime - System.currentTimeMillis()
                    if (delayTime > 0) {
                        delay(delayTime)
                        updateHealthUseCase().fold(
                            onSuccess = { healthUpdateState.emit(it) },
                            onFailure = {}
                        )
                    }
                }
            }
        }
    }
}
