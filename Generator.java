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
	
	private static int randPitch(Keys key, int scaleDegree) {
		int pitch = -60;
		switch(key) {
		case A:
			pitch = ((Math.random() * 2 < 1 ? (aPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		case B:
			pitch = ((Math.random() * 2 < 1 ? (bPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		case C:
			pitch = ((Math.random() * 2 < 1 ? (cPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		case D:
			pitch = ((Math.random() * 2 < 1 ? (dPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		case E:
			pitch = ((Math.random() * 2 < 1 ? (ePitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		case F:
			pitch = ((Math.random() * 2 < 1 ? (fPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		case G:
			pitch = ((Math.random() * 2 < 1 ? (gPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]));
			break;
		} // switch
		
		// middle C or higher
		return pitch + 60;
	} // randPitch
	
	/*private static int getPosInArray(int[] arr, int num) {
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == num)
				return i;
		}
		
		return -1;
	} */// getPostInArray
	
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
	 * Adds a melody track (as track 1) to the specified sequence
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

		Track track = sq.createTrack();
		
		while(tickPlacement < totalTicks) {
			int randPitch = randPitch(key, root);
			int randLength = ((int) (subDivisions / 2)) * ((int) (Math.random() * totalBeats) + 1);
			if(totalTicks - tickPlacement < randLength) {
				randLength = totalTicks - tickPlacement;
			}
			track.add(makeMidiEvent(144, 1, randPitch, 100, tickPlacement));
			track.add(makeMidiEvent(128, 1, randPitch, 100, tickPlacement + randLength));
			tickPlacement += randLength;
		}
		
	} // addRandomMelody
	
	/**
	 * Adds a harmony to track 2 of the specified sequence
	 * 
	 * @param melody the sequence which is being harmonized
	 * @param harm the Harmony type from enum Harmonies
	 * @param melodyNum the track number of the melody in the sequence "melody"
	 * @param key the key of the melody (from enum Keys)
	 * @param root the scale degree from the key which is the root of this harmony (0-6)
	 * @param timeSig the time signature of the melody (put 44, thats the only one that works)
	 */
	public static void addHarmony(Sequence melody, Harmonies harm, int melodyNum, Keys key, int root, int timeSig) {
		
		Track track = melody.getTracks()[melodyNum];
		int[] chordTones = getChordPitches(key, root);
		
		int numBeats = timeSig / 10,
				subDivisions = timeSig % 10,
				totalTicks = (int) track.ticks(),
				numMeasures = (int) totalTicks / subDivisions / numBeats,
				t = 0;
		
		switch(harm) {
		case DIADS:
			t = (int)(Math.random() * 2);
			track.add(makeMidiEvent(144, 2, chordTones[0+t] + 48, 100, 0));
			track.add(makeMidiEvent(128, 2, chordTones[0+t] + 48, 100, totalTicks - 1));
			track.add(makeMidiEvent(144, 2, chordTones[1+t] + 48, 100, 0));
			track.add(makeMidiEvent(128, 2, chordTones[1+t] + 48, 100, totalTicks - 1));
			break;
			
		case TRIADS:
			for(int i = 0; i < 3; i++) {
				track.add(makeMidiEvent(144, 2, chordTones[i] + 48, 100, 0));
				track.add(makeMidiEvent(128, 2, chordTones[i] + 48, 100, totalTicks - 1));
			}
			break;
			
		case ARPEGGIATION:
			for(int i = 0; i < totalTicks / numBeats; i++) {
				track.add(makeMidiEvent(144, 2, chordTones[0] + 36, 65, subDivisions * i));
				track.add(makeMidiEvent(128, 2, chordTones[0] + 36, 65, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[1] + 36, 65, subDivisions * i + 1));
				track.add(makeMidiEvent(128, 2, chordTones[1] + 36, 65, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[2] + 36, 65, subDivisions * i + 2));
				track.add(makeMidiEvent(128, 2, chordTones[2] + 36, 65, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[1] + 36, 65, subDivisions * i + 3));
				track.add(makeMidiEvent(128, 2, chordTones[1] + 36, 65, subDivisions * (i + 1)));
			}
			break;
		case RHYTHMIC_DIADS:
			t = (int)(Math.random() * 2);
			track.add(makeMidiEvent(144, 2, chordTones[0] + 36, 80, 0));
			track.add(makeMidiEvent(128, 2, chordTones[0] + 36, 80, totalTicks - 1));
			for(int i = 0; i < totalTicks / numBeats; i++) {
				track.add(makeMidiEvent(144, 2, chordTones[0] + 48, 75, subDivisions * i));
				track.add(makeMidiEvent(128, 2, chordTones[0] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[1 + t] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, 2, chordTones[1 + t] + 48, 75, subDivisions * (i + 1)));
			}
			break;
			
		case RHYTHMIC_TRIADS:
			for(int i = 0; i < totalTicks / numBeats; i++) {
				track.add(makeMidiEvent(144, 2, chordTones[0] + 48, 75, subDivisions * i));
				track.add(makeMidiEvent(128, 2, chordTones[0] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[1] + 48, 75, subDivisions * i));
				track.add(makeMidiEvent(128, 2, chordTones[1] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[1] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, 2, chordTones[1] + 48, 75, subDivisions * (i + 1)));
				track.add(makeMidiEvent(144, 2, chordTones[2] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, 2, chordTones[2] + 48, 75, subDivisions * (i + 1)));
			}
			break;
			
		case PEDAL:
			track.add(makeMidiEvent(144, 2, chordTones[0] + 36, 75, 0));
			track.add(makeMidiEvent(144, 2, chordTones[0] + 36, 75, totalTicks - 1));
			break;
			
		case PEDAL_WHOLE:
			for(int i = 0; i < numMeasures; i++) {
				track.add(makeMidiEvent(144, 2, chordTones[0] + 36, 75, subDivisions * numBeats * i));
				track.add(makeMidiEvent(144, 2, chordTones[0] + 36, 75, subDivisions * numBeats * (i + 1) - 1));
			}
			break;
		}
	} // addHarmony
	
	public static void main(String[] args) {
		
		int bpm = 90;
		int timeSig = 44; //only works with simple time signatures (44, 34)
		int totalBeats = timeSig / 10;
		int subDivisions = timeSig % 10;
		int numMeasures = 2;
		Keys key = Keys.D;
		Sequence sq = null;
		try {
			sq = new Sequence(Sequence.PPQ, subDivisions);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		addRandomMelody(sq, key, 6, timeSig, numMeasures);
		addHarmony(sq, Harmonies.RHYTHMIC_DIADS, 0, key, 6, timeSig);
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
	}
}
