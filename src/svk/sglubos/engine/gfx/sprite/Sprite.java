package svk.sglubos.engine.gfx.sprite;

import java.awt.image.BufferedImage;

// TODO documentation


public class Sprite {
	
	private int width,height;
	private int[] pixels;
	private BufferedImage renderable;
	
	/**
	 * Constructs a Sprite object from integer array of pixels which can be rendered by rendering BufferedImage renderable.
	 * 
	 * @param width 
	 * @param height
	 * @param pixels
	 */
	
	public Sprite(int width, int height, int[] pixels){
		renderable = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		updateRenderable();
		
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	public void updateRenderable(){
		renderable.setRGB(0, 0, width, height, pixels, 0, width);
	}
	
	/**
	 * @return integer array containing pixels of sprite
	 */
	public int[] getPixels(){
		return pixels;
	}
	
	/**
	 * @return width of sprite
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
