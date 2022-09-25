package rickmorty.repository

import com.nhaarman.mockitokotlin2.mock
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.persistence.CharacterDao
import com.ssong_develop.core_data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterRepositoryTest {

    private lateinit var repository: CharacterRepository
    private val client: CharacterClient = mock()
    private val characterDao: CharacterDao = mock()

    @Before
    fun setup() {
        repository = CharacterRepository(client, characterDao, Dispatchers.IO)
    }

    @Test
    fun repositoryTest() {
        assertEquals(repository.testValue, 1)
    }
}