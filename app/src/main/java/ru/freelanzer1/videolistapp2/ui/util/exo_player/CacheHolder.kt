//package ru.freelanzer1.videolistapp2.ui.util.exo_player
//
//import android.content.Context
//import androidx.media3.database.StandaloneDatabaseProvider
//import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
//import androidx.media3.datasource.cache.SimpleCache
//
//object CacheHolder {
//    private var cache: SimpleCache? = null
//    private val lock = Object()
//
//
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    fun get(context: Context): SimpleCache {
//        synchronized(lock) {
//            if (cache == null) {
//                val cacheSize = 20L * 1024 * 1024
//                val exoDatabaseProvider = StandaloneDatabaseProvider(context)
//
//                cache = SimpleCache(
//                    context.cacheDir,
//                    LeastRecentlyUsedCacheEvictor(cacheSize),
//                    exoDatabaseProvider
//                )
//            }
//        }
//        return cache!!
//    }
//}