package musgen;
/**
 * A class that represents the different attributes 
 * of the music.
 * @author parkerciaramella, zanbeaver, andrewdecker
 *
 */
public class Theory {
		/**
		 * Represents the notes in the major scales.
		 */
		public static int[] aPitches = {1,2,4,6,8,9,11},
						 bPitches = {11,1,3,4,6,8,10},
						 cPitches = {0,2,4,5,7,9,11},
						 dPitches = {2,4,6,7,9,11,1},
						 ePitches = {4,6,8,9,11,1,3},
						 fPitches = {5,7,9,10,0,2,4},
						 gPitches = {7,9,11,0,2,4,6};
		/**
		 * Represents different harmonies.
		 * @author parkerciaramella, zanbeaver, andrewdecker
		 *
		 */
		public static enum Harmonies {
			DIADS, TRIADS, ARPEGGIATION, RHYTHMIC_DIADS, RHYTHMIC_TRIADS,
			PEDAL, PEDAL_WHOLE;
		}
		/**
		 * Represents the major keys.
		 * @author parkerciaramella, zanbeaver, andrewdecker
		 *
		 */
		public static enum Keys {
			/** A-Major Scale*/
			A, 
			/** B-Major Scale*/
			B,
			/** C-Major Scale*/
			C,
			/** D-Major Scale*/
			D, 
			/** E-Major Scale*/
			E, 
			/** F-Major Scale*/
			F, 
			/** G-Major Scale*/
			G; 
		}
	
}

