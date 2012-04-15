package com.MusicalSketches.datarep;

import java.util.ArrayList;

public class Library {
	
	private ArrayList<Song> songs;

	public Library(ArrayList <Song> s){
		this.songs = s;
	}
	
	//sorting methods?
	
	public void addSong(Song s){
		this.songs.add(s);
	}
	
	public ArrayList<Song> getSongs(){
		return this.songs;
	}
	
	public Song getSong(int index){
		return this.songs.get(index);
	}

}
