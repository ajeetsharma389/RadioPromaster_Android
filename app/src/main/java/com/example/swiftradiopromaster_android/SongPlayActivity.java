package com.example.swiftradiopromaster_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.io.IOException;
import android.os.AsyncTask;

import static com.example.swiftradiopromaster_android.MySingleton.finalArray;

public class SongPlayActivity extends AppCompatActivity {
    private boolean playPause;
    private  String streamUrl;
    private int clickedIndex;
    private int currentIndex;
    MediaPlayer mediaPlayer;
    MySingleton mySingleton;
    private ConstraintLayout parentLinearLayout;
    private boolean initialStage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);
        parentLinearLayout = (ConstraintLayout)findViewById(R.id.parent_Constraint_layout);
        this.setTitle(getIntent().getStringExtra("title"));
        streamUrl = getIntent().getStringExtra("songUrl");
        clickedIndex = getIntent().getIntExtra("clickedIndex",0);
        currentIndex = clickedIndex;
        mediaPlayer = new MediaPlayer();
        mySingleton = MySingleton.getInstance();



        playPause = false;
    }

    // When user click back button
    protected void onDestroy() {

        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
    public void addCustomView(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view = inflater.inflate(R.layout.transparentview, parentLinearLayout, false);
        parentLinearLayout.addView(view);
    }

    public void removeCustomView(){

        View toremoveView = findViewById(R.id.transparentView);
        parentLinearLayout.removeView(toremoveView);
    }

    public void playSong(View view) {

        Object objectCliked = finalArray.get(currentIndex);
        streamUrl = ((getJsonData) objectCliked).getStreamURL();
        Log.d("tag","streamUrl " + streamUrl);

        if (!playPause) {


            if (initialStage) {
                new Player().execute(streamUrl);
            } else {
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
            }
            playPause = true;

        }
        else {
            this.removeCustomView();
            Button button = findViewById(R.id.playItem);
            button.setBackgroundResource(R.drawable.play);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            playPause = false;
        }
    }

    public void playForward(View view) {
        Button button = findViewById(R.id.playItem);
        button.setBackgroundResource(R.drawable.play);

        currentIndex ++;
        playPause = false;
        initialStage = true;
        mediaPlayer.stop();
        mediaPlayer.reset();
        this.playSong(null);

    }

    public void playBackward(View view) {
        if(currentIndex < 0) return;
        Button button = findViewById(R.id.playItem);
        button.setBackgroundResource(R.drawable.play);
        currentIndex --;
        playPause = false;
        initialStage = true;
        mediaPlayer.stop();
        mediaPlayer.reset();
        this.playSong(null);
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                       // btn.setText("Launch Streaming");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;


            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Button button = findViewById(R.id.playItem);
            button.setBackgroundResource(R.drawable.pause);

            removeCustomView();
            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addCustomView();

//            progressDialog.setMessage("Buffering...");
//            progressDialog.show();
        }
    }


}
