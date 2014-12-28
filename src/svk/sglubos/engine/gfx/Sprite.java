package svk.sglubos.engine.gfx;

import java.awt.image.BufferedImage;

// TODO documentation


public class Sprite {
	
	private int width,height;
	private int[] pixels;
	private BufferedImage renderable;
	
	/**
	 * Constructs a Sprite object from pixels which can be rendered by rendering BufferedImage renderable.
	 * 
	 * @param width 
	 * @param height
	 * @param pixels
	 */
	
	public Sprite(int width, int height, int[] pixels){
		renderable = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		renderable.setRGB(0, 0, width, height, pixels, 0, width);
		
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	/**
	 * @return integer array containing pixels of sprite
	 */
	public int[] getPixels(){
		return pixels;
	}
	
	/**
	 * @return with of sprite
	 */
	public int getWidth(){
		return width;
	}

	/**
	 * @return height of sprite
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * @return BufferedImage created from pixels
	 */
	public BufferedImage getRenderable() {
		return renderable;
	}
}
