package com.example.anass.eggalarm;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerButton;
    CountDownTimer timer;
    MediaPlayer mp;
    int timeLeft;
    // 0 stopped  1 active 2 paused 3 done
    int timerState = 0;


    public void onButtonClick(View view) {
        if (timerState == 0 || timerState == 2) {
            timer.start();
            timerButton.setText("pause");
            timerState = 1;
            timerSeekBar.setEnabled(false);
        } else if (timerState == 1 || timerState == 3) {
            if (timerState != 3) {
                //pausing the timer here
                timer.cancel();
                setTimer(timeLeft);
                timerButton.setText("resume");
                timerState = 2;
            } else {
                timer.cancel();
                mp.stop();
                timerSeekBar.setEnabled(true);
                timerState = 0;
                timerSeekBar.setProgress(30);

            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerButton = findViewById(R.id.timerStartButton);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerTextView.setText("00:30");

        setTimer(30000);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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
                timerButton.setText("stop");
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
