package com.MusicalSketches;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MusicalSketches extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListView list1 = (ListView) findViewById(R.id.listView1);

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, songs);

		list1.setAdapter(arrayAdapter);

		list1.setTextFilterEnabled(true);

		list1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
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
			break;
		case HELP:
			break;
		}
		return false;
	}

	public static final int DELETE = 1;
	public static final int SORT = 2;
	public static final int HELP = 3;
	public String[] songs = new String[] { "Friday, Friday",
			"In the Good Old Summertime", "Lady Gaga Goes Nuts", "More Songs",
			"Monday Monday", "Saturday", "Friday I'm in Love",
			"Last Friday Night", "Manic Mondays", "Black Friday",
			"Ruby Tuesday" };
}