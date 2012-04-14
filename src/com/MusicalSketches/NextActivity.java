package com.MusicalSketches;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NextActivity extends Activity {

	// Your member variable declaration here

	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent i = getIntent();
		String title = (String) i.getExtras().get("title");
				
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(title);
	}
}