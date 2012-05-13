package com.MusicalSketches.datarep;

import java.util.HashMap;
import java.util.Map;

public class KeySignatures {
	public static Map<String, String[]> keyMap = new HashMap<String, String[]>();
	//map key signature names to list that contains whether sharps or flats (element 0) and the names of the notes.
	//we can figure out how to draw the sharps/flats with the positioning of the notes feature - we know what y to use. they'll need to be closer together than the notes are.
	
	static {
		keyMap.put("C", new String[]  {null});
		keyMap.put("G", new String[] {"Sharp", "f"});
		keyMap.put("D", new String[] {"Sharp", "f", "c"});
		keyMap.put("A", new String[] {"Sharp", "f", "c", "g"});
		keyMap.put("E", new String[] {"Sharp", "f", "c", "g", "d"});
		keyMap.put("B", new String[] {"Sharp", "f", "c", "g", "d", "a"});
		keyMap.put("F#", new String[] {"Sharp", "f", "c", "g", "d", "a", "e"});
		keyMap.put("F", new String[] {"Flat", "b"});
		keyMap.put("Bb", new String[] {"Flat", "b", "e"});
		keyMap.put("Eb", new String[] {"Flat", "b", "e", "a"});
		keyMap.put("Ab", new String[] {"Flat", "b", "e", "a", "d"});
		keyMap.put("Db", new String[] {"Flat", "b", "e", "a", "d", "g"});
		keyMap.put("Gb", new String[] {"Flat", "b", "e", "a", "d", "g", "c"});

		
		keyMap.put("A minor", new String[]  {null});
		keyMap.put("E minor", new String[] {"Sharp", "f"});
		keyMap.put("B minor", new String[] {"Sharp", "f", "c"});
		keyMap.put("F# minor", new String[] {"Sharp", "f", "c", "g"});
		keyMap.put("C# minor", new String[] {"Sharp", "f", "c", "g", "d"});
		keyMap.put("G# minor", new String[] {"Sharp", "f", "c", "g", "d", "a"});
		keyMap.put("D minor", new String[] {"Flat", "b"});
		keyMap.put("G minor", new String[] {"Flat", "b", "e"});
		keyMap.put("C minor", new String[] {"Flat", "b", "e", "a"});
		keyMap.put("F minor", new String[] {"Flat", "b", "e", "a", "d"});
		keyMap.put("Bb minor", new String[] {"Flat", "b", "e", "a", "d", "g"});
		keyMap.put("Eb minor", new String[] {"Flat", "b", "e", "a", "d", "g", "c"});
		
	}

}
