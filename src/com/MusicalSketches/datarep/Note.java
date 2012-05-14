package com.MusicalSketches.datarep;

import java.io.Serializable;

public class Note implements Serializable {
	
	private double pitch;
	private double length;
	private String dynamic;
	private String name;
	private boolean rest = false;

	public Note(double pitch, double length, String name){
		this.pitch = pitch;
		this.length = length;
		this.name = name;
		this.dynamic = null;
	}
	
	public double getPitch(){
		return pitch;
	}
	public String getDynamic() {
		return this.dynamic;
	}
	public void setRest(boolean r) {
		this.rest = r;
	}
	public boolean isRest() {
		return this.rest;
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
	public void setDynamic(String s){
		this.dynamic = s;
	}
	public void removeDynamic() {
		this.dynamic = "";
	}

}
