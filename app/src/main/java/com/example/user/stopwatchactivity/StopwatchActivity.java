package com.example.user.stopwatchactivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }


        runTimer();

        //запустить секундомер при нажатии на Start
        Button start = findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                running = true;
            }
        });

        //остановить секундомер при нажатии на Stop
        Button stop = findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                running = false;
            }
        });

        //обнулить секундомер при нажатии на Reset
        Button reset = findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                running = false;
                seconds = 0;
            }
        });

    }
    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //отсчет времени приостановится, если активность приостановлена
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //снова запускается отсчет времени, если активность возобнавляется
    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

}
