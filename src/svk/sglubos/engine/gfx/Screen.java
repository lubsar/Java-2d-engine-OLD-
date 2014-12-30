package svk.sglubos.engine.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import svk.sglubos.engine.gfx.sprite.Sprite;

/**
 * Handles rendering of basic shapes, images, texts and sprites.
 * <code>BufferedImage</code> renderLayer contains all rendered graphics
 * and this <code>BufferedImage</code> is returned by method <code>getRenderLayer()</code>.
 *<p>
 * A java.awt.Graphics object which can draw on renderLayer is returned by method <code>getGraphics()</code>.
 * </p>
 * <p>
 * Before rendering game content every frame you need to call <code>prepare()</code> method, which creates new Graphics object
 * and fills entire screen with defaultColor passed in constructor of this class.  
 * After rendering game content you need to call <code>disposeGraphics()</code> method which disposes Graphics object to release system resources.
 * </p>
 * <h1>example: </h1>
 * <p>
 * <code>
 * 	public void render() {<br>
 * 		//start with preparation<br>
 * 		screen.prepare();<br>
 * 		<br>
 * 		//render content <br>
 * 		screen.renderFilledRectangle(0,0,50,50);<br>
 * 		<br>
 * 		//finish with dispose<br>
 * 		screen.dispose();<br>
 * }	
 * </code>
 * 
 */

//TODO documentation

public class Screen {
	/**
	 * Screen width in pixels.
	 */
	protected int width;
	/**
	 * Screen height in pixels.
	 */
	protected int height;
	
	/**
	 * Color used in <code>prepare()</code> method, entire screen is filled with this color.
	 * The value of this color is initialized in constructor and is last passed argument in constructor.
	 */
	protected Color defaultScreenColor;
	
	protected BufferedImage renderLayer;
	protected Graphics g;
	protected int[] pixels;
	
	public Screen(int width, int height, Color defaultColor) {
		renderLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) renderLayer.getRaster().getDataBuffer()).getData();

		g = renderLayer.createGraphics();

		this.width = width;
		this.height = height;
		this.defaultScreenColor = defaultColor;

		g.setFont(new Font("sans sernif", Font.BOLD, 30));
	}

	public void renderFilledRectangle(int x, int y, int width, int height,	Color color) {
		setColor(color);
		renderFilledRectangle(x, y, width, height);
	}

	public void renderFilledRectangle(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

	public void renderRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);
		g.drawRect(x, y, width, height);
	}

	public void renderRectangle(int width, int height, int x, int y) {
		g.drawRect(x, y, width, height);
	}

	public void renderImage(BufferedImage img, int x, int y) {
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}

	public void renderString(String text, int x, int y, Font font, Color color) {
		setColor(color);
		renderString(text, x, y, font);
	}

	public void renderString(String text, int x, int y, Font font) {
		setFont(font);
		g.drawString(text, x, y);
	}

	public void renderString(String text, int x, int y) {
		g.drawString(text, x, y);
	}

	public void renderOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderOval(x, y, width, height);
	}

	public void renderOval(int x, int y, int width, int height) {
		g.drawOval(x, y, width, height);
	}

	public void renderFiledOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderFiledOval(x, y, width, height);
	}

	public void renderFiledOval(int x, int y, int width, int height) {
		g.fillOval(x, y, width, height);
	}

	public void renderLine(int x, int y, int xa, int ya, Color color) {
		setColor(color);
		renderLine(x, y, xa, ya);
	}

	public void renderLine(int x, int y, int xa, int ya) {
		g.drawLine(x, y, xa, ya);
	}

	public void renderArc(int x, int y, int width, int height, int startAngle,	int arcAngle) {
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		renderArc(x, y, width, height, startAngle, arcAngle);
	}
	
	public void renderSprite(Sprite sprite, int x, int y){
		
	}
	
	public void prepare(){
		g = renderLayer.createGraphics();
		g.setColor(defaultScreenColor);
		g.fillRect(0, 0, width, height);
	}
	
	public void setColor(Color color) {
		if (color == null) {
			// TODO handle error
			return;
		}
		g.setColor(color);
	}

	public void setFont(Font font) {
		if (font == null) {
			// TODO handle error
			return;
		}
		g.setFont(font);
	}

	public Graphics getGraphics() {
		return g;
	}
	
	public void disposeGraphics() {
		g.dispose();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage getRenderLayer() {
		return renderLayer;
	}
}
