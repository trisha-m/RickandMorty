package com.trishala.cvs.presentation.features.details.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trishala.cvs.domain.usecase.character.IGetCharacterOfRmUseCase
import com.trishala.cvs.presentation.features.details.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetails: IGetCharacterOfRmUseCase
) : ViewModel() {

    private val intents = MutableSharedFlow<DetailsIntent>(extraBufferCapacity = 64)

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    private var lastId: Int? = null

    fun onIntent(intent: DetailsIntent) {
        intents.tryEmit(intent)
    }

    init {
        viewModelScope.launch {
            intents
                .map { intent ->
                    when (intent) {
                        is DetailsIntent.ScreenOpened -> {
                            lastId = intent.id
                            DetailsAction.Load(intent.id)
                        }
                        DetailsIntent.Retry -> DetailsAction.Load(lastId ?: -1)
                        DetailsIntent.ClearError -> null
                    }
                }
                .onEach { if (it == null) _state.update { s -> s.copy(error = null) } }
                .filterNotNull()
                .filter { it is DetailsAction.Load && it.id > 0 }
                .flatMapLatest { action -> execute(action) }
                .collect { result ->
                    _state.update { old -> DetailsReducer.reduce(old, result) }
                }
        }
    }

    private fun execute(action: DetailsAction): Flow<DetailsResult> =
        when (action) {
            is DetailsAction.Load ->
                getDetails(action.id)
                    .map { DetailsResult.Success(it) as DetailsResult }
                    .onStart { emit(DetailsResult.Loading) }
                    .catch { e -> emit(DetailsResult.Error(e.message ?: "Something went wrong")) }
        }
}
