package musgen;
import java.util.Random;
/**
 * Generates random titles for the created songs 
 * based on a bank of adjectives and nouns.
 * @author Andrew
 *
 */
public class MusicName {
	
	Random random = new Random();
	String[] adjectives = {"Agreeable","Amused","Brave","Calm","Comfortable","Delightful",
			"Eager","Energetic","Excited","Grateful","Jolly","Jovial","Lively",
			"Proud","Peaceful","Relieved","Silly","Thankful","Thrilled",
			"Victorious","Witty"};
	String[] nouns = {"Love","Fun","Wisdom","Knowledge","Information",
			"Help","Assistance","Courage","Bravery","Sastisfaction",
			"Curiosity","Aggression","Beauty","Freedom","Faith",
			"Grief","Guilt","Humor"};
	/**
	 * Returns a randomly created name.
	 * @return the name of the song
	 */
	public String makeTitle() {
		int adj = random.nextInt(adjectives.length);
		int noun = random.nextInt(nouns.length);
		return adjectives[adj] + " " + nouns[noun];
	}
}
