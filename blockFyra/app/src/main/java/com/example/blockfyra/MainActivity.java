package com.example.blockfyra;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    MediaPlayer eGuitar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        eGuitar = MediaPlayer.create(this, R.raw.music1) ;
        Switch switch_looping = (Switch)  findViewById(R.id.loopSwitch);
        switch_looping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                eGuitar.setLooping(isChecked);
                //eGuitar.seekTo(eGuitar.getCurrentPosition()+1000);
            }
        });
    }
    public void playMusic (View v){
        eGuitar.start();
        //eGuitar.seekTo(eGuitar.getCurrentPosition()+1000);

    }
    public void pauseMusic (View v){
        if(eGuitar.isPlaying())
            eGuitar.stop();
    }
}