package com.example.blockfyra

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

package com.example.blockfyra

class MainActivity : AppCompatActivity() {
    var eGuitar: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eGuitar = MediaPlayer.create(this, R.raw.music1)
        val switch_looping = findViewById<View>(R.id.loopSwitch) as Switch
        switch_looping.setOnCheckedChangeListener { buttonView, isChecked ->
            eGuitar.setLooping(isChecked)
            //eGuitar.seekTo(eGuitar.getCurrentPosition()+1000);
        }
    }

    fun playMusic(v: View?) {
        eGuitar!!.start()

        //eGuitar.seekTo(eGuitar.getCurrentPosition()+1000);
    }

    fun pauseMusic(v: View?) {
        if (eGuitar!!.isPlaying) eGuitar!!.stop()
    }
}