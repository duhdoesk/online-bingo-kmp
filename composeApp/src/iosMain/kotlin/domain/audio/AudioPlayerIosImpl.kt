@file:OptIn(ExperimentalResourceApi::class)

package domain.audio

import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import util.Log

class AudioPlayerIosImpl : AudioPlayer {

    private lateinit var player: AVAudioPlayer

    override val isPlaying: Boolean
        get() = player.isPlaying()

    override fun playNew(filePath: String) {
        try {
            val media = NSURL.URLWithString(URLString = filePath) ?: return
            player = AVAudioPlayer(media, error = null)
            player.setNumberOfLoops(Long.MAX_VALUE)
            player.prepareToPlay()
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
            player.finalize()
        } catch (e: Exception) {
            logError(e)
        }
    }

    private fun logError(exception: Throwable) {
        Log.e(
            message = "AudioPlayerIosImpl: ${exception.message}",
            throwable = exception,
            tag = AudioPlayerIosImpl::class.simpleName.toString()
        )
    }
}
