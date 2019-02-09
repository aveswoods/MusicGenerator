package musgen;

import javax.sound.midi.*;

public class Generator {
	
	//arrays for the pitch base values in each key
	//the last three are repeats to make chances of tonic, third, or fifth higher
	private static int[] aPitches = {1,2,4,6,8,9,11,9,1,4},
					 bPitches = {1,3,4,6,8,10,11,11,3,6},
					 cPitches = {0,2,4,5,7,9,11,0,4,7},
					 dPitches = {1,2,4,6,7,9,11,2,6,9},
					 ePitches = {1,3,4,6,8,9,11,4,8,11},
					 fPitches = {0,2,4,5,7,9,10,11,5,9,0},
					 gPitches = {0,2,4,6,7,9,11,7,11,2};
	
	//types of harmony structure
	public static enum Harmonies {
		DIADS, TRIADS, ARPEGIATION, RHYTHMIC_DIADS, RHYTHMIC_TRIADS;
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
	
	private static int randPitch(Keys key) {
		int pitch = -60;
		switch(key) {
		case A:
			pitch = (aPitches[(int) (Math.random() * 10)]);
			break;
		case B:
			pitch = (bPitches[(int) (Math.random() * 10)]);
			break;
		case C:
			pitch = (cPitches[(int) (Math.random() * 10)]);
			break;
		case D:
			pitch = dPitches[(int) (Math.random() * 10)];
			break;
		case E:
			pitch = ePitches[(int) (Math.random() * 10)];
			break;
		case F:
			pitch = fPitches[(int) (Math.random() * 10)];
			break;
		case G:
			pitch = gPitches[(int) (Math.random() * 10)];
			break;
		} // switch
		
		// middle C or higher
		return pitch + 60;
	}
	private static int getPosInArray(int[] arr, int num) {
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == num)
				return i;
		}
		
		return -1;
	}
	private static int[] getChordPitches(Keys key, int pitch) {
		
		int pos = 0;
		int[] notes = new int[3];
		
		switch(key) {
		case A:
			pos = getPosInArray(aPitches,pitch);
			notes[0] = aPitches[pos];
			notes[1] = aPitches[(pos + 2) % 7];
			notes[2] = aPitches[(pos + 4) % 7];
			break;
		case B:
			pos = getPosInArray(bPitches,pitch);
			notes[0] = bPitches[pos];
			notes[1] = bPitches[(pos + 2) % 7];
			notes[2] = bPitches[(pos + 4) % 7];
			break;
		case C:
			pos = getPosInArray(cPitches,pitch);
			notes[0] = cPitches[pos];
			notes[1] = cPitches[(pos + 2) % 7];
			notes[2] = cPitches[(pos + 4) % 7];
			break;
		case D:
			pos = getPosInArray(dPitches,pitch);
			notes[0] = dPitches[pos];
			notes[1] = dPitches[(pos + 2) % 7];
			notes[2] = dPitches[(pos + 4) % 7];
			break;
		case E:
			pos = getPosInArray(ePitches,pitch);
			notes[0] = ePitches[pos];
			notes[1] = ePitches[(pos + 2) % 7];
			notes[2] = ePitches[(pos + 4) % 7];
			break;
		case F:
			pos = getPosInArray(fPitches,pitch);
			notes[0] = fPitches[pos];
			notes[1] = fPitches[(pos + 2) % 7];
			notes[2] = fPitches[(pos + 4) % 7];
			break;
		case G:
			pos = getPosInArray(gPitches,pitch);
			notes[0] = gPitches[pos];
			notes[1] = gPitches[(pos + 2) % 7];
			notes[2] = gPitches[(pos + 4) % 7];
			break;
		}
		
		return notes;
	}
	
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
	
	public static Sequence addRandomMelody(Sequence sq, Keys key, int timeSig,  int numMeasures) {
		
		// makes a sequence dependent on a time signature
		// and number of measures
		int totalBeats = timeSig / 10,
			subDivisions = timeSig % 10,
			totalTicks = totalBeats * subDivisions * numMeasures,
			tickPlacement = 0;

		Track track = sq.createTrack();
		
		while(tickPlacement < totalTicks) {
			int randPitch = randPitch(key);
			int randLength = ((int) (subDivisions / 2)) * ((int) (Math.random() * totalBeats) + 1);
			if(totalTicks - tickPlacement < randLength) {
				randLength = totalTicks - tickPlacement;
			}
			track.add(makeMidiEvent(144, 1, randPitch, 100, tickPlacement));
			track.add(makeMidiEvent(128, 1, randPitch, 100, tickPlacement + randLength));
			tickPlacement += randLength;
		}
		
		return sq;
		
	} // makeRandomSequence
	
	public static void addHarmony(Sequence melody, Harmonies harm, int melodyNum, Keys key, int root, int timeSig) {
		
		Track track = melody.getTracks()[melodyNum];
		int[] chordTones = getChordPitches(key, root);
		
		int totalBeats = timeSig / 10,
				subDivisions = timeSig % 10,
				totalTicks = (int) track.ticks(),
				tickPlacement = 0;
		
		switch(harm) {
		case DIADS:
			int t = (int)(Math.random() * 2);
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
		
		}
		
	}
	
	public static void main(String[] args) {
		
		int bpm = 100;
		int timeSig = 44;
		int totalBeats = timeSig / 10;
		int subDivisions = timeSig % 10;
		int numMeasures = 2;
		Keys key = Keys.B;
		Sequence sq = null;
		try {
			sq = new Sequence(Sequence.PPQ, subDivisions);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		addRandomMelody(sq, key, timeSig, numMeasures);
		addHarmony(sq, Harmonies.TRIADS, 0, key, 11, timeSig);
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
