package com.example.swiftradiopromaster_android;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.media.MediaPlayer;
import android.media.AudioManager;
import java.io.IOException;

import static com.example.swiftradiopromaster_android.MySingleton.finalArray;

public class SongPlayActivity extends AppCompatActivity {
    private boolean playPause;
    private  String streamUrl;
    private int clickedIndex;
    private int currentIndex;
    MediaPlayer mediaPlayer;
    MySingleton mySingleton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);
        this.setTitle(getIntent().getStringExtra("title"));
        streamUrl = getIntent().getStringExtra("songUrl");
        clickedIndex = getIntent().getIntExtra("clickedIndex",0);
        currentIndex = clickedIndex;
        mediaPlayer = new MediaPlayer();
        mySingleton = MySingleton.getInstance();

        playPause = false;
    }


    public void playSong(View view) {

        Object objectCliked = finalArray.get(currentIndex);
        streamUrl = ((getJsonData) objectCliked).getStreamURL();
        Log.d("tag","streamUrl " + streamUrl);

        if (playPause == false) {
            Button button = findViewById(R.id.playItem);
            button.setBackgroundResource(R.drawable.pause);
            //ImageView image = findViewById(R.id.playItem);
            //image.setImageResource(R.drawable.pause);

            try{
                mediaPlayer.stop();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(streamUrl);//Write your location here
                mediaPlayer.prepare();


            }catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mediaPlayer.start();
            playPause = true;


        }
        else if(playPause){
           // ImageView image = findViewById(R.id.playItem);
           // image.setImageResource(R.drawable.play);
            mediaPlayer.pause();
            playPause = false;
        }
    }

    public void playForward(View view) {

        currentIndex ++;
        playPause = false;
       // playSong(View view);
        this.playSong(null);

    }

    public void playBackward(View view) {
    }
}
