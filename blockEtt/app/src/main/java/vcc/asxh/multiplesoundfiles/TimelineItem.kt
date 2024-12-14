package vcc.asxh.multiplesoundfiles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TimelineItem data class
data class TimelineItem(val audioResourceId: Int, var duration: Int = 0, var currentPosition: Int = 0)

// RecyclerView Adapter
class TimelineAdapter(private val context: Context, private val recyclerView: RecyclerView, private val timelineItems: MutableList<TimelineItem>) :
    RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    val uiUpdateScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    val players = mutableMapOf<Int, ExoPlayer>() // Store players by item position

    inner class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timelineView: AudioTimelineView = itemView.findViewById(R.id.timelineView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.timeline_item, parent, false)
        return TimelineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val timelineItem = timelineItems[position]
      //  holder.timelineView.setAudioFileDuration(timelineItem.duration)
     //   holder.timelineView.updateCurrentPosition(timelineItem.currentPosition)

        // Initialize player if not already created
      /*  if (!players.containsKey(position)) {
            val player = ExoPlayer.Builder(context).build()
            val mediaItem = MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(timelineItem.audioResourceId))
            player.setMediaItem(mediaItem)
            player.prepare()
            players[position] = player

            // Start playback and UI updates
            player.playWhenReady = true
            player.repeatMode = Player.REPEAT_MODE_ALL

            uiUpdateScope.launch {
                while (player.isPlaying) {
                    val currentPositionInSeconds = (player.currentPosition / 1000).toInt()
                    withContext(Dispatchers.Main) {
                        timelineItems[position].currentPosition = currentPositionInSeconds
                        holder.timelineView.updateCurrentPosition(currentPositionInSeconds)
                    }
                    delay(100) // Update every 100ms
                }
            }

            player.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if (!isPlaying) {
                        // Stop UI updates when playback stops
                        uiUpdateScope.cancel()
                    }
                }
            })
        } */

        // Initialize players and durations in the adapter
         for (i in timelineItems.indices) {
             val player = ExoPlayer.Builder(context).build()
             val mediaItem = MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(timelineItems[i].audioResourceId))
             player.setMediaItem(mediaItem)
             player.prepare()

             timelineItems[i].duration = (player.duration / 1000).toInt()
             players[i] = player // Store player in adapter's players map

             // Start playback and UI updates (similar to previous code)
             player.playWhenReady = true
             player.repeatMode = Player.REPEAT_MODE_ALL

             // In TimelineAdapter
             uiUpdateScope.launch {
                 while (player.isPlaying) {
                     val currentPositionInSeconds = (player.currentPosition / 1000).toInt()
                     withContext(Dispatchers.Main) {
                         // Directly update the TimelineView
                         val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? TimelineViewHolder
                      //   viewHolder?.timelineView?.updateCurrentPosition(currentPositionInSeconds)
                     }
                     delay(100) // Update every 100ms
                 }
             }

             player.addListener(object : Player.Listener {
                 override fun onIsPlayingChanged(isPlaying: Boolean) {
                     super.onIsPlayingChanged(isPlaying)
                     if (!isPlaying) {
                         uiUpdateScope.cancel()
                     }
                 }
             })
         }
    }

    override fun getItemCount(): Int = timelineItems.size
}