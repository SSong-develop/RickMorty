package rickmorty.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ssong_develop.rickmorty.network.service.CharacterService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Before
import retrofit2.Retrofit

@ExperimentalSerializationApi
class CharacterServiceTest {

    private lateinit var service: CharacterService

    private val json by lazy {
        Json { coerceInputValues = true }
    }

    @Before
    fun initService() {
        service = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .build()
            .create(CharacterService::class.java)
    }

}