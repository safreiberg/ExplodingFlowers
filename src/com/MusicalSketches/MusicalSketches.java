package com.MusicalSketches;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker;

public class MusicalSketches extends Activity {
	/** Called when the activity is first created. */
	private ArrayAdapter<String> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListView list1 = (ListView) findViewById(R.id.listView1);

		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, songs);

		list1.setAdapter(arrayAdapter);

		list1.setTextFilterEnabled(true);

		list1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				Intent next = new Intent(MusicalSketches.this,
						NextActivity.class);
				next.putExtra("title", ((TextView) view).getText());
				startActivity(next);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, DELETE, Menu.NONE, "DELETE");
		menu.add(Menu.NONE, SORT, Menu.NONE, "SORT");
		menu.add(Menu.NONE, HELP, Menu.NONE, "HELP!!!");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE:
			break;
		case SORT:
			Toast.makeText(this, "Sorting...", Toast.LENGTH_SHORT).show();
			java.util.Arrays.sort(songs);
			ListView list1 = (ListView) findViewById(R.id.listView1);
			arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, songs);
			list1.setAdapter(arrayAdapter);
			break;
		case HELP:
			createHelpDialog();
			break;
		}
		return false;
	}

	public void createHelpDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("I have no help for you here.")
				.setCancelable(true)
				.setPositiveButton("Sorry!",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public int createClefDialog() {
		final CharSequence[] clefs = { "Treble", "Bass" };
		final int newClef = (Integer) null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Clef");
		builder.setItems(clefs, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), clefs[item],
						Toast.LENGTH_SHORT).show();
				//newClef = item; this doesn't work - need new way to know return value. Could probably call setClef from here. 
			}
		});

		AlertDialog alert = builder.create();
		return newClef;
	}
	
	public int createDynamicsDialog() {
		final CharSequence[] dynamics = { "pp", "p", "mp", "mf", "f", "ff" };
		int newDynamic = (Integer) null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose New Clef");
		builder.setItems(dynamics, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), dynamics[item],
						Toast.LENGTH_SHORT).show();
				//newDynamic = item; this doesn't work - need new way to know return value. Could probably call setDynamic from here. 
			}
		});

		AlertDialog alert = builder.create();
		return newDynamic;
	}

	// TODO: this doesn't work! I need some way of grabbing the numbers and
	// turning them into ints. also,
	// should we add rules to keep entries within tempo ranges (40 to about 220)
//	public int createTempoDialog(int currentTempo) {
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle("New Tempo");
//		final int newTempo = currentTempo;
//		builder.set
//
//		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				newTempo = input.getText();
//				return;
//			}
//		});
//
//		alert.setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//
//					public void onClick(DialogInterface dialog, int which) {
//						return;
//					}
//				});
//		return newTempo;
//	}
	
//	public List <Integer> createMeterDialog(){
//		AlertDialog.Builder alert = new AlertDialog.Builder(this);
//		alert.setTitle("Choose New Meter");
//		 
//	}

	public static final int DELETE = 1;
	public static final int SORT = 2;
	public static final int HELP = 3;
	public String[] songs = new String[] { "Friday, Friday",
			"In the Good Old Summertime", "Lady Gaga Goes Nuts", "More Songs",
			"Monday Monday", "Saturday", "Friday I'm in Love",
			"Last Friday Night", "Manic Mondays", "Black Friday",
			"Ruby Tuesday" };
}