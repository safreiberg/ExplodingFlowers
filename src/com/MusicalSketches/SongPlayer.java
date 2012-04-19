package com.MusicalSketches;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.MusicalSketches.datarep.Note;
import com.MusicalSketches.datarep.Song;

public class SongPlayer extends Activity {
	Song song;
	// originally from
	// http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
	// and modified by Steve Pomeroy <steve@staticfree.info>
	Handler handler = new Handler();
	int sampleRate = 8000;
	byte[] generatedSnd;
	int numSamples;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.song = new Song();
		this.song.addNote(new Note(587.3, 1, "d5"));
		this.song.addNote(new Note(440, 1, "a4"));
		this.song.addNote(new Note(392, 1, "g4"));
		this.song.addNote(new Note(440, 1, "a4"));
		start();
	}

	private void start() {
		// Use a new tread as this can take a while
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				genTone();
				handler.post(new Runnable() {

					public void run() {
						playSound();
					}
				});
			}
		});
		thread.start();
	}

	void genTone() {
		double duration = 0; // seconds
		for (Note n : song.getNotes().getNotes()) {
			duration += n.getLength(); // TODO tempo!!
		}
		numSamples = (int) (duration * sampleRate);
		double[] sample = new double[numSamples];
		generatedSnd = new byte[2 * numSamples];
		int j = 0;
		for (Note n : song.getNotes().getNotes()) {
			Log.d("", "Pitch: " + n.getPitch());
			double freqOfTone = n.getPitch(); // hz
			int numThisNote = (int) (n.getLength() * sampleRate);
			Log.d("", "J is: " + j);
			// fill out the array
			for (int i = 0; i < numThisNote; ++i) {
				sample[j] = Math.sin(2 * Math.PI * i
						/ (sampleRate / freqOfTone));
				j++;
			}
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (final double dVal : sample) {
			// scale to maximum amplitude
			final short val = (short) ((dVal * 32767));
			// in 16 bit wav PCM, first byte is the low order byte
			generatedSnd[idx++] = (byte) (val & 0x00ff);
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

		}
	}

	void playSound() {
		Log.wtf("", "Buffer size in bytes: " + numSamples);
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_DEFAULT,
				AudioFormat.ENCODING_PCM_16BIT, 2 * numSamples,
				AudioTrack.MODE_STATIC);
		Log.wtf("", "GenSnd length: " + generatedSnd.length);
		audioTrack.write(generatedSnd, 0, generatedSnd.length);
		audioTrack.setPlaybackRate(8000);
		audioTrack.play();
	}

}
