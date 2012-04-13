package com.MusicalSketches;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MusicalSketches extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello, Android. Welcome to Musical Sketches, brought to you by Exploding Flowers!");
        setContentView(tv);
    }
}