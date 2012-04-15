package com.MusicalSketches.datarep;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

public class Library implements Serializable{

	private static final long serialVersionUID = 5419219148601728866L;
	private ArrayList<Song> songs;

	public Library(ArrayList<Song> s) {
		this.songs = s;
	}

	public Library() {
		this.songs = new ArrayList<Song>();
	}

	// sorting methods?

	public void addSong(Song s) {
		Log.d("", "Song added");
		this.songs.add(s);
	}

	public ArrayList<Song> getSongs() {
		return this.songs;
	}

	public Song getSong(int index) {
		return this.songs.get(index);
	}

	public Song getSong(String title) {
		for (Song s : songs) {
			if (title.compareToIgnoreCase(s.getTitle()) == 0) {
				return s;
			}
		}
		return null;
	}

	public void remove(String title) {
		Song r = null;
		for (Song s : songs) {
			if (s.getTitle().equals(title)) {
				r = s;
			}
		}
		this.songs.remove(r);
	}

}
