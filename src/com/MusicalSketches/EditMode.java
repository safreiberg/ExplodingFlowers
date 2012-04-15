package com.MusicalSketches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class EditMode  extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_mode);
	}
	
	
	
	public int createClefDialog() {
		final CharSequence[] clefs = { "Treble", "Bass" };
		final int newClef = (Integer) null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Clef");
		builder.setItems(clefs, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				//Toast.makeText(getApplicationContext(), clefs[item], Toast.LENGTH_SHORT).show();
				// newClef = item; this doesn't work - need new way to know
				// return value. Could probably call setClef from here.
				
			}
		});

		AlertDialog alert = builder.create();
		return newClef;
	}

	public void createDynamicsDialog() {
		final CharSequence[] dynamics = { "pp", "p", "mp", "mf", "f", "ff" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Dynamic");
		builder.setItems(dynamics, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Toast.makeText(getApplicationContext(),
				// dynamics[item],Toast.LENGTH_SHORT).show();
				// newDynamic = item; this doesn't work - need new way to know
				// return value. Could probably call setDynamic from here.

			}
		});

		AlertDialog alert = builder.create();
		// return newDynamic;
	}

	public void createKeyDialog() {
		final CharSequence[] keys = { "C", "G", "D", "A", "E", "B", "F#",
				"Db", "Ab", "Eb", "Bb", "F", "A minor", "E minor", "B minor",
				"F# minor", "Db minor", "Ab minor", "Eb minor", "Bb minor",
				"F minor", "C minor", "G minor", "D minor" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Key");
		builder.setItems(keys, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Toast.makeText(getApplicationContext(),
				// dynamics[item],Toast.LENGTH_SHORT).show();
				// newDynamic = item; this doesn't work - need new way to know
				// return value. Could probably call setDynamic from here.

			}
		});

		AlertDialog alert = builder.create();
		// return newDynamic;
	}

	// TODO: this doesn't work! I need some way of grabbing the numbers and
	// turning them into ints. also,
	// should we add rules to keep entries within tempo ranges (40 to about 220)
	// public int createTempoDialog(int currentTempo) {
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setTitle("New Tempo");
	// final int newTempo = currentTempo;
	// builder.set
	//
	// alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// newTempo = input.getText();
	// return;
	// }
	// });
	//
	// alert.setNegativeButton("Cancel",
	// new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int which) {
	// return;
	// }
	// });
	// return newTempo;
	// }

	// public List <Integer> createMeterDialog(){
	// AlertDialog.Builder alert = new AlertDialog.Builder(this);
	// alert.setTitle("Choose New Meter");
	//
	// }
}
