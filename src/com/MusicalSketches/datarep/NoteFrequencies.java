package com.MusicalSketches.datarep;

import java.util.HashMap;
import java.util.Map;

public class NoteFrequencies {

	public static Map<String, Double> frequencyMap = new HashMap<String, Double>();
	public static int[] staff_lines = new int[] { 227, 218, 208, 199, 189, 180,
			160, 150 }; // from first line to fifth line
	static {
		frequencyMap.put("e4", 329.4);
		frequencyMap.put("f4", 349.2);
		frequencyMap.put("f4sharp", 369.9);
		frequencyMap.put("g4sharp", 369.9);
		frequencyMap.put("g4", 392.0);
		frequencyMap.put("g4sharp", 415.3);
		frequencyMap.put("a4flat", 415.3);
		frequencyMap.put("a4", 440.0);
		frequencyMap.put("a4sharp", 466.2);
		frequencyMap.put("b4flat", 466.2);
		frequencyMap.put("b4", 439.9);
		frequencyMap.put("c5", 523.3);
		frequencyMap.put("c5sharp", 554.4);
		frequencyMap.put("d5flat", 554.4);
		frequencyMap.put("d5", 587.3);
		frequencyMap.put("d5sharp", 622.3);
		frequencyMap.put("e5flat", 622.3);
		frequencyMap.put("e5", 659.3);
		frequencyMap.put("f5", 698.5);
		frequencyMap.put("f5sharp", 740.0);
	}

	public static double getFrequency(String note) {
		// returns the frequency in Hz
		if (frequencyMap.containsKey(note)) {
			return frequencyMap.get(note).doubleValue();
		}
		return 440; // A4
	}

}
