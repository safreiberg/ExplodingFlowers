package com.MusicalSketches.datarep;

import java.io.Serializable;
import java.util.Date;

public class Song implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4104425135229975218L;
	private int clef;
	private int tempo;
	private int meterTop;
	private int meterBottom;
	private NoteSequence notes= new NoteSequence();
	private String title;
	private Date date;
	private String key;

	/*
	 * Default constructor if no arguments are passed. Date comes from
	 */
	public Song() {
		this.clef = 1;
		this.tempo = 100;
		this.meterTop = 2;
		this.meterBottom = 4;
		this.title = "Untitled";
		Date d = new Date();
		this.date = new Date(d.getYear(), d.getMonth(), d.getDate()); // yikes!
																		// deprecated
		this.key = "C";
	}
	
	public Song(Date date) {
		this.clef = 1;
		this.tempo = 100;
		this.meterTop = 2;
		this.meterBottom = 4;
		this.title = "Untitled";
		Date d = new Date();
		this.date = new Date(d.getYear(), d.getMonth(), d.getDate()); // yikes!
																		// deprecated
		this.key = "C";
	}

	public Song(int clef, int meterTop, int meterBottom, int tempo, String key,
			String title, Date date) {
		this.clef = clef;
		this.meterTop = meterTop;
		this.meterBottom = meterBottom;
		this.tempo = tempo;
		this.title = title;
		this.date = date;
		this.key = key;
	}

	public void playSong() {
		/*
		 * not sure what belongs here. probably play a song? this functionality
		 * should probably be implemented with the android media player. see
		 * here:
		 * http://stackoverflow.com/questions/2458833/how-do-i-get-a-wav-sound
		 * -to-play
		 */

	}

	/*
	 * Changes to a new note sequence.
	 */
	public void setNotes(NoteSequence n) {
		this.notes = n;
	}

	public void setClef(int clef) {
		this.clef = clef;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public void setMeter(int meterTop, int meterBottom) {
		this.meterTop = meterTop;
		this.meterBottom = meterBottom;
	}

	public NoteSequence getNotes() {
		return this.notes;
	}

	public int getClef() {
		return this.clef;
	}

	public int getTempo() {
		return this.tempo;
	}

	public int getMeterTop() {
		return this.meterTop;
	}

	public int getMeterBottom() {
		return this.meterBottom;
	}
	
	public void addNote(Note n){
		this.notes.addNote(n);
	}
	
	public void setTitle(String t) {
		this.title = t;
	}

	public String getTitle() {
		return title;
	}

	public void setKey(String string) {
		this.key = string;
	}

}
