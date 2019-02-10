package musgen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.midi.*;
import javax.sound.midi.spi.MidiFileWriter;

import musgen.Generator;
import musgen.Generator.*;
import musgen.Theory.Harmonies;
import musgen.Theory.Keys;

public class WriteRandomMidi {

	protected static Keys getRandomKey() {
		
		int n = (int) (Math.random() * 7);
		Keys key = null;
		
		switch(n) {
		case 0:
			key = Keys.A;
			break;
		case 1:
			key = Keys.B;
			break;
		case 2:
			key = Keys.C;
			break;
		case 3:
			key = Keys.D;
			break;
		case 4:
			key = Keys.E;
			break;
		case 5:
			key = Keys.F;
			break;
		case 6:
			key = Keys.G;
			break;
		}
		
		return key;
		
	} // getRandomKey
	
	protected static int[] getRandomChords() {
		int[] chords = new int[4];
		chords[0] = 1;
		
		int sec = (int) (Math.random() * 3);
		switch(sec) {
		case 0:
			chords[1] = 4;
			break;
		case 1:
			chords[1] = 5;
			break;
		case 2:
			chords[1] = 6;
			break;
		}
		
		if(Math.random() * 8 < 1) {
			chords[2] = 1;
			chords[3] = 4;
			return chords;
		}
		
		int thir = (int) (Math.random() * 3);
		switch(thir) {
		case 0:
			chords[2] = 1;
			break;
		case 1:
			chords[2] = 5;
			break;
		case 2:
			chords[2] = 6;
			break;
		}
		
		if(Math.random() * 4 < 1) {
			chords[3] = 1;
			return chords;
		}
		
		int four = (int) (Math.random() * 3);
		switch(four) {
		case 0:
			chords[3] = 5;
			break;
		case 1:
			chords[3] = 6;
			break;
		case 2:
			chords[3] = 7;
			break;
		}
		
		return chords;
	}

	private static Harmonies getRandomHarmony() {
		
		int n = (int) (Math.random() * 5);
		Harmonies harm = null;
		
		switch(n) {
		case 0:
			harm = Harmonies.DIADS;
			break;
		case 1:
			harm = Harmonies.TRIADS;
			break;
		case 2:
			harm = Harmonies.ARPEGGIATION;
			break;
		case 3:
			harm = Harmonies.RHYTHMIC_DIADS;
			break;
		case 4:
			harm = Harmonies.RHYTHMIC_TRIADS;
			break;
		}
		
		return harm;
	}
	
	protected static Sequence combineSequences(Sequence[] seqs) throws InvalidMidiDataException {
		
		Sequence seq = new Sequence(Sequence.PPQ, 4);
		seq.createTrack();
		int totalTicks = 0;
		
		//for each sequence
		for(int i = 0; i < seqs.length; i++) {
			
			//track in each sequence
			totalTicks = (int) seq.getTracks()[0].ticks();
			Track[] tracks = seqs[i].getTracks();
			
			//for each track
			for(int j = 0; j < tracks.length; j++) {
				
				//for each midievent in the track
				for(int k = 0; k < tracks[j].size(); k++) {
					
					tracks[j].get(k).setTick(tracks[j].get(k).getTick() + totalTicks);
					seq.getTracks()[0].add(tracks[j].get(k));
				}
			}
		}
		
		return seq;
	}
	
	protected static Sequence[] makeRandomSong(Keys key, int[] chords, int measuresPerChord) throws InvalidMidiDataException {
		
		Sequence[] song = new Sequence[4];
		Harmonies harm = getRandomHarmony();
		for(int i = 0; i < 4; i++) {
			Sequence sq = new Sequence(Sequence.PPQ, 4);
			Generator.addRandomMelody(sq, key, chords[i] - 1, 44, measuresPerChord);
			Generator.addHarmony(sq, harm, 1, key, chords[i] - 1, 44);
			song[i] = sq;
		}
		
		return song;
	}
	
	public static void writeMidi(Sequence s, int bpm, String path) {
		
		//writes the file!
		try {
			MidiSystem.write(s, 0, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} 
