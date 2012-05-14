package com.MusicalSketches.datarep;

import java.io.Serializable;
import java.util.Date;

public class Song implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4104425135229975218L;
	private int clef;
	private int tempo;
	private int meterTop;
	private int meterBottom;
	private NoteSequence notes = new NoteSequence();
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

	public void addNote(Note n) {
		this.notes.addNote(n);
	}

	public void setNote(int index, Note n) {
		if (n == null) {
			if (this.notes.size()>index) {
				this.notes.remove(index);
			}
		}
		if (this.notes.size() <= index) {
			this.notes.addNote(n);
		} else {
			notes.setNote(index, n);
		}
	}

	public int size() {
		int i = 0;
		for (Note n : this.notes.getNotes()) {
			if (n!= null) {
				i++;
			}
		}
		return i;
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

	public void setKey(int i) {
		switch (i) {
		case 0:
			this.key = "C";
			break;
		case 1:
			this.key = "G";
			break;
		case 2:
			this.key = "D";
			break;
		case 3:
			this.key = "A";
			break;
		case 4:
			this.key = "E";
			break;
		case 5:
			this.key = "B";
			break;
		case 6:
			this.key = "F#";
			break;
		case 7:
			this.key = "Db";
			break;
		case 8:
			this.key = "Ab";
			break;
		case 9:
			this.key = "Eb";
			break;
		case 10:
			this.key = "Bb";
			break;
		case 11:
			this.key = "F";
			break;
		case 12:
			this.key = "A minor";
			break;
		case 13:
			this.key = "E minor";
			break;
		case 14:
			this.key = "B minor";
			break;
		case 15:
			this.key = "F# minor";
			break;
		case 16:
			this.key = "Db minor";
			break;
		case 17:
			this.key = "Ab minor";
			break;
		case 18:
			this.key = "Eb minor";
			break;
		case 19:
			this.key = "Bb minor";
			break;
		case 20:
			this.key = "F minor";
			break;
		case 21:
			this.key = "C minor";
			break;
		case 22:
			this.key = "G minor";
			break;
		case 23:
			this.key = "D minor";
			break;
		}
	}

	public void updateNotePitch(int index, double pitch) {
		this.notes.updateNotePitch(index, pitch);
	}

	public void updateNoteName(int index, String name) {
		this.notes.updateNoteName(index, name);
	}

	public Note getNoteNum(int index) {
		try {
			return this.notes.getNotes().get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public String getKey() {
		return this.key;
	}

}
