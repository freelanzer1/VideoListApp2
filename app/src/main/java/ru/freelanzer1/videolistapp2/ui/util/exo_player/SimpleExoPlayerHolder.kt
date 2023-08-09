package ru.freelanzer1.videolistapp2.ui.util.exo_player

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer

object SimpleExoPlayerHolder {
    private var exoplayer: ExoPlayer? = null

    fun get(context: Context): ExoPlayer {
        if (exoplayer == null) {
            exoplayer = createExoPlayer(context)
        }
        return exoplayer!!
    }

    private fun createExoPlayer(context: Context): ExoPlayer {
        return ExoPlayer.Builder(context)
//            .setLoadControl(
//                DefaultLoadControl.Builder().setBufferDurationsMs(
//                    DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
//                    DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
//                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS / 10,
//                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS / 10
//                ).build()
//            )
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_ONE
            }
    }
}
