package domain.audio

interface AudioPlayer {

    /** Returns true if player is playing */
    val isPlaying: Boolean

    /** Plays a sound by id */
    fun playNew(filePath: String)

    /** Plays the current set sound */
    fun play()

    /** Pauses execution */
    fun pause()

    /** Stops execution */
    fun stop()

    /** Releases player */
    fun release()
}
