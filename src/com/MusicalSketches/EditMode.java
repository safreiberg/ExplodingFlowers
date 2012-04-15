package com.MusicalSketches;

import com.MusicalSketches.datarep.NoteFrequencies;
import com.MusicalSketches.datarep.Song;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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
}
