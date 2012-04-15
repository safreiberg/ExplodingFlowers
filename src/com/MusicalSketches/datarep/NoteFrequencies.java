package com.MusicalSketches.datarep;

public class NoteFrequencies {

	public NoteFrequencies() {

	}

	public static double getFrequency(String note) {
		// returns the frequency in Hz
		if (note.equalsIgnoreCase("e4")) {
			return 329.4;
		} else if (note.equalsIgnoreCase("f4")) {
			return 349.2;
		} else if (note.equalsIgnoreCase("f4sharp")) {
			return 369.9;
		} else if (note.equalsIgnoreCase("g4flat")) {
			return 369.9;
		} else if (note.equalsIgnoreCase("g4")) {
			return 392.0;
		} else if (note.equalsIgnoreCase("g4sharp")) {
			return 415.3;
		} else if (note.equalsIgnoreCase("a4flat")) {
			return 415.3;
		} else if (note.equalsIgnoreCase("a4")) {
			return 440.0;
		} else if (note.equalsIgnoreCase("a4sharp")) {
			return 466.2;
		} else if (note.equalsIgnoreCase("b4flat")) {
			return 466.2;
		} else if (note.equalsIgnoreCase("b4")) {
			return 493.9;
		} else if (note.equalsIgnoreCase("c5")) {
			return 523.3;
		} else if (note.equalsIgnoreCase("c5sharp")) {
			return 554.4;
		} else if (note.equalsIgnoreCase("d5flat")) {
			return 554.4;
		} else if (note.equalsIgnoreCase("d5")) {
			return 587.3;
		} else if (note.equalsIgnoreCase("d5sharp")) {
			return 622.3;
		} else if (note.equalsIgnoreCase("e5flat")) {
			return 622.3;
		} else if (note.equalsIgnoreCase("e5")) {
			return 659.3;
		} else if (note.equalsIgnoreCase("f5")) {
			return 698.5;
		} else if (note.equalsIgnoreCase("f5sharp")) {
			return 740;
		}
		return -1;
	}

}
