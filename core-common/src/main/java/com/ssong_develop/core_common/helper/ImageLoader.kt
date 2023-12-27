package com.ssong_develop.core_common.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_common.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class ImageLoader @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val cache = LruCache<String, Bitmap>(5)

    suspend fun getImage(url: String, block: (Bitmap?) -> Unit) {
        if (url.isEmpty()) {
            block(null)
            return
        }

        cache.get(url)?.let {
            block(it)
            return
        }

        withContext(ioDispatcher) {
            runCatching {
                val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
                cache.put(url, bitmap)
                bitmap
            }.onSuccess {
                withContext(mainDispatcher) {
                    block(it)
                }
            }.onFailure {
                withContext(mainDispatcher) {
                    block(null)
                }
            }
        }
    }
}