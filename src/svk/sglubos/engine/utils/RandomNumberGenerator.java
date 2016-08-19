package svk.sglubos.engine.utils;

import java.util.Random;

public class RandomNumberGenerator {
	private static final Random random = new Random();
	
	public static double getRandomDouble() {
		return random.nextDouble();
	}
}
