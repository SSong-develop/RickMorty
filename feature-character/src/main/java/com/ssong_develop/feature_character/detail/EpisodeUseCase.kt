package com.ssong_develop.feature_character.detail

import android.util.Log
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.Episode
import kotlinx.coroutines.*
import java.io.Closeable
import javax.inject.Inject


class EpisodeUseCase @Inject constructor(
    private val repository: CharacterRepository
) : Closeable {

    private var useCaseJob: Job = Job()
    private var useCaseCoroutineScope: CoroutineScope = CoroutineScope(useCaseJob)

    suspend fun getEpisode(
        episodeUrls: List<String>,
        startLoading: () -> Unit,
        stopLoading: () -> Unit
    ): List<Episode> = withContext(useCaseCoroutineScope.coroutineContext) {
        runCatching {
            startLoading()
            val deferredJob = async {
                withTimeout(20_000L) {
                    repository.getEpisodes(episodeUrls)
                }
            }
            deferredJob.await()
        }.getOrElse {
            emptyList()
        }
    }

    override fun close() {
        Log.d("ssong-develop","close invoke??")
        useCaseJob.cancel()
    }
}
