package vcc.asxh.multiplesoundfiles

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vcc.asxh.multiplesoundfiles.AudioTimelineView
import vcc.asxh.multiplesoundfiles.R
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.text.toFloat
import kotlin.text.toLong
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toDuration

class MainActivity : AppCompatActivity() {

    lateinit var mediaPlayer1 : MediaPlayer
    lateinit var mediaPlayer2 : MediaPlayer
    lateinit var mediaPlayer3 : MediaPlayer
    lateinit var loopingPlayer1 : LoopingAudioPlayer
    lateinit var loopingPlayer2 : LoopingAudioPlayer
    lateinit var loopingPlayer3 : LoopingAudioPlayer
    lateinit var start : Button
    lateinit var stop : Button
    var randomValue by Delegates.notNull<Int>()
    lateinit var audioTimelineView1 : AudioTimelineView
    lateinit var audioTimelineView2 : AudioTimelineView
    lateinit var audioTimelineView3 : AudioTimelineView
    lateinit var positionTv1 : TextView
    lateinit var positionTv2 : TextView
    lateinit var positionTv3 : TextView
    var currentPositionInMillisP1 : Float = 0.0f
    var currentPositionInMillisP2 : Float = 0.0f
    var currentPositionInMillisP3 : Float = 0.0f
    var currentPositionP1 : Long = 0
    var currentPositionP2 : Long = 0
    var currentPositionP3 : Long = 0
    var durationInMillis : Long = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // val soundPlayer = MultiSoundExoPlayer(this) // 'this' is your Context
        audioTimelineView1 = findViewById<AudioTimelineView>(R.id.audioTimelineView1)
        audioTimelineView2 = findViewById<AudioTimelineView>(R.id.audioTimelineView2)
        audioTimelineView3 = findViewById<AudioTimelineView>(R.id.audioTimelineView3)
        positionTv1 = findViewById(R.id.tv1_position)
        positionTv2 = findViewById(R.id.tv2_position)
        positionTv3 = findViewById(R.id.tv3_position)
      //  val soundIds = listOf(R.raw.loop1, R.raw.loop2, R.raw.loop3)
        start = findViewById(R.id.start_button)
        stop = findViewById(R.id.stop_button)

     /*   mediaPlayer1 = MediaPlayer.create(this, R.raw.loop5)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.loop6)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.loop7) */

        start.setOnClickListener(View.OnClickListener {
            setAudio()
        })

        stop.setOnClickListener(View.OnClickListener {
         /*   mediaPlayer1.stop()
            mediaPlayer1.prepare()
            mediaPlayer2.stop()
            mediaPlayer2.prepare()
            mediaPlayer3.stop()
            mediaPlayer3.prepare() */

            loopingPlayer1.stop()
            loopingPlayer2.stop()
            loopingPlayer3.stop()

        })
        loopingPlayer1 = LoopingAudioPlayer(this@MainActivity, R.raw.loop1)
        loopingPlayer3 = LoopingAudioPlayer(this@MainActivity, R.raw.loop2)
        loopingPlayer2 = LoopingAudioPlayer(this@MainActivity, R.raw.loop3)
        durationInMillis = loopingPlayer1.getDuration() // Assuming you have getDuration()
        audioTimelineView1.setDuration(durationInMillis)

     /*   mediaPlayer1.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
           // setAudio()
         //   Log.d("TAG", mediaPlayer1.duration.toString())
            // Setup the visualizer for the audio session
           // val audioSessionId = mediaPlayer1.audioSessionId
            audioTimelineView1.setAudioFileDuration(mediaPlayer1.duration)
            audioTimelineView2.setAudioFileDuration(mediaPlayer2.duration)
            audioTimelineView3.setAudioFileDuration(mediaPlayer3.duration)

        }) */


    }

    fun setAudio(){
        try {
            /*  var duration = mediaPlayer1.duration
              randomValue = Random.nextInt(0, duration)
              mediaPlayer1.seekTo(randomValue)

              duration = mediaPlayer2.duration
              randomValue = Random.nextInt(0, duration)
              mediaPlayer2.seekTo(randomValue)

              duration = mediaPlayer3.duration
              randomValue = Random.nextInt(0, duration)
              mediaPlayer3.seekTo(randomValue)

              mediaPlayer1.start()
              mediaPlayer1.isLooping = true
              mediaPlayer2.start()
              mediaPlayer2.isLooping = true
              mediaPlayer3.start()
              mediaPlayer3.isLooping = true */


            loopingPlayer1.start(true)


            loopingPlayer3.start(true)


            loopingPlayer2.start(true )

            /* audioTimelineView1.setAudioFileDuration(mediaPlayer1.duration)
               audioTimelineView2.setAudioFileDuration(mediaPlayer2.duration)
               audioTimelineView3.setAudioFileDuration(mediaPlayer3.duration)*/

          //  loopingPlayer.stop() // Stop playback

            val handler = Handler(Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                  //  looping.currentPosition()?.let { audioTimelineView1.updateCurrentPosition(it) }
                    currentPositionP1 = loopingPlayer1.getCurrentPositionInMillis()
                    audioTimelineView1.setProgress(currentPositionP1)
                 //   currentPositionInMillisP1 = (currentPositionP1.toFloat() / loopingPlayer1.sampleRate) * 1000
                  //  audioTimelineView1.updateCurrentPosition(currentPositionInMillisP1.toInt())

                    currentPositionP2 = loopingPlayer2.getCurrentPositionInMillis()
                    audioTimelineView2.setProgress(currentPositionP2)
                    currentPositionInMillisP2 = (currentPositionP2.toFloat() / loopingPlayer2.sampleRate) * 1000
                 //   audioTimelineView2.updateCurrentPosition(currentPositionInMillisP2.toInt())

                    currentPositionP3 = loopingPlayer3.getCurrentPositionInMillis()
                    audioTimelineView3.setProgress(currentPositionP3)
                    currentPositionInMillisP3 = (currentPositionP3.toFloat() / loopingPlayer3.sampleRate) * 1000
                 //   audioTimelineView3.updateCurrentPosition(currentPositionInMillisP3.toInt())

                    positionTv1.text = currentPositionP1.toInt().toString()
                    positionTv2.text = currentPositionInMillisP2.toInt().toString()
                    positionTv3.text = currentPositionInMillisP3.toInt().toString()
                    handler.postDelayed(this, 101) // Update every 100 milliseconds
                    // test comment
                }
            }

            // Update TimelineView progress periodically
          /*  val positionHandler = Handler(Looper.getMainLooper())
            val positionRunnable = object : Runnable {
                override fun run() {
                    val currentPositionInMillis = loopingPlayer1.getCurrentPositionInMillis()

                    // Handle looping: Reset progress to 0 when reaching the end
                    val progressInMillis = if (currentPositionInMillis >= durationInMillis) {
                        0 // Reset to beginning for looping
                    } else {
                        currentPositionInMillis
                    }

                    audioTimelineView1.setProgress(progressInMillis)

                    positionHandler.postDelayed(this, 100) // Update every 100 milliseconds
                }
            }
            positionHandler.post(positionRunnable) */


            handler.postDelayed(runnable, 100)
        } catch (e: Exception) {
            Log.e("Exception",e.printStackTrace().toString())
        }



    }

    override fun onDestroy() {
        super.onDestroy()
     /*   mediaPlayer1.release()
        mediaPlayer2.release()
        mediaPlayer3.release() */

        loopingPlayer1.stop()
        loopingPlayer2.stop()
        loopingPlayer3.stop()
    }

    override fun onStop() {
        super.onStop()
     /*   mediaPlayer1.release()
        mediaPlayer2.release()
        mediaPlayer3.release() */
    }
}