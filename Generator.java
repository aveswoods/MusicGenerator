package musgen;

import javax.sound.midi.*;

public class Generator {
	
	//arrays for the pitch base values in each key
	private String[] aPitches = "1,2,4,6,8,9,11".split(","),
					 bPitches = "1,3,4,6,8,10,11".split(","),
					 cPitches = "0,2,4,5,7,9,11".split(","),
					 dPitches = "1,2,4,6,7,9,11".split(","),
					 ePitches = "1,3,4,6,8,9,11".split(","),
					 fPitches = "0,2,4,5,7,9,10,11".split(","),
					 gPitches = "0,2,4,6,7,9,11".split(",");
	
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
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 0 || pitch == 3 || pitch == 5 || pitch == 7 || pitch == 10) {
				pitch = (int) (Math.random() * 12);
			}
		case B:
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 0 || pitch == 2 || pitch == 5 || pitch == 7 || pitch == 9) {
				pitch = (int) (Math.random() * 12);
			}
		case C:
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 1 || pitch == 3 || pitch == 6 || pitch == 8 || pitch == 10) {
				pitch = (int) (Math.random() * 12);
			}
		case D:
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 0 || pitch == 3 || pitch == 5 || pitch == 8 || pitch == 10) {
				pitch = (int) (Math.random() * 12);
			}
		case E:
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 0 || pitch == 2 || pitch == 5 || pitch == 7 || pitch == 10) {
				pitch = (int) (Math.random() * 12);
			}
		case F:
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 1 || pitch == 3 || pitch == 6 || pitch == 8 || pitch == 11) {
				pitch = (int) (Math.random() * 12);
			}
		case G:
			pitch = (int) (Math.random() * 12);
			while(pitch == -1 || pitch == 1 || pitch == 3 || pitch == 5 || pitch == 8 || pitch == 10) {
				pitch = (int) (Math.random() * 12);
			}
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
