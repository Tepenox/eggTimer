package com.example.anass.eggalarm;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.Duration;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerButton;
    CountDownTimer timer;

    public void onButtonClick(View view) {
        timer.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerButton = findViewById(R.id.timerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerTextView.setText("00:30");


        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTimerText(progress * 1000);
                changeInstanceTimer(progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void changeInstanceTimer(final int milliseconds) {
        timer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTimerText((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {

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
