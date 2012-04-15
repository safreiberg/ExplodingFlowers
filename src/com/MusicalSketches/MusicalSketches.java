package com.MusicalSketches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.Button;
import android.view.*;


public class MusicalSketches extends Activity {
	/** Called when the activity is first created. */
	private ArrayAdapter<String> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button = (Button) findViewById(R.id.button1);
		
		
		button.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
            	Intent next = new Intent(MusicalSketches.this,MusicalLibrary.class);
				startActivity(next);
            }
        });

	}
	
//	@Override
//	public void onPause(){
//		ImageView iw = (ImageView) findViewById(R.id.background);
//		iw.setBackgroundResource(0);
//	}
	
	
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

	public void createHelpDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("I have no help for you here.")
		       .setCancelable(true)
		       .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	

	public static final int DELETE = 1;
	public static final int SORT = 2;
	public static final int HELP = 3;
}