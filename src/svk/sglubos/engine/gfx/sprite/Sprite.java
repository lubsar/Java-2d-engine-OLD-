package svk.sglubos.engine.gfx.sprite;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;

/**
 * Contains integer array of pixels which can be rendered on {@link svk.sglubos.engine.gfx.Screen Screen}. <br>
 * 
 * This class provides methods which can change this pixels.
 *
 *@see svk.sglubos.engine.gfx.Screen
 */

public class Sprite {
	/**
	 * Width of sprite in pixels initialized in {@link #Sprite(int, int, int[]) constructor} to value passed as parameter.
	 */
	protected int width;
	
	/**
	 * Height of sprite int pixels initialized in {@link #Sprite(int, int, int[]) constructor} to value passed as parameter.
	 */
	protected int height;
	
	/**
	 * Contains pixels which can be rendered on Screen.
	 */
	protected int[] pixels;
	
	/**
	 * Constructs a Sprite object which contains integer array of pixels which can be rendered on {@link svk.sglubos.engine.gfx.Screen Screen}.
	 * 
	 * @param width determines width of sprite
	 * @param height determines height of sprite
	 * @param pixels actual pixels of sprite
	 * 
	 * @see svk.sglubos.engine.gfx.Screen
	 */
	public Sprite(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	/**
	 * @return integer array containing pixels of sprite
	 */
	public int[] getPixels() {
		return pixels;
	}
	
	/**
	 * @return width of sprite
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return height of sprite
	 */
	public int getHeight() {
		return height;
	}
	//TODO document
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(getClass(), hashCode());
		ret.appendTab();
		ret.append("width = " + width, " height = " + height);
		ret.appendLineSeparator();
		ret.appendObjectToStringTabln("pixels = ", pixels);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
