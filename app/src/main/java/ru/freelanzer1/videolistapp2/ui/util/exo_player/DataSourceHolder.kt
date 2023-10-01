//package ru.freelanzer1.videolistapp2.ui.util.exo_player
//
//import android.content.Context
//import androidx.media3.datasource.DataSource
//import androidx.media3.datasource.DefaultDataSource
//import androidx.media3.datasource.FileDataSource
//import androidx.media3.datasource.cache.CacheDataSink
//import androidx.media3.datasource.cache.CacheDataSource
//
//object DataSourceHolder {
//    private var cacheDataSourceFactory: CacheDataSource.Factory? = null
//    private var defaultDataSourceFactory: DataSource.Factory? = null
//
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    fun getCacheFactory(context: Context): CacheDataSource.Factory {
//        if (cacheDataSourceFactory == null) {
//            val simpleCache = CacheHolder.get(context)
//            val defaultFactory = getDefaultFactory(context)
//            cacheDataSourceFactory = CacheDataSource.Factory()
//                .setCache(simpleCache)
//                .setUpstreamDataSourceFactory(defaultFactory)
//                .setCacheReadDataSourceFactory(FileDataSource.Factory())
//                .setCacheWriteDataSinkFactory(
//                    CacheDataSink.Factory()
//                        .setCache(simpleCache)
//                        .setFragmentSize(CacheDataSink.DEFAULT_FRAGMENT_SIZE)
//                )
//        }
//
//        return cacheDataSourceFactory!!
//    }
//
//    private fun getDefaultFactory(context: Context): DataSource.Factory {
//        if (defaultDataSourceFactory == null) {
//            defaultDataSourceFactory = DefaultDataSource.Factory(context)
//        }
//        return defaultDataSourceFactory!!
//    }
//}