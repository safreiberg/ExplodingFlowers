package com.MusicalSketches;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MusicSheet extends Activity {
	
	private CharSequence songName;
	
	public void MusicSheet(CharSequence songName) {
		this.songName = songName;
	}
	
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
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	
	
	
	
	public static final String[] SONGS = new String[] { "Friday, Friday",
		"Zach Is Awesome", "Zach Is Awesome","Zach Is Awesome","Zach Is Awesome","Zach Is Awesome",
		"Zach Is Awesome","Zach Is Awesome","Zach Is Awesome","Zach Is Awesome","Zach Is Awesome",
		"Lady Gaga Goes Nuts", "More Songs", "Monday Monday", "Saturday",
			"Friday I'm in Love", "Last Friday Night", "Manic Mondays","Black Friday", "Ruby Tuesday"};
}
