package com.trishala.cvs.presentation.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trishala.cvs.domain.usecase.search.SearchCharacterOfRmUseCase
import com.trishala.cvs.presentation.features.search.mvi.Reducer
import com.trishala.cvs.presentation.features.search.mvi.SearchAction
import com.trishala.cvs.presentation.features.search.mvi.SearchIntent
import com.trishala.cvs.presentation.features.search.mvi.SearchParams
import com.trishala.cvs.presentation.features.search.mvi.SearchResult
import com.trishala.cvs.presentation.features.search.mvi.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchCharacterOfRmUseCase
) : ViewModel() {

    private val intents = MutableSharedFlow<SearchIntent>(extraBufferCapacity = 64)

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    fun onIntent(intent: SearchIntent) {
        intents.tryEmit(intent)
    }

    init {
        observeAutoSearch()
    }

    @OptIn(FlowPreview::class)
    private fun observeAutoSearch() {
        viewModelScope.launch {
            intents

                .onEach { intent ->
                    when (intent) {
                        is SearchIntent.QueryChanged ->
                            _state.update { it.copy(query = intent.value) }

                        is SearchIntent.StatusChanged ->
                            _state.update { it.copy(status = intent.value) }

                        is SearchIntent.SpeciesChanged ->
                            _state.update { it.copy(species = intent.value) }

                        is SearchIntent.TypeChanged ->
                            _state.update { it.copy(type = intent.value) }

                        SearchIntent.ClearError ->
                            _state.update { it.copy(error = null) }
                    }
                }

                .map {
                    val s = _state.value
                    val trimmed = s.query.trim()

                    if (trimmed.isBlank()) {
                        SearchAction.ClearResults
                    } else {
                        SearchAction.ExecuteSearch(
                            SearchParams(
                                name = trimmed,
                                status = s.status,
                                species = s.species,
                                type = s.type
                            )
                        )
                    }
                }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { action -> execute(action) }
                .collect { result ->
                    _state.update { old -> Reducer.reduce(old, result) }
                }
        }
    }
    private fun execute(action: SearchAction): Flow<SearchResult> =
        when (action) {

            SearchAction.ClearResults ->
                flowOf<SearchResult>(SearchResult.Cleared)

            is SearchAction.ExecuteSearch ->
                searchUseCase(
                    name = action.params.name,
                    status = action.params.status,
                    species = action.params.species,
                    type = action.params.type
                )
                    .map { list -> SearchResult.Success(data = list) as SearchResult }
                    .onStart { emit(SearchResult.Loading) }
                    .catch { e -> emit(SearchResult.Error(e.message ?: "Something went wrong")) }
        }



}
