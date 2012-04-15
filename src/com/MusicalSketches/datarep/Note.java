package com.MusicalSketches.datarep;

import java.io.Serializable;

public class Note implements Serializable {
	
	private double pitch;
	private double length;

	public Note(double pitch, double length){
		this.pitch = pitch;
		this.length = length;
	}
	
	public double getPitch(){
		return pitch;
	}
	public double getLength() {
		return length;
	}

}
