package musgen;
import javax.sound.midi.*;

import musgen.Theory.Keys;
import musgen.Theory.Harmonies;
import java.util.Random;
public class Generator {
	private static int randPitch(Keys key, int scaleDegree, int prevNote) {
		int pitch = -60;
		switch(key) {
		case A:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.aPitches[(prevNote - 1 + 7) % 7] : Theory.aPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.aPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case B:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.bPitches[(prevNote - 1 + 7) % 7] : Theory.bPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.bPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case C:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.cPitches[(prevNote - 1 + 7) % 7] : Theory.cPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.cPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case D:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.dPitches[(prevNote - 1 + 7) % 7] : Theory.dPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.dPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case E:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.ePitches[(prevNote - 1 + 7) % 7] : Theory.ePitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.ePitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case F:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.fPitches[(prevNote - 1 + 7) % 7] : Theory.fPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.fPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
			break;
		case G:
			pitch = ((int)(Math.random() * 4) > 1) ? ((Math.random() * 2 < 1) ? Theory.gPitches[(prevNote - 1 + 7) % 7] : Theory.gPitches[(prevNote + 1 + 7) % 7])
					: (Math.random() * 3 > 1 ? (Theory.gPitches[(int) (Math.random() * 7)]) : getChordPitches(key, scaleDegree)[(int) (Math.random()*3)]);
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
	}
		
	private static int[] getKeyPitches(Keys key) {
		
		int[] scale = null;
		
		switch(key) {
		case A:
			scale = Theory.aPitches;
			break;
		case B:
			scale = Theory.bPitches;
			break;
		case C:
			scale = Theory.cPitches;
			break;
		case D:
			scale = Theory.dPitches;
			break;
		case E:
			scale = Theory.ePitches;
			break;
		case F:
			scale = Theory.fPitches;
			break;
		case G:
			scale = Theory.gPitches;
			break;
		}
		
		return scale;
	}
	
	public static int[] getChordPitches(Keys key, int scaleDegree) {
		
		int[] notes = new int[3];
		
		switch(key) {
		case A:
			notes[0] = Theory.aPitches[scaleDegree];
			notes[1] = Theory.aPitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.aPitches[(scaleDegree + 4) % 7];
			break;
		case B:
			notes[0] = Theory.bPitches[scaleDegree];
			notes[1] = Theory.bPitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.bPitches[(scaleDegree + 4) % 7];
			break;
		case C:
			notes[0] = Theory.cPitches[scaleDegree];
			notes[1] = Theory.cPitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.cPitches[(scaleDegree + 4) % 7];
			break;
		case D:
			notes[0] = Theory.dPitches[scaleDegree];
			notes[1] = Theory.dPitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.dPitches[(scaleDegree + 4) % 7];
			break;
		case E:
			notes[0] = Theory.ePitches[scaleDegree];
			notes[1] = Theory.ePitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.ePitches[(scaleDegree + 4) % 7];
			break;
		case F:
			notes[0] = Theory.fPitches[scaleDegree];
			notes[1] = Theory.fPitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.fPitches[(scaleDegree + 4) % 7];
			break;
		case G:
			notes[0] = Theory.gPitches[scaleDegree];
			notes[1] = Theory.gPitches[(scaleDegree + 2) % 7];
			notes[2] = Theory.gPitches[(scaleDegree + 4) % 7];
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
	} 
	
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

		Track track = null;
		if(sq.getTracks().length == 0)
			track = sq.createTrack();
		else
			track = sq.getTracks()[0];
		
		int randPitch = 0;
		int prevNote = 0;
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
	 * @param melodyNum the track number of the melody in the sequence "melody"
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
				track.add(makeMidiEvent(144, 2, chordTones[channel] + 36, 65, subDivisions * i + 2));
				track.add(makeMidiEvent(128, 2, chordTones[channel] + 36, 65, subDivisions * (i + 1)));
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
				track.add(makeMidiEvent(144, 2, chordTones[channel] + 48, 75, subDivisions * i + 2));
				track.add(makeMidiEvent(128, 2, chordTones[channel] + 48, 75, subDivisions * (i + 1)));
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
	public static void play(int instrument1, int instrument2) {
		Random random = new Random();
		int beats = random.nextInt(61) + 60;
		int timeSig = 44; 
		int totalBeats = timeSig / 10;
		int subDivisions = timeSig % 10;
		int numMeasures = 8;
		Keys key = Keys.C;
		Sequence sq = null;
		try {
			sq = new Sequence(Sequence.PPQ, subDivisions);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		addRandomMelody(sq, key, 0, timeSig, numMeasures);
		addHarmony(sq, Harmonies.ARPEGGIATION, 0, key, 0, timeSig);
		try {
			
			MidiChannel[] midChannel;
			Synthesizer syn = MidiSystem.getSynthesizer();
			syn.open();
			
			midChannel = syn.getChannels();
			midChannel[1].programChange(instrument1);
			midChannel[2].programChange(instrument2);
			midChannel = syn.getChannels();
					
			Sequencer sqr = MidiSystem.getSequencer();
			Transmitter sqrTrans = sqr.getTransmitter();
			Receiver synthRcvr = syn.getReceiver();
			sqrTrans.setReceiver(synthRcvr);
			
			sqr.open();
			sq = WriteRandomMidi.combineSequences(WriteRandomMidi.makeRandomSong(WriteRandomMidi.getRandomKey(), WriteRandomMidi.getRandomChords(), 2));
			sqr.setSequence(sq);
			sqr.setTempoInBPM(beats);
			sqr.start();
		
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
}
