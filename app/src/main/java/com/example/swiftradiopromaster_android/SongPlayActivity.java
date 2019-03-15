package com.example.swiftradiopromaster_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SongPlayActivity extends AppCompatActivity {
    private boolean playPause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);
        this.setTitle(getIntent().getStringExtra("title"));
        playPause = false;
    }

    public void playSong(View view) {

        Log.d("tag","Play button clicked" + view.getContentDescription());
        if (playPause == false) {
            ImageView image = (ImageView) findViewById(R.id.playItem);
            image.setImageResource(R.drawable.pause);
            playPause = true;
        }
        else if(playPause){
            ImageView image = (ImageView) findViewById(R.id.playItem);
            image.setImageResource(R.drawable.play);
            playPause = false;
        }
    }
}
