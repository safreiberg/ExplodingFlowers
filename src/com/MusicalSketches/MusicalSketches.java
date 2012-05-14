package com.MusicalSketches;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MusicalSketches extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent next = new Intent(MusicalSketches.this,
						MusicalLibrary.class);
				startActivity(next);
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
	}
}