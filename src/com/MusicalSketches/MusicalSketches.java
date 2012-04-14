package com.MusicalSketches;

import android.app.Activity;
import android.os.Bundle;
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
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, SONGS);
		
		list1.setAdapter(arrayAdapter);
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	public static final String[] SONGS = new String[] { "Friday, Friday",
			"In the Good Old Summertime", "Lady Gaga Goes Nuts", "More Songs", "Monday Monday", "Saturday",
			"Friday I'm in Love", "Last Friday Night", "Manic Mondays","Black Friday", "Ruby Tuesday"};
}