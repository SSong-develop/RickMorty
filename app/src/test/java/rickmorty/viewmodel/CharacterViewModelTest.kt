package rickmorty.viewmodel

import com.nhaarman.mockitokotlin2.mock
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.network.service.CharacterService
import com.ssong_develop.rickmorty.persistence.CharacterDao
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.ui.character.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharacterViewModelTest {

    private lateinit var viewModel : CharacterViewModel
    private lateinit var repository : CharacterRepository
    private val service : CharacterService = mock()
    private val client = CharacterClient(service)
    private val characterDao : CharacterDao = mock()

    @Before
    fun setup(){
        repository = CharacterRepository(client,characterDao,Dispatchers.IO)
        viewModel = CharacterViewModel(repository)
    }

    @Test
    fun viewModelTest(){
        assertEquals(viewModel.testValue , 1)
    }
}