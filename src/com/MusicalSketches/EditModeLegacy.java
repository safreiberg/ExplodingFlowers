package com.MusicalSketches;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.MusicalSketches.SongSelect.MyOnItemSelectedListener;
import com.MusicalSketches.datarep.KeySignatures;
import com.MusicalSketches.datarep.Note;
import com.MusicalSketches.datarep.NoteFrequencies;
import com.MusicalSketches.datarep.Song;

public class EditModeLegacy extends Activity {
	enum placement_objects {
		eighth_note, quarter_note, half_note, sharp, flat, natural, eighth_rest, quarter_rest, half_rest, pp, p, mp, f, ff, mf
	}

	enum states {
		wait, selected_to_place
	}

	ViewGroup group;
	int offset_left = 80;
	ImageButton left_note;
	TextView tempo_text;
	ImageButton middle_note;
	ImageButton right_note;
	ImageButton sharp_button;
	ImageButton natural_button;
	Button rests_button;
	ImageButton flat_button;
	TextView meter_disp;
	TextView title_text;
	ImageButton trash_button;
	ImageView clef_image;
	ImageView key_image;
	ImageView gray_cover;
	ImageView music_score;
	ImageButton right_button;
	ImageButton left_button;
	boolean inNotesMode = true; // false when the rests are showing
	boolean placingDynamic = false; // only true when the screen is grayed
									// (that's the plan anyway)
	public static final int MAX_NOTES_ONSCREEN = 9;
	Song song = null;
	states state = states.wait;

	View selected_view;
	boolean button_clicked = false; // tells whether the selected item is a
									// button or placed piece
	placement_objects type_selected;
	View[] notes = new View[MAX_NOTES_ONSCREEN];
	View[] annotations = new View[MAX_NOTES_ONSCREEN];
	View[] dynos = new View[MAX_NOTES_ONSCREEN];
	int screen_number = 0; // this increments as you screen to the right. min =
							// 0.

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_mode);
		meter_disp = (TextView) findViewById(R.id.meter_text);
		group = (ViewGroup) findViewById(R.id.edit_layout);
		left_note = (ImageButton) findViewById(R.id.left_note);
		middle_note = (ImageButton) findViewById(R.id.middle_note);
		right_note = (ImageButton) findViewById(R.id.right_note);
		ImageButton more_notes = (ImageButton) findViewById(R.id.more_notes);
		rests_button = (Button) findViewById(R.id.rests_button);
		ImageButton record_button = (ImageButton) findViewById(R.id.record_button);
		Button dynamics_button = (Button) findViewById(R.id.dynamics_button);
		ImageButton slurs_button = (ImageButton) findViewById(R.id.slurs_button);
		sharp_button = (ImageButton) findViewById(R.id.sharp_button);
		flat_button = (ImageButton) findViewById(R.id.flat_button);
		natural_button = (ImageButton) findViewById(R.id.natural_button);
		music_score = (ImageView) findViewById(R.id.music_score);
		trash_button = (ImageButton) findViewById(R.id.trash_can);
		clef_image = (ImageView) findViewById(R.id.clef_image);
		key_image = (ImageView) findViewById(R.id.keysig_image);
		right_button = (ImageButton) findViewById(R.id.right_arrow);
		left_button = (ImageButton) findViewById(R.id.left_arrow);
		gray_cover = (ImageView) findViewById(R.id.gray_cover);
		gray_cover.setVisibility(View.INVISIBLE);
		tempo_text = (TextView) findViewById(R.id.bpm_text);
		title_text = (TextView) findViewById(R.id.title_text);

		song = (Song) getIntent().getSerializableExtra("song object");

		addClefMeterKey(song);

		class record_click implements OnClickListener {
			@Override
			public void onClick(View v) {
				createRecordDialog();
			}
		}
		record_button.setOnClickListener(new record_click());
		class left_arrow_button_click implements OnClickListener {
			@Override
			public void onClick(View v) {
				if (!inNotesMode) {
					rests_button.performClick();
				}
				generateScreen(screen_number - 1, false);
			}
		}

		class right_arrow_button_click implements OnClickListener {
			@Override
			public void onClick(View v) {
				if (!inNotesMode) {
					rests_button.performClick();
				}
				generateScreen(screen_number + 1, false);
			}
		}

		meter_disp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openMeterSpinner();
			}

		});

		tempo_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeTempo();
			}
		});

		title_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeTitle();
			}
		});

		class eigth_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.wait) {
					state = states.selected_to_place;
					button_clicked = true;
					if (inNotesMode) {
						selected_view = select(R.drawable.eigth_note_transparent);
						((ImageButton) arg0)
								.setImageResource(R.drawable.eigth_note_inverted);
						type_selected = placement_objects.eighth_note;
					} else {
						selected_view = select(R.drawable.eighth_rest);
						((ImageButton) arg0)
								.setImageResource(R.drawable.eighth_rest_inverted);
						type_selected = placement_objects.eighth_rest;
					}
				} else {
					state = states.wait;
					unclickAll();
				}
			}
		}

		class rest_click implements OnClickListener {
			@Override
			public void onClick(View arg0) {
				// this should just toggle the images.
				// it also unclicks everything for safety TODO is this
				// necessary????
				state = states.wait;
				// it also changes its text depending on what's showing.
				if (rests_button.getText().equals("Rests")) {
					// then we should show rests and change the text to Notes
					rests_button.setText("Notes");
					inNotesMode = false;
					left_note.setImageResource(R.drawable.eighth_rest);
					middle_note.setImageResource(R.drawable.quarter_rest);
					right_note.setImageResource(R.drawable.half_rest);
				} else {
					// then we should show notes and change the text to Rests
					inNotesMode = true;
					rests_button.setText("Rests");
					left_note
							.setImageResource(R.drawable.eigth_note_transparent);
					middle_note
							.setImageResource(R.drawable.quarter_note_transparent);
					right_note
							.setImageResource(R.drawable.half_note_transparent);
				}
				unclickAll();
			}
		}

		class quarter_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.wait) {
					state = states.selected_to_place;
					button_clicked = true;
					if (inNotesMode) {
						selected_view = select(R.drawable.quarter_note_transparent);
						((ImageButton) arg0)
								.setImageResource(R.drawable.quarter_note_inverted);
						type_selected = placement_objects.quarter_note;
					} else {
						selected_view = select(R.drawable.quarter_rest);
						((ImageButton) arg0)
								.setImageResource(R.drawable.quarter_rest_inverted);
						type_selected = placement_objects.quarter_rest;
					}
				} else {
					state = states.wait;
					unclickAll();
				}
			}
		}

		class half_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.wait) {
					button_clicked = true;
					state = states.selected_to_place;
					if (inNotesMode) {
						selected_view = select(R.drawable.half_note_transparent);
						((ImageButton) arg0)
								.setImageResource(R.drawable.half_note_inverted);
						type_selected = placement_objects.half_note;
					} else {
						selected_view = select(R.drawable.half_rest);
						((ImageButton) arg0)
								.setImageResource(R.drawable.half_rest_inverted);
						type_selected = placement_objects.half_rest;
					}
				} else {
					state = states.wait;
					unclickAll();
				}
			}
		}

		class sharp_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.wait) {
					button_clicked = true;
					state = states.selected_to_place;
					selected_view = select(R.drawable.sharp_transparent);
					((ImageButton) arg0)
							.setImageResource(R.drawable.sharp_inverted);
					type_selected = placement_objects.sharp;
				} else {
					state = states.wait;
					unclickAll();
				}
			}
		}

		class natural_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.wait) {
					button_clicked = true;
					state = states.selected_to_place;
					selected_view = select(R.drawable.natural_transparent);
					((ImageButton) arg0)
							.setImageResource(R.drawable.natural_inverted);
					type_selected = placement_objects.natural;
				} else {
					state = states.wait;
					unclickAll();
				}
			}
		}

		class flat_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.wait) {
					state = states.selected_to_place;
					button_clicked = true;
					selected_view = select(R.drawable.flat_transparent);
					((ImageButton) arg0)
							.setImageResource(R.drawable.flat_inverted);
					type_selected = placement_objects.flat;
				} else {
					state = states.wait;
					unclickAll();
				}
			}
		}

		class trash_click implements OnClickListener {

			@Override
			public void onClick(View arg0) {
				if (state == states.selected_to_place) {
					Log.d("",
							"Selected view null? "
									+ Boolean.toString(selected_view == null));
					// at this point we could be placing a new element OR
					// deleting an old one
					if (button_clicked) {
						button_clicked = false;
					} else {
						if (selected_view != null) {
							group.removeView(selected_view);
							for (int i = 0; i < notes.length; i++) {
								if (notes[i] == selected_view) {
									notes[i] = null;
								}
							}
							for (int i = 0; i < annotations.length; i++) {
								if (annotations[i] == selected_view) {
									annotations[i] = null;
								}
							}
						}
					}
					state = states.wait;
					unclickAll();
				} else {
					createAreYouSure();
				}
			}
		}

		left_note.setOnClickListener(new eigth_click());
		middle_note.setOnClickListener(new quarter_click());
		right_note.setOnClickListener(new half_click());
		natural_button.setOnClickListener(new natural_click());
		flat_button.setOnClickListener(new flat_click());
		sharp_button.setOnClickListener(new sharp_click());
		trash_button.setOnClickListener(new trash_click());
		left_button.setOnClickListener(new left_arrow_button_click());
		right_button.setOnClickListener(new right_arrow_button_click());
		rests_button.setOnClickListener(new rest_click());

		dynamics_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				createDynamicsDialog();
			}
		});

		clef_image.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				createClefDialog();
			}
		});
		key_image.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				createKeyDialog();
			}
		});

		class score_touch implements OnTouchListener {
			public boolean onTouch(float event_x, float event_y) {
				if (notes[0] != null) {
					Log.d("", notes[0].getHeight() + ", notes 0 height");
				}
				Log.d("", "Processing down click");
				Log.d("", "Location: " + event_x + " " + event_y);
				if (state == states.selected_to_place) {
					Log.d("", "Processing placement click");
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.WRAP_CONTENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
					params.topMargin = (int) event_y - 30;
					// figure out where click occurred
					int x = (int) event_x;
					int y = (int) event_y;
					// see if that's on an old image
					for (int i = 0; i < notes.length; i++) {
						if (notes[i] == null) {
							continue;
						}
						Rect hitSpace = new Rect();
						notes[i].getHitRect(hitSpace);
						if (hit(x, y, hitSpace)) {
							// hit!
							if (type_selected == placement_objects.flat
									|| type_selected == placement_objects.natural
									|| type_selected == placement_objects.sharp) {
								// need to find an annotation spot
								params.leftMargin = offset_left + (i + 1) * 60;
								// remove any previous annotation there
								if (annotations[i] != null) {
									group.removeView(annotations[i]);
								}
								if (notes[i].getTag() == placement_objects.eighth_rest
										|| notes[i].getTag() == placement_objects.half_rest
										|| notes[i].getTag() == placement_objects.quarter_rest) {
									unclickAll();
									return true;
								}
								annotations[i] = selected_view;
								selected_view.setLayoutParams(params);
								group.removeView(selected_view);
								group.addView(selected_view);
								selected_view.setTag(type_selected);
							}
							state = states.wait;
							unclickAll();
							return true;
						}
					}
					// if we hit an annotation, unclickkk.
					for (int i = 0; i < annotations.length; i++) {
						if (annotations[i] == null) {
							continue;
						}
						Rect hitSpace = new Rect();
						annotations[i].getHitRect(hitSpace);
						if (hit(x, y, hitSpace)) {
							// hit!
							unclickAll();
							state = states.wait;
							return true;
						}
					}
					// placing a piece.
					// I'm sure these three for loops are important. But I don't
					// know why, and I'm not gonna mess up a good thing.
					for (int i = 0; i < notes.length; i++) {
						if (notes[i] == selected_view) {
							notes[i] = null;
						}
					}
					for (int i = 0; i < annotations.length; i++) {
						if (annotations[i] == selected_view) {
							annotations[i] = null;
						}
					}
					for (int i = 0; i < dynos.length; i++) {
						if (dynos[i] == selected_view) {
							dynos[i] = null;
						}
					}
					group.removeView(selected_view);
					// //////////
					if (type_selected == placement_objects.flat
							|| type_selected == placement_objects.natural
							|| type_selected == placement_objects.sharp) {
						state = states.wait;
						unclickAll();
						return true;
					} else if (placingDynamic) {
						// need to find a location for the dynamic on the bottom
						// based on proximity of the click.
						placingDynamic = false;
						if (event_y > 280 || event_y < 250) {
							state = states.wait;
							unclickAll();
							// outside the boundary.
							return true;
						} else {
							// loc is where we determine the dynamic to probably
							// have been placed.
							// thus we add it...
							int loc = (int) ((event_x - 30) / 60 - 1);
							params.topMargin = 264;
							params.leftMargin = offset_left + 10 + (loc) * 60;
							Log.d("",
									"Placing a dynamic: "
											+ selected_view.getTag());
							dynos[loc] = selected_view;
							selected_view.setLayoutParams(params);
							group.addView(selected_view);
							state = states.wait;
							unclickAll();
							return true;
						}
					} else {
						// need to find a note spot
						if (event_y > 267 || event_y < 110) {
							state = states.wait;
							unclickAll();
							// outside the score...
							return true;
						}
						int i = nextNote();
						if (i < 0) {
							// here's where we need a new screen. we should
							// generate the new screen
							// then process the click as normal?
							// generateScreen(screen_number + 1);
							return false;
						}
						int staff_line = 0;
						for (int j = 0; j < NoteFrequencies.staff_lines.length; j++) {
							if (event_y < NoteFrequencies.staff_lines[j]) {
								staff_line = j;
								continue;
							}
						}
						if (!inNotesMode) {
							// all the rests should go at the same height, which
							// is staff_lines[2]
							staff_line = 5;
						}
						if (staff_line == 0
								|| staff_line == NoteFrequencies.staff_lines.length - 1) {
							// need a new picture.
							if (type_selected == placement_objects.eighth_note) {
								((ImageView) selected_view)
										.setImageResource(R.drawable.eigth_note_transparent_low);
							} else if (type_selected == placement_objects.quarter_note) {
								((ImageView) selected_view)
										.setImageResource(R.drawable.quarter_note_transparent_low);
							} else if (type_selected == placement_objects.half_note) {
								((ImageView) selected_view)
										.setImageResource(R.drawable.half_note_transparent_low);
							}
						}
						params.topMargin = NoteFrequencies.staff_lines[staff_line] - 30;
						params.leftMargin = offset_left + 10 + (i + 1) * 60;
						Log.d("", "Setting notes[" + i + "]");
						notes[i] = selected_view;
					}
					selected_view.setLayoutParams(params);
					group.addView(selected_view);
					selected_view.setTag(type_selected);
					state = states.wait;
					unclickAll();
					return true;
				} else {
					// waiting. this click is probably to pick up a piece.
					// figure out where click occurred
					button_clicked = false;
					int x = (int) event_x;
					int y = (int) event_y;
					// see if that's on an old image
					for (int i = 0; i < notes.length; i++) {
						if (notes[i] == null) {
							continue;
						}
						Rect hitSpace = new Rect();
						notes[i].getHitRect(hitSpace);
						if (hit(x, y, hitSpace)) {
							// hit!
							((ImageView) notes[i]).setBackgroundColor(Color
									.argb(50, 255, 230, 100)); // should be a
																// more neutral
																// yellow
							selected_view = notes[i];
							Log.d("", "Selected view null (selectin)? "
									+ Boolean.toString(selected_view == null));
							type_selected = (placement_objects) notes[i]
									.getTag();
							state = states.selected_to_place;
							return true;
						}
					}
					for (int i = 0; i < annotations.length; i++) {
						if (annotations[i] == null) {
							continue;
						}
						Rect hitSpace = new Rect();
						annotations[i].getHitRect(hitSpace);
						if (hit(x, y, hitSpace)) {
							// hit!
							((ImageView) annotations[i])
									.setBackgroundColor(Color.argb(50, 255,
											230, 100)); // more neutral yellow
							selected_view = annotations[i];
							type_selected = (placement_objects) selected_view
									.getTag();
							state = states.selected_to_place;
							return true;
						}
					}
					for (int i = 0; i < dynos.length; i++) {
						if (dynos[i] == null) {
							continue;
						}
						Rect hitSpace = new Rect();
						dynos[i].getHitRect(hitSpace);
						if (hit(x, y, hitSpace)) {
							// hit!
							((ImageView) dynos[i]).setBackgroundColor(Color
									.argb(50, 255, 230, 100)); // more neutral
																// yellow
							selected_view = dynos[i];
							type_selected = (placement_objects) selected_view
									.getTag();
							state = states.selected_to_place;
							placingDynamic = true;
							return true;
						}
					}
				}
				return false;
			}

			@Override
			public boolean onTouch(View v1, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					return onTouch(event.getX(), event.getY());
				}
				return false;
			}
		}
		;

		music_score.setOnTouchListener(new score_touch());
		generateScreen(0, true);
		setToNotesMode();
	}

	private void generateScreen(int number, boolean opening) {
		// plan here is to save all the shit onscreen
		// then redisplay an overlapping note and arrows as necessary.
		// NB: savePage() must occur before reassigning screen_number
		if (!opening) {
			this.savePage();
		}
		Log.d("", "generating screen: " + number);
		Log.d("", "song size: " + this.song.size());
		this.screen_number = number;
		if (this.screen_number > 0) {
			this.left_button.setVisibility(View.VISIBLE);
			this.clef_image.setVisibility(View.INVISIBLE);
			this.meter_disp.setVisibility(View.INVISIBLE);
			this.key_image.setVisibility(View.INVISIBLE);
		} else {
			this.left_button.setVisibility(View.INVISIBLE);
			this.clef_image.setVisibility(View.VISIBLE);
			this.meter_disp.setVisibility(View.VISIBLE);
			this.key_image.setVisibility(View.VISIBLE);
		}
		// show the right button if there are more notes off the page.
		if (this.song.size() >= (this.screen_number + 1) * MAX_NOTES_ONSCREEN) {
			this.right_button.setVisibility(View.VISIBLE);
		} else {
			this.right_button.setVisibility(View.INVISIBLE);
		}
		// before displaying anything, we hardcore clear the screen.
		unclickAll();
		for (View v : notes) {
			group.removeView(v);
		}
		for (View v : annotations) {
			group.removeView(v);
		}
		for (View v : dynos) {
			group.removeView(v);
		}
		this.dynos = new View[MAX_NOTES_ONSCREEN];
		this.notes = new View[MAX_NOTES_ONSCREEN];
		this.annotations = new View[MAX_NOTES_ONSCREEN];

		// we do this by duplicating and hopefully replacing all of the
		// uploadFromSong code.
		int g = 0;
		while (g < MAX_NOTES_ONSCREEN) {
			Note n = this.song.getNoteNum(g + this.screen_number
					* MAX_NOTES_ONSCREEN);
			if (n == null) {
				break;
			}
			Log.d("", "adding note");
			double freq = n.getPitch();
			Log.d("", "Frequency: " + freq);
			double l = n.getLength();
			double x = offset_left + 10 + (g + 1) * 60;
			Log.d("", "" + x);
			String str = NoteFrequencies.freqToString.get(freq);
			Log.d("", str);
			str = str.substring(0, 2);
			Log.d("", str);
			double y = 0;
			String[] staff_note_rep = null;
			if (song.getClef() == 0) {
				staff_note_rep = NoteFrequencies.bass_staff_notes;
			} else {
				staff_note_rep = NoteFrequencies.staff_notes;
			}
			for (int i = 0; i < staff_note_rep.length; i++) {
				if (staff_note_rep[i].equals(str)) {
					y = NoteFrequencies.staff_lines[i] - 30;
				}
			}
			boolean line_through = false;
			if (y == NoteFrequencies.staff_lines[0] - 30
					|| y == NoteFrequencies.staff_lines[NoteFrequencies.staff_lines.length - 1] - 30) {
				line_through = true;
			}
			Log.d("", "");
			if (n.isRest()) {
				inNotesMode = false;
			}
			if (l == 0.125) {
				left_note.performClick();
				if (inNotesMode) {
					if (!line_through) {
						((ImageView) selected_view)
								.setImageResource(R.drawable.eigth_note_transparent);
					} else {
						((ImageView) selected_view)
								.setImageResource(R.drawable.eigth_note_transparent_low);
					}
				}
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y;
				params.leftMargin = offset_left + 10 + (g + 1) * 60;
				// remove any previous annotation there
				notes[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				selected_view.setTag(type_selected);
				state = states.wait;
				unclickAll();
			} else if (l == 0.25) {
				middle_note.performClick();
				if (inNotesMode) {
					if (!line_through) {
						((ImageView) selected_view)
								.setImageResource(R.drawable.quarter_note_transparent);
					} else {
						((ImageView) selected_view)
								.setImageResource(R.drawable.quarter_note_transparent_low);
					}
				}
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y;
				params.leftMargin = offset_left + 10 + (g + 1) * 60;
				// remove any previous annotation there
				notes[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				selected_view.setTag(type_selected);
				state = states.wait;
				unclickAll();
			} else if (l == 0.5) {
				right_note.performClick();
				if (inNotesMode) {
					if (!line_through) {
						((ImageView) selected_view)
								.setImageResource(R.drawable.half_note_transparent);
					} else {
						((ImageView) selected_view)
								.setImageResource(R.drawable.half_note_transparent_low);
					}
				}
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y;
				params.leftMargin = offset_left + 10 + (g + 1) * 60;
				// remove any previous annotation there
				notes[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				selected_view.setTag(type_selected);
				state = states.wait;
				unclickAll();
			}
			str = NoteFrequencies.freqToString.get(freq);
			Log.d("", "str value: " + str);
			if (str.endsWith("sharp")) {
				Log.wtf("", "Clicking sharp");
				sharp_button.performClick();
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y - 30;
				params.leftMargin = offset_left + (g + 1) * 60;
				// remove any previous annotation there
				annotations[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				selected_view.setTag(type_selected);
				state = states.wait;
				unclickAll();
			} else if (str.endsWith("flat")) {
				flat_button.performClick();
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y - 30;
				params.leftMargin = offset_left + (g + 1) * 60;
				// remove any previous annotation there
				annotations[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				selected_view.setTag(type_selected);
				state = states.wait;
				unclickAll();
			} else if (str.endsWith("natural")) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (int) y - 30;
				params.leftMargin = offset_left + (g + 1) * 60;
				// remove any previous annotation there
				annotations[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				selected_view.setTag(type_selected);
				state = states.wait;
				unclickAll();
			}
			String dynString = n.getDynamic();
			if (dynString != null) {
				if (dynString.equals("pp")) {
					selected_view = select(R.drawable.pp);
					selected_view.setTag(placement_objects.pp);
				} else if (dynString.equals("p")) {
					selected_view = select(R.drawable.p);
					selected_view.setTag(placement_objects.p);
				} else if (dynString.equals("mp")) {
					selected_view = select(R.drawable.mp);
					selected_view.setTag(placement_objects.mp);
				} else if (dynString.equals("mf")) {
					selected_view = select(R.drawable.mf);
					selected_view.setTag(placement_objects.mf);
				} else if (dynString.equals("f")) {
					selected_view = select(R.drawable.f);
					selected_view.setTag(placement_objects.f);
				} else if (dynString.equals("ff")) {
					selected_view = select(R.drawable.ff);
					selected_view.setTag(placement_objects.ff);
				}
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = 264;
				params.leftMargin = offset_left + 10 + (g + 1) * 60;
				// remove any previous annotation there
				dynos[g] = selected_view;
				selected_view.setLayoutParams(params);
				group.removeView(selected_view);
				group.addView(selected_view);
				state = states.wait;
				unclickAll();
			}
			g++;
			if (inNotesMode == false) {
				inNotesMode = true;
			}
		}
	}

	public void unclickAll() {
		Log.d("", "Unclicking all");
		if (inNotesMode) {
			left_note.setImageResource(R.drawable.eigth_note_transparent);
			middle_note.setImageResource(R.drawable.quarter_note_transparent);
			right_note.setImageResource(R.drawable.half_note_transparent);
		} else {
			left_note.setImageResource(R.drawable.eighth_rest);
			middle_note.setImageResource(R.drawable.quarter_rest);
			right_note.setImageResource(R.drawable.half_rest);
		}
		gray_cover.setVisibility(View.INVISIBLE);
		placingDynamic = false;
		sharp_button.setImageResource(R.drawable.sharp_transparent);
		flat_button.setImageResource(R.drawable.flat_transparent);
		natural_button.setImageResource(R.drawable.natural_transparent);
		selected_view = null;
		type_selected = null;
		for (View v : notes) {
			if (v != null) {
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		}
		for (View v : annotations) {
			if (v != null) {
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		}
		for (View v : dynos) {
			if (v != null) {
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	public View select(int res) {
		state = states.selected_to_place;
		ImageView img = new ImageView(getApplicationContext());
		img.setImageResource(res);
		Log.d("", "Selected_view: " + res);
		img.setAdjustViewBounds(true);
		img.setMaxHeight(65);
		img.setMaxWidth(50);
		img.setVisibility(0);
		return img;
	}

	public int nextAnnotation() {
		for (int i = 0; i < this.annotations.length; i++) {
			if (annotations[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public int nextNote() {
		for (int i = 0; i < this.notes.length; i++) {
			if (notes[i] == null) {
				if (i == this.notes.length - 1) {
					this.right_button.setVisibility(View.VISIBLE);
				}
				return i;
			}
		}
		return -1;
	}

	public boolean hit(int x, int y, Rect hitSpace) {
		if (x > hitSpace.left && x < hitSpace.right && y < hitSpace.bottom
				&& y > hitSpace.top) {
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onPause() {
		super.onPause();
		savePage();
		finish();
	}

	public void savePage() {
		Log.d("",
				"save page called, song is null? : "
						+ Boolean.toString(song == null));
		if (song != null) {
		} else {
			song = new Song();
		}
		for (int i = 0; i < notes.length; i++) {
			Note n = getNoteFromIndex(i);
			Log.d("",
					"note at index: " + i + " is null? "
							+ Boolean.toString(n == null));
			// Log.d("", "note at index: " + i + " is " + n.getName());
			song.setNote(MAX_NOTES_ONSCREEN * this.screen_number + i, n);
		}
		// put song into return intent
		Intent i = new Intent();
		i.putExtra("song object", song);
		setResult(0, i);
	}

	public void addClefMeterKey(Song s) {
		if (s == null) {
			return;
		}
		int tempo = s.getTempo();
		tempo_text.setText("" + tempo + " " + "BPM");
		title_text.setText(s.getTitle());
		int clef = s.getClef();
		String[] keyList = KeySignatures.keyMap.get(s.getKey());
		if (clef == 1) { // treble clef
			((ImageView) findViewById(R.id.clef_image))
					.setImageResource(R.drawable.treble_clef);

			if (keyList[0] == "Sharp") {
				if (keyList.length == 2) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t1sharps);
				} else if (keyList.length == 3) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t2sharps);
				} else if (keyList.length == 4) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t3sharps);
				} else if (keyList.length == 5) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t4sharps);
				} else if (keyList.length == 6) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t5sharps);
				} else if (keyList.length == 7) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t6sharps);
				} else if (keyList.length == 8) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t7sharps);
				}

			} else if (keyList[0] == "Flat") {
				if (keyList.length == 2) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t1flats);
				} else if (keyList.length == 3) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t2flats);
				} else if (keyList.length == 4) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t3flats);
				} else if (keyList.length == 5) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t4flats);
				} else if (keyList.length == 6) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t5flats);
				} else if (keyList.length == 7) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t6flats);
				} else if (keyList.length == 8) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.t7flats);
				}
			} else { // C or A minor
				((ImageView) findViewById(R.id.keysig_image))
						.setImageResource(R.drawable.none);
			}

		} else {// bass clef
			if (keyList[0] == "Sharp") {
				if (keyList.length == 2) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b1sharps);
				} else if (keyList.length == 3) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b2sharps);
				} else if (keyList.length == 4) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b3sharps);
				} else if (keyList.length == 5) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b4sharps);
				} else if (keyList.length == 6) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b5sharps);
				} else if (keyList.length == 7) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b6sharps);
				} else if (keyList.length == 8) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b7sharps);
				}

			} else if (keyList[0] == "Flat") {
				if (keyList.length == 2) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b1flats);
				} else if (keyList.length == 3) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b2flats);
				} else if (keyList.length == 4) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b3flats);
				} else if (keyList.length == 5) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b4flats);
				} else if (keyList.length == 6) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b5flats);
				} else if (keyList.length == 7) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b6flats);
				} else if (keyList.length == 8) {
					((ImageView) findViewById(R.id.keysig_image))
							.setImageResource(R.drawable.b7flats);
				}
			} else { // C or A minor
				((ImageView) findViewById(R.id.keysig_image))
						.setImageResource(R.drawable.none);
			}
		}
		meter_disp.setText("" + s.getMeterTop() + "\n" + s.getMeterBottom());
	}

	public enum edit_menu_options {
		DELETE, SAVE, CLOSE, PLAY,
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
			savePage();
			finish();
			break;
		case R.id.edit_play:
			savePage();
			Intent next = new Intent(EditModeLegacy.this,
					PlaybackModeLegacy.class);
			next.putExtra("song object", song);
			next.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(next);
			break;
		case R.id.edit_save:
			savePage();
			break;
		case R.id.edit_delete:
			createAreYouSure();
			break;
		}
		return false;
	}

	public void createAreYouSure() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete Permanently?")
				.setCancelable(true)
				.setPositiveButton("Yes!",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent();
								i.putExtra("song object", song);
								setResult(1, i);// 1 is delete
								finish();
							}
						})
				.setNegativeButton("No!",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void createDynamicsDialog() {
		final CharSequence[] dynamics = { "pp", "p", "mp", "mf", "f", "ff" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Dynamic");
		builder.setItems(dynamics, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				((Button) findViewById(R.id.dynamics_button))
						.setText(dynamics[item]);
				gray_cover.setVisibility(View.VISIBLE);
				placingDynamic = true;
				if (dynamics[item].equals("pp")) {
					selected_view = select(R.drawable.pp);
					selected_view.setTag(placement_objects.pp);
				} else if (dynamics[item].equals("p")) {
					selected_view = select(R.drawable.p);
					selected_view.setTag(placement_objects.p);
				} else if (dynamics[item].equals("mp")) {
					selected_view = select(R.drawable.mp);
					selected_view.setTag(placement_objects.mp);
				} else if (dynamics[item].equals("mf")) {
					selected_view = select(R.drawable.mf);
					selected_view.setTag(placement_objects.mf);
				} else if (dynamics[item].equals("f")) {
					selected_view = select(R.drawable.f);
					selected_view.setTag(placement_objects.f);
				} else if (dynamics[item].equals("ff")) {
					selected_view = select(R.drawable.ff);
					selected_view.setTag(placement_objects.ff);
				}
			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	public void createClefDialog() {
		final CharSequence[] clefs = { "Bass", "Treble" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Clef");
		builder.setItems(clefs, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				song.setClef(item);
				if (item == 0) {
					((ImageView) findViewById(R.id.clef_image))
							.setImageResource(R.drawable.bass_clef);
				} else {
					((ImageView) findViewById(R.id.clef_image))
							.setImageResource(R.drawable.treble_clef);
				}
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void createKeyDialog() {
		final CharSequence[] keys = { "C", "G", "D", "A", "E", "B", "F#", "Db",
				"Ab", "Eb", "Bb", "F", "A minor", "E minor", "B minor",
				"F# minor", "Db minor", "Ab minor", "Eb minor", "Bb minor",
				"F minor", "C minor", "G minor", "D minor" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Key");
		builder.setItems(keys, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				song.setKey(item);
				int c = song.getClef();
				String[] keyList = KeySignatures.keyMap.get(song.getKey());
				if (c == 1) { // treble clef

					if (keyList[0] == "Sharp") {
						if (keyList.length == 2) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t1sharps);
						} else if (keyList.length == 3) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t2sharps);
						} else if (keyList.length == 4) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t3sharps);
						} else if (keyList.length == 5) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t4sharps);
						} else if (keyList.length == 6) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t5sharps);
						} else if (keyList.length == 7) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t6sharps);
						} else if (keyList.length == 8) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t7sharps);
						}

					} else if (keyList[0] == "Flat") {
						if (keyList.length == 2) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t1flats);
						} else if (keyList.length == 3) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t2flats);
						} else if (keyList.length == 4) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t3flats);
						} else if (keyList.length == 5) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t4flats);
						} else if (keyList.length == 6) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t5flats);
						} else if (keyList.length == 7) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t6flats);
						} else if (keyList.length == 8) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.t7flats);
						}
					} else { // C or A minor
						((ImageView) findViewById(R.id.keysig_image))
								.setImageResource(R.drawable.none);
					}

				} else {// bass clef
					if (keyList[0] == "Sharp") {
						if (keyList.length == 2) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b1sharps);
						} else if (keyList.length == 3) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b2sharps);
						} else if (keyList.length == 4) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b3sharps);
						} else if (keyList.length == 5) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b4sharps);
						} else if (keyList.length == 6) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b5sharps);
						} else if (keyList.length == 7) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b6sharps);
						} else if (keyList.length == 8) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b7sharps);
						}

					} else if (keyList[0] == "Flat") {
						if (keyList.length == 2) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b1flats);
						} else if (keyList.length == 3) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b2flats);
						} else if (keyList.length == 4) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b3flats);
						} else if (keyList.length == 5) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b4flats);
						} else if (keyList.length == 6) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b5flats);
						} else if (keyList.length == 7) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b6flats);
						} else if (keyList.length == 8) {
							((ImageView) findViewById(R.id.keysig_image))
									.setImageResource(R.drawable.b7flats);
						}
					} else { // C or A minor
						((ImageView) findViewById(R.id.keysig_image))
								.setImageResource(R.drawable.none);
					}
				}
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public Note getNoteFromIndex(int i) {
		double pitch = 0;
		double length = 0;
		String name = "";
		String annotation = "";
		String dynamic = null;
		if (notes[i] != null) {
			if (annotations[i] != null) {
				if (((ImageView) annotations[i]).getTag() == placement_objects.flat) {
					annotation = "flat";
				} else if (((ImageView) annotations[i]).getTag() == placement_objects.sharp) {
					annotation = "sharp";
				}
			}
			if (dynos[i] != null) {
				placement_objects tag = (placement_objects) (dynos[i]).getTag();
				Log.d("", "there should be a dynamic here: " + tag);
				if (tag == placement_objects.pp) {
					dynamic = "pp";
				} else if (tag == placement_objects.p) {
					dynamic = "p";
				} else if (tag == placement_objects.mp) {
					dynamic = "mp";
				} else if (tag == placement_objects.mf) {
					dynamic = "mf";
				} else if (tag == placement_objects.f) {
					dynamic = "f";
				} else if (tag == placement_objects.ff) {
					dynamic = "ff";
				}
			}
			String[] staff_note_rep;
			if (song.getClef() == 0) {
				staff_note_rep = NoteFrequencies.bass_staff_notes;
				Log.d("", "Bass rep");
			} else {
				staff_note_rep = NoteFrequencies.staff_notes;
			}
			Log.d("", "staff_note_rep[0]: " + staff_note_rep[0]);
			name = staff_note_rep[0] + annotation;
			for (int j = 0; j < NoteFrequencies.staff_lines.length; j++) {
				if (NoteFrequencies.staff_lines[j] > ((ImageView) notes[i])
						.getTop() + 25) {
					name = staff_note_rep[j] + annotation;
				}
			}
			Log.d("", "Note name: " + name);
			if (!NoteFrequencies.frequencyMap.containsKey(name)) {
				pitch = 440;
			} else {
				pitch = NoteFrequencies.frequencyMap.get(name).doubleValue();
			}
			Log.d("", "Type of note: " + ((ImageView) notes[i]).getTag());
			placement_objects tag = (placement_objects) ((ImageView) notes[i])
					.getTag();
			if (tag == placement_objects.eighth_note
					|| tag == placement_objects.eighth_rest) {
				length = 0.125;
			} else if (tag == placement_objects.quarter_note
					|| tag == placement_objects.quarter_rest) {
				length = 0.25;
			} else if (tag == placement_objects.half_note
					|| tag == placement_objects.half_rest) {
				length = 0.5;
			}
			Note n = new Note(pitch, length, name);
			Log.d("", "tag: " + tag);
			if (tag == placement_objects.eighth_rest
					|| tag == placement_objects.half_rest
					|| tag == placement_objects.quarter_rest) {
				n.setRest(true);
				Log.d("", "Setting rest = true, note: " + i);
			}
			n.setDynamic(dynamic);
			Log.d("", "Just set dynamic: " + dynamic);
			return n;
		}
		return null;
	}

	public void createRecordDialog() {
		final View layout = View.inflate(this, R.layout.recording, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(layout);

		final MediaRecorder recording = new android.media.MediaRecorder();
		recording.setAudioSource(MediaRecorder.AudioSource.MIC); // can't test
																	// this with
																	// emulator
																	// because
																	// it has no
																	// mic.
																	// should
																	// work in
																	// phone
		recording.start();

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				recording.stop();
				// need to save the recording somewhere
				recording.release();
				Song s1 = new Song();
				Log.d("", "new song make");
				s1.setTitle("My Favorite Song");
				Log.d("", "new song title");
				s1.addNote(new Note(NoteFrequencies.getFrequency("e4"), 0.125,
						"e4"));
				Log.d("", "new note");
				s1.addNote(new Note(NoteFrequencies.getFrequency("e5"), 0.25,
						"e5"));
				s1.addNote(new Note(NoteFrequencies.getFrequency("a4"), 0.5,
						"a4"));
				s1.addNote(new Note(NoteFrequencies.getFrequency("b4"), 0.125,
						"b4"));
				Log.d("", "all notes");
				song = s1;
				generateScreen(0, true);
			}
		});
	}

	private void openMeterSpinner() {
		final CharSequence[] meters = getResources().getStringArray(
				R.array.meter_array);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Meter");
		builder.setItems(meters, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (meters[item].toString().length() >= 4) {
					song.setMeter(12, 8);
				} else {
					String str = meters[item].toString();
					Log.d("", "Meter (should be less than 12/8): " + str);
					song.setMeter(Integer.valueOf(str.substring(0, 1)),
							Integer.valueOf(str.substring(2, 3)));
				}
				meter_disp.setText("" + song.getMeterTop() + "\n"
						+ song.getMeterBottom());
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void changeTempo() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Change Tempo");
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				try {
					song.setTempo(Integer.valueOf(value));
					tempo_text.setText("" + song.getTempo() + " " + "BPM");
				} catch (Exception e) {

				}
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		alert.show();
	}

	private void changeTitle() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Change Title");
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				try {
					song.setTitle(value);
					title_text.setText(value);
				} catch (Exception e) {

				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

	private void setToNotesMode() {
		inNotesMode = true;
		left_note.setImageResource(R.drawable.eigth_note_transparent);
		middle_note.setImageResource(R.drawable.quarter_note_transparent);
		right_note.setImageResource(R.drawable.half_note_transparent);
		rests_button.setText("Rests");
	}
}
