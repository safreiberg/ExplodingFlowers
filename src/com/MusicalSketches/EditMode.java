package com.MusicalSketches;

import com.MusicalSketches.datarep.NoteFrequencies;
import com.MusicalSketches.datarep.Song;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class EditMode extends Activity {
	private Song song;
	int notesOnScreen = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_mode);

		ImageButton left_note = (ImageButton) findViewById(R.id.left_note);
		ImageButton middle_note = (ImageButton) findViewById(R.id.middle_note);
		ImageButton right_note = (ImageButton) findViewById(R.id.right_note);
		ImageButton more_notes = (ImageButton) findViewById(R.id.more_notes);
		Button rests_button = (Button) findViewById(R.id.rests_button);
		ImageButton record_button = (ImageButton) findViewById(R.id.record_button);
		Button dynamics_button = (Button) findViewById(R.id.dynamics_button);
		ImageButton slurs_button = (ImageButton) findViewById(R.id.slurs_button);
		ImageButton sharp_button = (ImageButton) findViewById(R.id.sharp_button);
		ImageButton flat_button = (ImageButton) findViewById(R.id.flat_button);
		ImageButton natural_button = (ImageButton) findViewById(R.id.natural_button);
		ImageView music_score = (ImageView) findViewById(R.id.music_score);

		Log.wtf("", "it's open");

		class ButtonTouchListener implements OnTouchListener {

			private int resourceID;
			private ImageView img;
			private View v;
			private int offsetX;
			private int offsetY;

			public ButtonTouchListener() {
				throw new RuntimeException("Unimplemented");
			}

			// draws the given resourceID
			public ButtonTouchListener(int resourceID, View v) {
				this.resourceID = resourceID;
				this.v = v;
				Log.d("", "image constructed from button");
			}

			@Override
			public boolean onTouch(View vi, MotionEvent event) {
				final int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					renewImage();
					((ViewGroup) findViewById(R.id.edit_layout)).addView(img);
					img.setX(event.getX() + offsetX);
					img.setY(event.getY() + offsetY);
					Log.d("",
							"action down: " + event.getX() + " " + event.getY());
					break;
				case MotionEvent.ACTION_UP:
					Log.d("", "action up: " + event.getX() + " " + event.getY());
					addNote(img);
					img = null;
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("",
							"action move: " + event.getX() + " " + event.getY());
					img.setX(event.getX() + offsetX);
					img.setY(event.getY() + offsetY);
					break;
				}
				return true;
			}

			public void renewImage() {
				img = new ImageView(getApplicationContext());
				this.offsetX = this.v.getLeft() - 15;
				this.offsetY = this.v.getTop() - 35;
				img.setImageResource(this.resourceID);
				img.setAdjustViewBounds(true);
				img.setMaxHeight(65);
				img.setMaxWidth(50);
				img.setVisibility(0);
			}
		}

		left_note.setOnTouchListener(new ButtonTouchListener(
				R.drawable.eigth_note_transparent, left_note));
		middle_note.setOnTouchListener(new ButtonTouchListener(
				R.drawable.quarter_note_transparent, middle_note));
		right_note.setOnTouchListener(new ButtonTouchListener(
				R.drawable.half_note_transparent, right_note));
		sharp_button.setOnTouchListener(new ButtonTouchListener(
				R.drawable.sharp_transparent, sharp_button));
		natural_button.setOnTouchListener(new ButtonTouchListener(
				R.drawable.natural_transparent, natural_button));
		flat_button.setOnTouchListener(new ButtonTouchListener(
				R.drawable.flat_transparent, flat_button));
		
		
	}

	public void addNote(View view) {
		class PlacedItemTouchListener implements OnTouchListener {
			//TODO figure out why the offsets are -23 and -140...
			private View v;
			private int noteNum;

			public PlacedItemTouchListener() {
				throw new RuntimeException("Unimplemented");
			}

			public PlacedItemTouchListener(View v, int noteNum) {
				this.v = v;
				this.noteNum = noteNum;
			}

			@Override
			public boolean onTouch(View vi, MotionEvent event) {
				final int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					this.v.setX(event.getRawX() - 23);
					this.v.setY(event.getRawY() - 140);
					Log.d("",
							"action down (modify): " + event.getRawX() + " " + event.getRawY());
					break;
				case MotionEvent.ACTION_UP:
					Log.d("", "action up (modify): " + event.getRawX() + " " + event.getRawY());
					updateNote(this.v, noteNum);
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("",
							"action move (modify): " + event.getRawX() + " " + event.getRawY());
					this.v.setX(event.getRawX()-23);
					this.v.setY(event.getRawY()-140);
					break;
				}
				return true;
			}
		}
		notesOnScreen += 1;
		view.setOnTouchListener(new PlacedItemTouchListener(view,notesOnScreen));
		Log.d("","Pitch is: "+getPitchFromYIndex(view.getY()));
		snapToBar(view);
		snapLeftRight(view,notesOnScreen);
	}
	
	public void updateNote(View v,int noteNum){
		Log.d("","Pitch is: "+getPitchFromYIndex(v.getY()));
		snapToBar(v);
		snapLeftRight(v,noteNum);
	}
	
	public double getPitchFromYIndex(float y) {
		if (191 >= y && y > 181) {
			return NoteFrequencies.getFrequency("e4"); 
		} else if (181>= y && y > 171) {
			return NoteFrequencies.getFrequency("f4"); 
		} else if (171 >= y && y > 161) {
			return NoteFrequencies.getFrequency("g4"); 
		} else if (161 >= y && y > 151) {
			return NoteFrequencies.getFrequency("a5"); 
		} else if (151 >= y && y > 141) {
			return NoteFrequencies.getFrequency("b5"); 
		} else if (141 >= y && y > 131) {
			return NoteFrequencies.getFrequency("c5"); 
		} else if (131 >= y && y > 121) {
			return NoteFrequencies.getFrequency("d5"); 
		} else if (121 >= y && y > 111) {
			return NoteFrequencies.getFrequency("e5"); 
		} else if (111 >= y && y > 101) {
			return NoteFrequencies.getFrequency("f5"); 
		}
		return -1;
	}
	
	public void snapToBar(View v) {
		double y = v.getY();
		if (y > 191) {
			v.setY(186);
		} else if (191 >= y && y > 181) {
			v.setY(186);
		} else if (181 >= y && y > 171) {
			v.setY(176); 
		} else if (171 >= y && y > 161) {
			v.setY(166); 
		} else if (161 >= y && y > 151) {
			v.setY(156); 
		} else if (151 >= y && y > 141) {
			v.setY(146); 
		} else if (141 >= y && y > 131) {
			v.setY(136); 
		} else if (131 >= y && y > 121) {
			v.setY(126); 
		} else if (121 >= y && y > 111) {
			v.setY(116); 
		} else if (111 >= y && y > 101) {
			v.setY(106); 
		} else if (y < 101) {
			v.setY(106); 
		}
	}
	
	public void snapLeftRight(View v, int noteNum) {
		v.setX(10 + 100*noteNum);
	}
	
	public enum edit_menu_options {
		UNDO,
		SAVE,
		CLOSE,
		PLAY,
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.edit_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_close:
			Toast.makeText(this, "Closing!", Toast.LENGTH_SHORT).show();
			save();
			finish();
			break;
		case R.id.edit_play:
			Toast.makeText(this, "Play...", Toast.LENGTH_SHORT).show();
			break;
		case R.id.edit_save:
			Toast.makeText(this, "Save...", Toast.LENGTH_SHORT).show();
			save();
			break;
		case R.id.edit_undo:
			Toast.makeText(this, "Undo...", Toast.LENGTH_SHORT).show();
			break;
		}
		return false;
	}
	
	public void save() {
		//TODO implement save
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
