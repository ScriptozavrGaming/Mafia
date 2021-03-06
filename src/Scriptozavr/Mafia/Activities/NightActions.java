package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.Utilities;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NightActions extends Activity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private int currentCircle;
    private Map<Button, Player> killButtonToPlayerMap = new HashMap<Button, Player>();
    private Map<Button, Integer> pressedButtonToIntegerMap = new HashMap<Button, Integer>();
    private ArrayList<Integer> playersForLastMinute = new ArrayList<Integer>();
    private Button chosenKillButton;
    private int chosenButton = -1;
    private boolean keyPressed = false;
    final String LOG_TAG = "myLogs";
    final String DATA_SD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();


    MediaPlayer mediaPlayer;
    AudioManager am;

    private SeekBar songStatusBar;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private Utilities utils;
    private Handler mHandler = new Handler();
    private boolean audioPlayerIsInit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullnight);

        initAudioPlayer();

        final Button[] killButtons = {
                (Button) findViewById(R.id.killButton1),
                (Button) findViewById(R.id.killButton2),
                (Button) findViewById(R.id.killButton3),
                (Button) findViewById(R.id.killButton4),
                (Button) findViewById(R.id.killButton5),
                (Button) findViewById(R.id.killButton6),
                (Button) findViewById(R.id.killButton7),
                (Button) findViewById(R.id.killButton8),
                (Button) findViewById(R.id.killButton9),
                (Button) findViewById(R.id.killButton10),
        };

        final Parcelable[] players = getIntent().getParcelableArrayExtra("players");
        setSongStatusBar();

        currentCircle = getIntent().getIntExtra("currentCircle", 0);
        for (int i = 0; i < killButtons.length; i++) {
            killButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!keyPressed) {
                        chosenKillButton = (Button) findViewById(v.getId());
                        chosenKillButton.setBackgroundColor(Color.RED);
                        chosenButton = pressedButtonToIntegerMap.get(chosenKillButton);
                        for (int i = 0; i < killButtons.length; i++) {
                            if (i != chosenButton) {
                                killButtons[i].setClickable(false);
                            }
                        }
                        keyPressed = !keyPressed;
                    } else {
                        chosenKillButton.setBackgroundColor(Color.GREEN);
                        for (int i = 0; i < killButtons.length; i++) {
                            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                                killButtons[i].setClickable(true);
                            }
                        }
                        chosenButton = -1;
                        keyPressed = !keyPressed;
                    }
                }
            });
        }
        findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCircle++;
                if (chosenButton != -1) {
                    ((Player) players[chosenButton]).setStatus(getResources().getString(R.string.status_killed));
                    playersForLastMinute.add(chosenButton);
                    Intent LastMinute = new Intent(getApplicationContext(), Scriptozavr.Mafia.Activities.LastMinute.class);
                    LastMinute.putExtra("ifKilled", true);
                    LastMinute.putExtra("players", players);
                    LastMinute.putExtra("currentCircle", currentCircle);
                    LastMinute.putIntegerArrayListExtra("lastMin", playersForLastMinute);
                    startActivity(LastMinute);
                } else {
                    Intent MorningActions = new Intent(getApplicationContext(), MorningActions.class);
                    MorningActions.putExtra("currentCircle", currentCircle);
                    MorningActions.putExtra("players", players);
                    startActivity(MorningActions);
                }
                if(audioPlayerIsInit) {
                    mediaPlayer.stop();
                    mHandler.removeCallbacks(mUpdateTimeTask);
                }
                //mUpdateTimeTask;
                finish();
            }
        });

        for (int i = 0; i < players.length; i++) {
            if (!((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                killButtons[i].setBackgroundColor(Color.GRAY);
                killButtons[i].setClickable(false);
            } else {
                killButtons[i].setBackgroundColor(Color.GREEN);
            }
            killButtonToPlayerMap.put(killButtons[i], (Player) players[i]);
            pressedButtonToIntegerMap.put(killButtons[i], i);
        }
    }

    protected void setSongStatusBar() {
        songStatusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(audioPlayerIsInit) {
                    mHandler.removeCallbacks(mUpdateTimeTask);
                    int totalDuration = mediaPlayer.getDuration();
                    int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                    // forward or backward to certain seconds
                    mediaPlayer.seekTo(currentPosition);

                    // update timer progress again
                    updateProgressBar();
                }
            }
        });
    }

    //work with Audio
    private void initAudioPlayer() {
        final SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(this);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        seekbar.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekbar.setProgress(seekbar.getMax() / 2);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, seekbar.getProgress(), 0);

        //----------------Status of Song-----------
        songStatusBar = (SeekBar) findViewById(R.id.songProgressBar);
        //songStatusBar.setOnSeekBarChangeListener(this);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        utils = new Utilities();
        //-----------------------------------------
    }

    public void onClickDefaultSongs(View view) {
        releaseMP();

        try {
            switch (view.getId()) {
                case R.id.btnStartSD:
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_SD);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    audioPlayerIsInit = true;
                    break;
                case R.id.btnStartRaw:
                    mediaPlayer = MediaPlayer.create(this, R.raw.track1);
                    songStatusBar.setProgress(0);
                    songStatusBar.setMax(100);
                    audioPlayerIsInit = true;
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mediaPlayer == null)
            return;

        mediaPlayer.setOnCompletionListener(this);
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickPlay(View view) {
        if (mediaPlayer == null)
            return;
        switch (view.getId()) {

            case R.id.btnPlay:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ((ImageButton) findViewById(R.id.btnPlay)).setImageResource(R.drawable.play);
                }
                else {
                    mediaPlayer.start();
                    ((ImageButton)findViewById(R.id.btnPlay)).setImageResource(R.drawable.pause);
                }
                updateProgressBar();
                break;
            //if needed stop button!!!
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "onPrepared");
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(LOG_TAG, "onCompletion");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        am.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        am.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), 0);
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = utils.getProgressPercentage(currentDuration, totalDuration);
            //Log.d("Progress", ""+progress);
            songStatusBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }

    };
    //work with audio
}
