package musgen;

import javax.sound.midi.*;

public class Generator {
	
	//arrays for the pitch base values in each key
	private static int[] aPitches = {1,2,4,6,8,9,11},
					 bPitches = {1,3,4,6,8,10,11},
					 cPitches = {0,2,4,5,7,9,11},
					 dPitches = {1,2,4,6,7,9,11},
					 ePitches = {1,3,4,6,8,9,11},
					 fPitches = {0,2,4,5,7,9,10,11},
					 gPitches = {0,2,4,6,7,9,11};
	
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
		int pitch = -1;
		switch(key) {
		case A:
			pitch = (aPitches[(int) (Math.random() * 7)]);
			break;
		case B:
			pitch = (bPitches[(int) (Math.random() * 7)]);
			break;
		case C:
			pitch = (cPitches[(int) (Math.random() * 7)]);
			break;
		case D:
			pitch = dPitches[(int) (Math.random() * 7)];
			break;
		case E:
			pitch = ePitches[(int) (Math.random() * 7)];
			break;
		case F:
			pitch = fPitches[(int) (Math.random() * 7)];
			break;
		case G:
			pitch = gPitches[(int) (Math.random() * 7)];
			break;
		} // switch
		
		// middle C or higher
		return pitch + 60;
	}
	
	private static Sequence makeRandomSequence(Keys key, int numNotes, int subDivisions) {
		
		// makes a sequence dependent on beats per minute
		// and number of ticks per beat
		Sequence sq = null;
		try {
			
			sq = new Sequence(Sequence.PPQ, subDivisions);
			Track track = sq.createTrack();
			
			for(int i = 0; i < numNotes; i++) {
				int randPitch = randPitch(key);
				track.add(makeMidiEvent(144, 1, randPitch, 100, (i * subDivisions) + subDivisions));
				track.add(makeMidiEvent(128, 1, randPitch, 100, (i * subDivisions + 3) + subDivisions));
			}
			
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
		return sq;
		
	}
	//144 = NOTE_ON
	//128 = NOTE_OFF
	private static MidiEvent makeMidiEvent(int command, int channel, int pitch, int velocity, int tick) {
		
		MidiEvent event = null;
		try {
			ShortMessage message = new ShortMessage(command, channel, pitch, velocity);
			event = new MidiEvent(message, tick);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return event;
	}
	
	public static void main(String[] args) {
		
		int bpm = 180;
		int subDivisions = 3;
		int numNotes = 20;
		Keys key = Keys.G;
		try {
			Sequencer sqr = MidiSystem.getSequencer();
			sqr.open();
			sqr.setSequence(makeRandomSequence(key, numNotes, subDivisions));
			sqr.setTempoInBPM(bpm);
			sqr.start();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
	}
}
