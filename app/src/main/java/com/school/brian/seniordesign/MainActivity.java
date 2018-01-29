package com.school.brian.seniordesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public long time = 0;

    public long getTimeStamp(){
        return System.currentTimeMillis();
    }
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // grab time on press
            time = getTimeStamp();
            updateTextViewStart(time);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // grab tim eon release
            time = getTimeStamp();
            updateTextViewStop(time);
        }
        return false;
    }

    public void updateTextViewStart(long time) {
        TextView textView = findViewById(R.id.start);
        textView.setText(Long.toString(time));
    }

    public void updateTextViewStop(long time) {
        TextView textView = findViewById(R.id.stop);
        textView.setText(Long.toString(time));
    }

}


