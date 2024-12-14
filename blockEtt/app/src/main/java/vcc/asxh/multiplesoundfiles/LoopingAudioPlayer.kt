package vcc.asxh.multiplesoundfiles

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import kotlin.random.Random
import kotlin.random.nextInt

class LoopingAudioPlayer(private val context: Context, private val audioResId: Int) {

    private lateinit var audioTrack: AudioTrack
    private lateinit var audioData: ByteArray

    val sampleRate: Int
        get() = if (this::audioTrack.isInitialized) audioTrack.sampleRate else 0 // Handle uninitialized case

    fun start(randomStart: Boolean) {
        // Load audio data from resources
        val inputStream = context.resources.openRawResource(audioResId)
        audioData = inputStream.readBytes()
        inputStream.close()

        // Create AudioTrack instance
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val audioFormat = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT) // Adjust encoding if needed
            .setSampleRate(44100) // Adjust sample rate if needed
            .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO) // Adjust channel mask if needed
            .build()

        val bufferSize = AudioTrack.getMinBufferSize(
            audioFormat.sampleRate,
            audioFormat.channelMask,
            audioFormat.encoding
        )

        audioTrack = AudioTrack(
            audioAttributes,
            audioFormat,
            bufferSize,
            AudioTrack.MODE_STREAM,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        )

        // Start playback in a separate thread
        Thread {
            audioTrack.play()

            val frameSizeInBytes = audioFormat.channelCount * audioFormat.encoding // Calculate frame size
            var offset = if (randomStart) {
                val randomFrame = Random.nextInt(audioData.size / frameSizeInBytes) // Random frame index
                randomFrame * frameSizeInBytes // Align to frame boundary
            } else {
                0 // Start from the beginning
            }

            while (true) {
                val bytesWritten = audioTrack.write(audioData, offset, audioData.size - offset)
                offset += bytesWritten
                if (offset >= audioData.size) {
                    offset = 0 // Loop back to the beginning
                }
            }
        }.start()
        /*   Thread {
             audioTrack.play()
             Log.d("TAG_AUDIO", audioData.size.toString())
             val randomStart = Random.nextInt(0,audioData.size)
             Log.d("TAG_AUDIO_RANDOM", randomStart.toString())
             var offset = randomStart
           var offset = if (randomStart) {
                 Random.nextInt(0,audioData.size) // Random start position
             } else {
                 0 // Start from the beginning
             }

            while (true) {
                val bytesWritten = audioTrack.write(audioData, offset, audioData.size - offset)
                offset += bytesWritten
                if (offset >= audioData.size) {
                    offset = 0 // Loop back to the beginning
                }
            }
        }.start() */
    }

    fun stop() {
        audioTrack.stop()
        audioTrack.release()
    }

    fun getPlaybackPosition(): Int {
        if (this::audioTrack.isInitialized) { // Check if audioTrack is initialized
         //   Log.d("TAG_FRAMES_", audioTrack.playbackHeadPosition.toString())
            return audioTrack.playbackHeadPosition
        } else {
            return 0 // Or handle the case where audioTrack is not yet initialized
        }
    }

    fun getDuration(): Long {
        if (this::audioTrack.isInitialized) {
            // Calculate duration based on audio data size, sample rate, and channels
            val audioDataSizeInBytes = audioData.size
            val sampleRate = audioTrack.sampleRate
            val numChannels = audioTrack.channelCount
            val bytesPerSample = 2 // Assuming 16-bit PCM (adjust if needed)

            val durationInSeconds = audioDataSizeInBytes.toFloat() / (sampleRate * numChannels * bytesPerSample)
            return (durationInSeconds * 1000).toLong()
        } else {
            return 0 // Or handle uninitialized case
        }
    }

    fun getCurrentPositionInMillis(): Long {
        if (this::audioTrack.isInitialized) {
            val currentPositionInFrames = audioTrack.playbackHeadPosition
            return (currentPositionInFrames.toFloat() / audioTrack.sampleRate * 1000).toLong()
        } else {
            return 0 // Or handle uninitialized case
        }
    }
}