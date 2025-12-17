package com.trishala.cvs.usecase

import com.trishala.cvs.domain.model.CharacterRmModel
import com.trishala.cvs.domain.repository.CharacterRepository
import com.trishala.cvs.domain.usecase.search.SearchCharacterOfRmUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCharacterOfRmUseCaseTest {

    private val repository: CharacterRepository = mockk(relaxed = true)
    private val useCase = SearchCharacterOfRmUseCase(repository)

    @Test
    fun `blank name returns empty list and does not call repository`() = runTest {
        val result: List<CharacterRmModel> = useCase(name = "   ").first()

        assertTrue(result.isEmpty())

        verify(exactly = 0) { repository.searchCharacters(any(), any(), any(), any()) }
    }

    @Test
    fun `valid name returns repository list`() = runTest {
        val fake = listOf(
            CharacterRmModel(
                id = 1,
                name = "Rick Sanchez",
                imageUrl = "img",
                species = "Human",
                status = "Alive",
                origin = "Earth",
                type = null,
                createdAt = "date"
            )
        )

        every { repository.searchCharacters("Rick", null, null, null) } returns flowOf(fake)

        val result = useCase(name = "Rick").first()

        assertEquals(fake, result)
        verify(exactly = 1) { repository.searchCharacters("Rick", null, null, null) }
    }
}
