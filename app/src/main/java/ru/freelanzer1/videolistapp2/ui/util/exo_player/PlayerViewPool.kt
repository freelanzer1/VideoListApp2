package ru.freelanzer1.videolistapp2.ui.util.exo_player

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.core.util.Pools
import androidx.media3.*
import androidx.media3.ui.PlayerView
import ru.freelanzer1.videeolistapp2.R

object PlayerViewPool {
    @SuppressLint("StaticFieldLeak")
    var currentPlayerView: PlayerView? = null

    private val playerViewPool = Pools.SimplePool<PlayerView>(2)

    fun get(context: Context): PlayerView {
        return playerViewPool.acquire() ?: createPlayerView(context)
    }

    fun release(player: PlayerView) {
        playerViewPool.release(player)
    }

    @SuppressLint("InflateParams")
    private fun createPlayerView(context: Context): PlayerView {
        return (
                LayoutInflater.from(context)
                    .inflate(R.layout.player_view, null, false) as PlayerView
                )
    }
}