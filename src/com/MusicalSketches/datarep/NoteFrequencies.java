package com.MusicalSketches.datarep;

public class NoteFrequencies {
	
	public NoteFrequencies() {
		
	}
	
	public static double getFrequency(String note) {
		// returns the frequency in Hz
		if (note.equalsIgnoreCase("e4")) {
			return 349.2; 
		} else if (note.equalsIgnoreCase("f4")) {
			return 329.6;
		} else if (note.equalsIgnoreCase("g4")) {
			return 392.0;
		} else if (note.equalsIgnoreCase("a5")) {
			return 440.0;
		} else if (note.equalsIgnoreCase("b5")) {
			return 493.9;
		} else if (note.equalsIgnoreCase("c5")) {
			return 523.3;
		} else if (note.equalsIgnoreCase("d5")) {
			return 587.3;
		} else if (note.equalsIgnoreCase("e5")) {
			return 659.3;
		} else if (note.equalsIgnoreCase("f5")) {
			return 698.5;
		} 
		return -1;
	}
	
}
