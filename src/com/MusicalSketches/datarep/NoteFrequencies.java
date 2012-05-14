package com.MusicalSketches.datarep;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NoteFrequencies {

	public static Map<String, Double> frequencyMap = new HashMap<String, Double>();
	public static Map<Double, String> freqToString = new HashMap<Double, String>();
	public static int[] staff_lines = new int[] { 247, 237, 227, 218, 208, 199,
			189, 180, 170, 160, 150, 140, 131 }; // from first line to fifth
													// line
	// we should somehow make this a switch statement so we can switch between
	// bass and treble
	public static String[] staff_notes = new String[] { "c4", "d4", "e4", "f4",
			"g4", "a4", "b4", "c5", "d5", "e5", "f5", "g5", "a5" };
	// bass representation
	public static String[] bass_staff_notes = new String[] { "e2", "f2", "g2",
			"a2", "b2", "c3", "d3", "e3", "f3", "g3", "a3", "b3", "c4" };

	static {
		// http://www.seventhstring.com/resources/notefrequencies.html
		// http://en.wikipedia.org/wiki/File:Bass_and_Treble_clef.svg
		frequencyMap.put("e2flat", 77.78);
		frequencyMap.put("e2", 82.41);
		frequencyMap.put("e2sharp", 87.31);
		frequencyMap.put("f2flat", 82.41);
		frequencyMap.put("f2", 87.31);
		frequencyMap.put("f2sharp", 92.5);
		frequencyMap.put("g2flat", 92.5);
		frequencyMap.put("g2", 98.0);
		frequencyMap.put("g2sharp", 103.8);
		frequencyMap.put("a2flat", 103.8);
		frequencyMap.put("a2", 110.0);
		frequencyMap.put("a2sharp", 116.5);
		frequencyMap.put("b2flat", 116.5);
		frequencyMap.put("b2", 123.5);
		frequencyMap.put("b2sharp", 130.8);
		//
		frequencyMap.put("c3flat", 123.5);
		frequencyMap.put("c3", 130.8);
		frequencyMap.put("c3sharp", 138.6);
		frequencyMap.put("d3flat", 138.6);
		frequencyMap.put("d3", 146.8);
		frequencyMap.put("d3sharp", 155.6);
		frequencyMap.put("e3flat", 155.6);
		frequencyMap.put("e3", 164.8);
		frequencyMap.put("e3sharp", 174.6);
		frequencyMap.put("f3flat", 164.8);
		frequencyMap.put("f3", 174.6);
		frequencyMap.put("f3sharp", 185.0);
		frequencyMap.put("g3flat", 185.0);
		frequencyMap.put("g3", 196.0);
		frequencyMap.put("g3sharp", 207.7);
		frequencyMap.put("a3flat", 207.7);
		frequencyMap.put("a3", 220.0);
		frequencyMap.put("a3sharp", 233.1);
		frequencyMap.put("b3flat", 233.1);
		frequencyMap.put("b3", 246.9);
		// frequencyMap.put("b3sharp", 261.6);
		//
		frequencyMap.put("c4flat", 246.9);
		frequencyMap.put("c4", 261.6);
		frequencyMap.put("c4sharp", 277.2);
		frequencyMap.put("d4flat", 277.2);
		frequencyMap.put("d4", 293.7);
		frequencyMap.put("d4sharp", 311.1);
		frequencyMap.put("e4flat", 311.1);
		frequencyMap.put("e4", 329.4);
		frequencyMap.put("e4sharp", 349.2);
		frequencyMap.put("f4flat", 329.4);
		frequencyMap.put("f4", 349.2);
		frequencyMap.put("f4sharp", 370.0);
		frequencyMap.put("g4flat", 370.0);
		frequencyMap.put("g4", 392.0);
		frequencyMap.put("g4sharp", 415.3);
		frequencyMap.put("a4flat", 415.3);
		frequencyMap.put("a4", 440.0);
		frequencyMap.put("a4sharp", 466.2);
		frequencyMap.put("b4flat", 466.2);
		frequencyMap.put("b4", 439.9);
		frequencyMap.put("b4sharp", 523.3);
		//
		frequencyMap.put("c5flat", 439.9);
		frequencyMap.put("c5", 523.3);
		frequencyMap.put("c5sharp", 554.4);
		frequencyMap.put("d5flat", 554.4);
		frequencyMap.put("d5", 587.3);
		frequencyMap.put("d5sharp", 622.3);
		frequencyMap.put("e5flat", 622.3);
		frequencyMap.put("e5", 659.3);
		frequencyMap.put("e5sharp", 698.5);
		frequencyMap.put("f5flat", 659.3);
		frequencyMap.put("f5", 698.5);
		frequencyMap.put("f5sharp", 740.0);
		frequencyMap.put("g5flat", 740.0);
		frequencyMap.put("g5", 784.0);
		frequencyMap.put("g5sharp", 830.6);
		frequencyMap.put("a5flat", 830.6);
		frequencyMap.put("a5", 880.0);
		frequencyMap.put("a5sharp", 932.3);

		Set<String> str = frequencyMap.keySet();
		Collection<Double> dubs = frequencyMap.values();
		Iterator<Double> dubit = dubs.iterator();
		while (dubit.hasNext()) {
			double d = dubit.next();
			Iterator<String> j = str.iterator();
			while (j.hasNext()) {
				String js = j.next();
				if (frequencyMap.get(js) == d) {
					freqToString.put(d, js);
				}
			}
		}
	}

	public static double getFrequency(String note) {
		// returns the frequency in Hz
		if (frequencyMap.containsKey(note)) {
			return frequencyMap.get(note).doubleValue();
		}
		return 440; // A4
	}

}
