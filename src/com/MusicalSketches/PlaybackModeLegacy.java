package com.MusicalSketches;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MusicalSketches.EditMode.types;
import com.MusicalSketches.EditModeLegacy.states;
import com.MusicalSketches.datarep.Note;
import com.MusicalSketches.datarep.NoteFrequencies;
import com.MusicalSketches.datarep.Song;

public class PlaybackModeLegacy extends Activity {
	Song song;
	int notesOnScreen = 0;
	int state = 0;
	MediaPlayer mediaPlayer;
	ImageView arrow;
	int[] arrowLocations = new int[] { 110, 210, 310, 410, 510, 610 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playback_mode);

		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);

		song = (Song) getIntent().getSerializableExtra("song object");
		arrow = (ImageView)findViewById(R.id.arrow_indicator);
		addClefMeterKey(song);

		ImageButton play_pause = (ImageButton) findViewById(R.id.play_pause_button);
		ImageButton rewind_button = (ImageButton) findViewById(R.id.rewind_button);
		ImageButton forward_button = (ImageButton) findViewById(R.id.forward_button);

		play_pause.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ImageButton b = (ImageButton) findViewById(R.id.play_pause_button);
				if (state == 0) {
					state = 1;
					b.setImageResource(R.drawable.pause);
					mediaPlayer.start();
					playArrows();
				} else {
					state = 0;
					b.setImageResource(R.drawable.play);
					mediaPlayer.pause();
				}
			}
		});

		rewind_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
			}
		});

		forward_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
			}
		});

		uploadFromSong(song);
	}

	public void uploadFromSong(Song s) {
		int g = 0;
		ViewGroup group = (ViewGroup)findViewById(R.id.edit_layout);
		for (Note n : s.getNotes().getNotes()) {
			Log.d("", "adding note");
			double freq = n.getPitch();
			double l = n.getLength();
			String str = NoteFrequencies.freqToString.get(freq);
			Log.d("", str);
			str = str.substring(0, 2);
			Log.d("", str);
			double y = 0;
			for (int i = 0; i < NoteFrequencies.staff_notes.length; i++) {
				if (NoteFrequencies.staff_notes[i].equals(str)) {
					y = NoteFrequencies.staff_lines[i] - 30;
				}
			}
			if (l == 0.125) {
				ImageView img = new ImageView(getApplicationContext());
				img.setImageResource(R.drawable.eigth_note_transparent);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y;
				params.leftMargin = 20 + (g + 1) * 60;
				// remove any previous annotation there
				img.setLayoutParams(params);
				group.removeView(img);
				group.addView(img);
			} else if (l == 0.25) {
				ImageView img = new ImageView(getApplicationContext());
				img.setImageResource(R.drawable.quarter_note_transparent);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y;
				params.leftMargin = 20 + (g + 1) * 60;
				// remove any previous annotation there
				img.setLayoutParams(params);
				group.removeView(img);
				group.addView(img);
			} else if (l == 0.5) {
				ImageView img = new ImageView(getApplicationContext());
				img.setImageResource(R.drawable.half_note_transparent);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y;
				params.leftMargin = 20 + (g + 1) * 60;
				// remove any previous annotation there
				img.setLayoutParams(params);
				group.removeView(img);
				group.addView(img);
			}
			double x = 20 + (g + 1) * 60;
			Log.d("", "" + x);
			str = NoteFrequencies.freqToString.get(freq);
			Log.d("", "str value: " + str);
			if (str.endsWith("sharp")) {
				ImageView img = new ImageView(getApplicationContext());
				img.setImageResource(R.drawable.sharp_transparent);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y - 30;
				params.leftMargin = 20 + (g + 1) * 60;
				// remove any previous annotation there
				img.setLayoutParams(params);
				group.addView(img);
			} else if (str.endsWith("flat")) {
				ImageView img = new ImageView(getApplicationContext());
				img.setImageResource(R.drawable.flat_transparent);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y - 30;
				params.leftMargin = 20 + (g + 1) * 60;
				// remove any previous annotation there
				img.setLayoutParams(params);
				group.addView(img);
			} else if (str.endsWith("natural")) {
				ImageView img = new ImageView(getApplicationContext());
				img.setImageResource(R.drawable.natural_transparent);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y - 30;
				params.leftMargin = 20 + (g + 1) * 60;
				// remove any previous annotation there
				img.setLayoutParams(params);
				group.removeView(img);
				group.addView(img);
			}
			g++;
		}
	}

	public enum playback_menu_options {
		CLOSE, EDIT,
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.playback_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.playback_close:
			Toast.makeText(this, "Bye!", Toast.LENGTH_SHORT).show();
			mediaPlayer.stop();
			finish();
			break;
		case R.id.playback_edit:
			Toast.makeText(this, "As you wish...", Toast.LENGTH_SHORT).show();
			mediaPlayer.stop();
			//Intent next = new Intent(PlaybackModeLegacy.this, EditModeLegacy.class);
			//next.putExtra("song object", song);
			//startActivity(next);
			finish();
			break;
		}
		return false;
	}

	public void addClefMeterKey(Song s) {
		int clef = s.getClef();
		if (clef == 1) {
			((ImageView) findViewById(R.id.clef_image))
					.setImageResource(R.drawable.treble_clef);
		}
		((TextView) findViewById(R.id.meter_text)).setText("" + s.getMeterTop()
				+ "\n" + s.getMeterBottom());
	}
	
	public void playArrows() {
	}
}
