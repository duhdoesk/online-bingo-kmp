@file:OptIn(ExperimentalResourceApi::class)

package domain.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import util.Log

class AudioPlayerAndroidImpl(context: Context) : AudioPlayer {

    private val player = ExoPlayer.Builder(context).build()

    init {
        player.prepare()
    }

    override val isPlaying: Boolean
        get() = player.isPlaying

    override fun playNew(filePath: String) {
        try {
            val media = MediaItem.fromUri(filePath)
            player.repeatMode = ExoPlayer.REPEAT_MODE_ONE
            player.setMediaItem(media)
            player.play()
        } catch (e: Exception) {
            logError(e)
        }
    }

    override fun play() {
        try {
            player.play()
        } catch (e: Exception) {
            logError(e)
        }
    }

    override fun pause() {
        try {
            player.pause()
        } catch (e: Exception) {
            logError(e)
        }
    }

    override fun stop() {
        try {
            player.stop()
        } catch (e: Exception) {
            logError(e)
        }
    }

    override fun release() {
        try {
            player.release()
        } catch (e: Exception) {
            logError(e)
        }
    }

    private fun logError(exception: Throwable) {
        Log.e(
            message = "AudioPlayerIosImpl: ${exception.message}",
            throwable = exception,
            tag = AudioPlayerAndroidImpl::class.simpleName.toString()
        )
    }
}
