package com.MusicalSketches.datarep;

import java.io.Serializable;

public class Note implements Serializable {
	
	private double pitch;
	private double length;
	private String name;

	public Note(double pitch, double length, String name){
		this.pitch = pitch;
		this.length = length;
		this.name = name;
	}
	
	public double getPitch(){
		return pitch;
	}
	public double getLength() {
		return length;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	public void setLength(double l) {
		this.length = l;
	}

}
