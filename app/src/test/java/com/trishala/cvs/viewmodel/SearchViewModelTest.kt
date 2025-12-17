package com.trishala.cvs.viewmodel

import com.trishala.cvs.domain.model.CharacterRmModel
import com.trishala.cvs.domain.usecase.search.SearchCharacterOfRmUseCase
import com.trishala.cvs.presentation.features.search.mvi.SearchIntent
import com.trishala.cvs.presentation.features.search.viewmodel.SearchViewModel
import com.trishala.cvs.util.JsonTestUtil
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: SearchCharacterOfRmUseCase = mockk()

    @Test
    fun `QueryChanged triggers search and updates state`() = runTest {
        val fake :List<CharacterRmModel> = JsonTestUtil.loadJson("sample_character_response.json")

        every { useCase.invoke(name = "ric", status = null, species = null, type = null) } returns flowOf(fake)

        val vm = SearchViewModel(searchUseCase = useCase)
        vm.onIntent(SearchIntent.QueryChanged("ric"))
        advanceTimeBy(300)
        advanceUntilIdle()
        verify(exactly = 1) { useCase.invoke(name = "ric", status = null, species = null, type = null) }
        assertEquals("ric", vm.state.value.query)
        assertEquals(fake, vm.state.value.items)
    }
}
