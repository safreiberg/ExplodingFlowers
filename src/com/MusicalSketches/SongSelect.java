package com.MusicalSketches;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.Button;
import android.view.*;

public class SongSelect extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_song_setting);

	    Spinner spinner = (Spinner) findViewById(R.id.spinner);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.planets_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	    
	    Spinner button1 = (Spinner) findViewById(R.id.button1);
	    ArrayAdapter<CharSequence> adapterb = ArrayAdapter.createFromResource(
	            this, R.array.clef_array, android.R.layout.simple_spinner_item);
	    adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    button1.setAdapter(adapterb);
	    button1.setOnItemSelectedListener(new MyOnItemSelectedListener());
	    
	    Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
	    ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(
	            this, R.array.meter_array, android.R.layout.simple_spinner_item);
	    adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner1.setAdapter(adapterc);
	    spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
}