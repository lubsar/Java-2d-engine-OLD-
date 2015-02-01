package svk.sglubos.engine.utils;

import java.util.Random;

//TODO implement own algorithm
public class RandomNumberGenerator {
	private static final Random random = new Random();
	
	public static double getRandomDouble() {
		return random.nextDouble();
	}
}
