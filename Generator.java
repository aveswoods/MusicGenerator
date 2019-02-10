package musgen;

import javax.sound.midi.*;

public class Generator {
	
	//arrays for the pitch base values in each key
	//the last three are repeats to make chances of tonic, third, or fifth higher
	private static int[] aPitches = {1,2,4,6,8,9,11},
					 bPitches = {11,1,3,4,6,8,10},
					 cPitches = {0,2,4,5,7,9,11},
					 dPitches = {2,4,6,7,9,11,1},
					 ePitches = {4,6,8,9,11,1,3},
					 fPitches = {5,7,9,10,0,2,4},
					 gPitches = {7,9,11,0,2,4,6};
	
	//types of harmony structure
	public static enum Harmonies {
		DIADS, TRIADS, ARPEGGIATION, RHYTHMIC_DIADS, RHYTHMIC_TRIADS,
		PEDAL, PEDAL_WHOLE;
	}
	
	// only major keys for now
	public enum Keys {
		A, //1,2,4,6,8,9,11
		B, //1,3,4,6,8,10,11
		C, //0,2,4,5,7,9,11
		D, //1,2,4,6,7,9,11
		E, //1,3,4,6,8,9,11
		F, //0,2,4,5,7,9,10,11
		G; //0,2,4,6,7,9,11
	}
	
	private static int randPitch(Keys key, int scaleDegree, int prevNote) {
		int pitch = -60;
		switch(key) {
		case A:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? aPitches[(prevNote - 1 + 7) % 7] : aPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (aPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case B:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? bPitches[(prevNote - 1 + 7) % 7] : bPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (bPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case C:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? cPitches[(prevNote - 1 + 7) % 7] : cPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (cPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case D:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? dPitches[(prevNote - 1 + 7) % 7] : dPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (dPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case E:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? ePitches[(prevNote - 1 + 7) % 7] : ePitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (ePitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case F:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? fPitches[(prevNote - 1 + 7) % 7] : fPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (fPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case G:
			pitch = ((int)(Math.random() * 2) == 1) ? ((Math.random() * 2 < 1) ? gPitches[(prevNote - 1 + 7) % 7] : gPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (gPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		} // switch
		
		// middle C or higher
		return pitch + 60;
	} // randPitch
	
	private static int getPosInArray(int[] arr, int num) {
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == num)
				return i;
		}
		
		return -1;
	} // getPostInArray
	
	private static int[] getKeyPitches(Keys key) {
		
		int[] scale = null;
		
		switch(key) {
		case A:
			scale = aPitches;
			break;
		case B:
			scale = bPitches;
			break;
		case C:
			scale = cPitches;
			break;
		case D:
			scale = dPitches;
			break;
		case E:
			scale = ePitches;
			break;
		case F:
			scale = fPitches;
			break;
		case G:
			scale = gPitches;
			break;
		}
		
		return scale;
	}
	
	private static int[] getChordPitches(Keys key, int scaleDegree) {
		
		int[] notes = new int[3];
		
		switch(key) {
		case A:
			notes[0] = aPitches[scaleDegree];
			notes[1] = aPitches[(scaleDegree + 2) % 7];
			notes[2] = aPitches[(scaleDegree + 4) % 7];
			break;
		case B:
			notes[0] = bPitches[scaleDegree];
			notes[1] = bPitches[(scaleDegree + 2) % 7];
			notes[2] = bPitches[(scaleDegree + 4) % 7];
			break;
		case C:
			notes[0] = cPitches[scaleDegree];
			notes[1] = cPitches[(scaleDegree + 2) % 7];
			notes[2] = cPitches[(scaleDegree + 4) % 7];
			break;
		case D:
			notes[0] = dPitches[scaleDegree];
			notes[1] = dPitches[(scaleDegree + 2) % 7];
			notes[2] = dPitches[(scaleDegree + 4) % 7];
			break;
		case E:
			notes[0] = ePitches[scaleDegree];
			notes[1] = ePitches[(scaleDegree + 2) % 7];
			notes[2] = ePitches[(scaleDegree + 4) % 7];
			break;
		case F:
			notes[0] = fPitches[scaleDegree];
			notes[1] = fPitches[(scaleDegree + 2) % 7];
			notes[2] = fPitches[(scaleDegree + 4) % 7];
			break;
		case G:
			notes[0] = gPitches[scaleDegree];
			notes[1] = gPitches[(scaleDegree + 2) % 7];
			notes[2] = gPitches[(scaleDegree + 4) % 7];
			break;
		}
		
		return notes;
	} // getChordPitches
	
	private static MidiEvent makeMidiEvent(int command, int channel, int pitch, int velocity, int tick) {
		
		MidiEvent event = null;
		try {
			ShortMessage message = new ShortMessage(command, channel, pitch, velocity);
			event = new MidiEvent(message, tick);
		} catch(Exception e) {
			e.printStackTrace();
		}
			
		return event;
	} // makeMidiEvent
	
	/**
	 * Adds a melody track (as track 0) to the specified sequence
	 * 
	 * @param sq the sequence track where the melody is being written on
	 * @param key the key the melody will be generated in
	 * @param root the scale degree which is the root of the melody (0-6)
	 * @param timeSig the time signature the melody will be generated in (only 44 works)
	 * @param numMeasures the number of measures that the melody will be
	 */
	public static void addRandomMelody(Sequence sq, Keys key, int root, int timeSig,  int numMeasures) {
		
		// makes a sequence dependent on a time signature
		// and number of measures
		int totalBeats = timeSig / 10,
			subDivisions = timeSig % 10,
			totalTicks = totalBeats * subDivisions * numMeasures,
			tickPlacement = 0;

		Track track = null;
		if(sq.getTracks().length == 0)
			track = sq.createTrack();
		else
			track = sq.getTracks()[0];
		
		int randPitch = 0;
		int prevNote = -1;
		int[] scale = getKeyPitches(key);
		while(tickPlacement < totalTicks) {
			randPitch = randPitch(key, root, prevNote);
			prevNote = getPosInArray(scale, randPitch);
			int randLength = ((int) (subDivisions / 2)) * ((int) (Math.random() * totalBeats) + 1);
			if(totalTicks - tickPlacement < randLength) {
				randLength = totalTicks - tickPlacement;
			}
			track.add(makeMidiEvent(144, 0, randPitch, 100, tickPlacement));
			track.add(makeMidiEvent(128, 0, randPitch, 100, tickPlacement + randLength));
			tickPlacement += randLength;
		}
		
	} // addRandomMelody
	
	/**
	 * Adds a harmony to track 2 of the specified sequence
	 * 
	 * @param melody the sequence which is being harmonized
	 * @param harm the Harmony type from enum Harmonies
	 * @param channel the channel that the harmony is placed in (1-15)
	 * @param key the key of the melody (from enum Keys)
	 * @param root the scale degree from the key which is the root of this harmony (0-6)
	 * @param timeSig the time signature of the melody (put 44, thats the only one that works)
	 */
	public static void addHarmony(Sequence melody, Harmonies harm, int channel, Keys key, int root, int timeSig) {
		
		Track track = melody.getTracks()[0];
		int[] chordTones = getChordPitches(key, root);
		
		int numBeats = timeSig / 10,
				subDivisions = timeSig % 10,
				totalTicks = (int) track.ticks(),
				numMeasures = (int) totalTicks / subDivisions / numBeats,
				t = 0;
		
		switch(harm) {
		case DIADS:
			t = (int)(Math.random() * 2);
			for(int i = 0; i < numMeasures; i++) {
				track.add(makeMidiEvent(144, channel, chordTones[0+t] + 48, 90, subDivisions * numBeats * i));
				track.add(makeMidiEvent(128, channel, chordTones[0+t] + 48, 90, subDivisions * numBeats * (i + 1) - 1));
				track.add(makeMidiEvent(144, channel, chordTones[1+t] + 48, 90, subDivisions * numBeats * i));
				track.add(makeMidiEvent(128, channel, chordTones[1+t] + 48, 90, subDivisions * numBeats * (i + 1) - 1));
			}
			break;
			
		case TRIADS:
			for(int i = 0; i < numMeasures; i++) {
				for(int j = 0; j < 3; j++) {
					track.add(makeMidiEvent(144, channel, chordTones[j] + 48, 85, subDivisions * numBeats * i));
					track.add(makeMidiEvent(128, channel, chordTones[j] + 48, 85, subDivisions * numBeats * (i + 1) - 1));
				}
			}
			break;
			
		case ARPEGGIATION:
			t = (int)(Math.random() * 2);
			for(int i = 0; i < totalTicks / numBeats; i++) {
				track.add(makeMidiEvent(144, channel, chordTones[0] + 48, 70, subDivisions * i));
				track.add(makeMidiEvent(128, channel, chordTones[0] + 48, 70, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[1] + 48, 70, subDivisions * i + 1));
				track.add(makeMidiEvent(128, channel, chordTones[1] + 48, 70, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[2] + 48, 70, subDivisions * i + 2));
				track.add(makeMidiEvent(128, channel, chordTones[2] + 48, 70, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[1] + 48, 70, subDivisions * i + 3));
				track.add(makeMidiEvent(128, channel, chordTones[1] + 48, 70, subDivisions * (i + 1)));
			}
			break;
		case RHYTHMIC_DIADS:
			t = (int)(Math.random() * 2);
			for(int i = 0; i < totalTicks / numBeats; i++) {
				track.add(makeMidiEvent(144, channel, chordTones[0] + 48, 75, subDivisions * i));
				track.add(makeMidiEvent(128, channel, chordTones[0] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[1 + t] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, channel, chordTones[1 + t] + 48, 75, subDivisions * (i + 1)));
			}
			break;
			
		case RHYTHMIC_TRIADS:
			for(int i = 0; i < totalTicks / numBeats; i++) {
				track.add(makeMidiEvent(144, channel, chordTones[0] + 48, 75, subDivisions * i));
				track.add(makeMidiEvent(128, channel, chordTones[0] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[1] + 48, 75, subDivisions * i));
				track.add(makeMidiEvent(128, channel, chordTones[1] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[1] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, channel, chordTones[1] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, channel, chordTones[2] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, channel, chordTones[2] + 48, 75, subDivisions * (i + 1)));
			}
			break;
			
		case PEDAL:
			track.add(makeMidiEvent(144, channel, chordTones[0] + 36, 75, totalTicks));
			track.add(makeMidiEvent(128, channel, chordTones[0] + 36, 75, totalTicks + totalTicks - 1));
			break;
			
		case PEDAL_WHOLE:
			for(int i = 0; i < numMeasures; i++) {
				track.add(makeMidiEvent(144, channel, chordTones[0] + 36, 75, subDivisions * numBeats * i));
				track.add(makeMidiEvent(128, channel, chordTones[0] + 36, 75, subDivisions * numBeats * (i + 1) - 1));
			}
			break;
		}
	} // addHarmony
	
	/*public static void main(String[] args) {
		
		int bpm = 180;
		int timeSig = 44; //only works with simple time signatures (44, 34)
		int totalBeats = timeSig / 10;
		int subDivisions = timeSig % 10;
		int numMeasures = 4;
		Keys key = Keys.B;
		Sequence sq = null;
		try {
			sq = new Sequence(Sequence.PPQ, subDivisions);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		addRandomMelody(sq, key, 0, timeSig, numMeasures);
		addHarmony(sq, Harmonies.TRIADS, 1, key, 0, timeSig);
		//addHarmony(sq, Harmonies.PEDAL_WHOLE, 0, key, 0, timeSig);
		try {
			Sequencer sqr = MidiSystem.getSequencer();
			sqr.open();
			sqr.setSequence(sq);
			sqr.setTempoInBPM(bpm);
			sqr.start();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}*/
}
