package com.example.anass.eggalarm;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerStartButton;
    Button timerStopButton;
    CountDownTimer timer;
    MediaPlayer mp;
    int timeLeft;
    // 0 stopped  1 active 2 paused 3 done
    int timerState = 0;


    public void onButtonClick(View view) {
        if (!view.getTag().toString().equals("timerStopButton")) {
            if (timerState == 0 || timerState == 2) {
                timer.start();
                timerStartButton.setText("pause");
                timerState = 1;
                timerSeekBar.setEnabled(false);
                timerStopButton.setVisibility(View.INVISIBLE);
            } else if (timerState == 1 || timerState == 3) {
                if (timerState != 3) {
                    //pausing the timer here
                    timer.cancel();
                    setTimer(timeLeft);
                    timerStartButton.setText("resume");
                    timerStopButton.setVisibility(View.VISIBLE);
                    timerState = 2;
                } else {
                    //time has done and clicking
                    timer.cancel();
                    mp.pause();
                    mp.seekTo(0);
                    timerStartButton.setText("start");
                    timerSeekBar.setEnabled(true);
                    timerState = 0;
                    timerSeekBar.setProgress(900);

                }
            }
        }else {
            //stop button has been clicked
            timer.cancel();
            timerSeekBar.setEnabled(true);
            timerSeekBar.setProgress(900);
            setTimerText(900000);
            setTimer(900000);
            timerStopButton.setVisibility(View.INVISIBLE);
            timerStartButton.setText("start");
            timerState = 0;
            Log.i("m","Stop button has been clicked");
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerStartButton = findViewById(R.id.timerStartButton);
        timerStopButton = findViewById(R.id.stopTimerButton);
        timerStopButton.setVisibility(View.INVISIBLE);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.alarmsong);

        timerSeekBar.setMax(1800);
        timerSeekBar.setProgress(900);
        setTimerText(900000);
        setTimer(900000);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress<1){
                    progress = 1;
                }
                setTimerText(progress * 1000);
                setTimer(progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void setTimer(final int milliseconds) {
        timer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) millisUntilFinished;
                setTimerText((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mp.start();
                timerStartButton.setText("stop");
                timerState = 3;
            }

        };
    }

    private void setTimerText(int milliseconds) {
        int minutes = milliseconds / 1000 / 60;
        int seconds = milliseconds / 1000 - minutes * 60;
        String minutesText = Integer.toString(minutes);
        String secondsText = Integer.toString(seconds);
        if (minutes <= 9)
            minutesText = "0" + minutesText;
        if (seconds <= 9)
            secondsText = "0" + secondsText;

        String timerText = minutesText + ":" + secondsText;
        timerTextView.setText(timerText);

    }


}
