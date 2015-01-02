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
 * A java.awt.Graphics object which can drawn on renderLayer is returned by method <code>getGraphics()</code>.
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
 * <p>
 * @see java.awt.Graphics
 * @see svk.sglubos.engine.gfx.sprite.Sprite
	
 * @see {@link #prepare()}
 * @see {@link #disposeGraphics()}
 */

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
	 * Horizontal offset of screen in pixels. Default value 0.<br>
	 * Offset can be set by {@link #setOffset(int, int) setOffset(xOffset, yOffset)} method.
	 * 
	 * @see {@link #setOffset(int xOffset,int yOffset)}
	 */
	protected int xOffset = 0;
	
	/**
	 * Vertical offset of screen in pixels. Default value 0.<br>
	 * Offset can be set by {@link #setOffset(int, int) setOffset(xOffset, yOffset)} method.
	 * 
	 * @see {@link #setOffset(int xOffset,int yOffset)}
	 */
	protected int yOffset = 0;
	
	/**
	 * If true offsets are ignored when rendering some content, else position of rendered content is offset. <br>
	 * Can be set by {@link #setIngoreOffset(boolean) setIgnoreOffset(boolean ignoreOffset)} method.
	 * 
	 *  @see {@link #xOffset xOffset}
	 *  @see {@link #yOffset yOffset}
	 *  @see {@link #setOffset(int, int) setOffset(xOffset, yOffset)}
	 */
	protected boolean ignoreOffset;
	
	/**
	 * Color used in {@link #prepare()} method, entire screen is filled with this color when {@link #prepare()} is called.<br>
	 * The value of this color is initialized in constructor and is last passed argument in {@link #Screen(int, int, Color) constructor}.
	 */
	protected Color defaultScreenColor;
	
	/**
	 * BufferedImage which contains all rendered graphics. To display your rendered graphics display this image.<br>
	 * It is initialized in {@link #Screen(int, int, Color) constructor}.<br>
	 * This image is returned by {@link #getRenderLayer()} method. <br>
	 */
	protected BufferedImage renderLayer;
	
	/**
	 * {@link java.awt.Graphics} object which provides ability to draw on renderLayer.<br>
	 * This object is initialized in method {@link #prepare()}. <br>
	 * It should be initialized every frame before rendering and disposed at the end of rendering for better performance. <br>
	 * 
	 * @see {@link #prepare()}
	 * @see {@link #renderLayer}
	 * @see {@link #disposeGraphics()}
	 */
	protected Graphics g;
	
	/**
	 * Array of screen pixels used to render sprites.<br>
	 * This array is initialized in {@link #Screen(int, int, Color) constructor} of this class.
	 * 
	 *@see svk.sglubos.engine.sprite.Sprite 
	 */
	protected int[] pixels;
	
	/**
	 * Constructs new object of Screen class.<br>
	 * 
	 * <h1>Initializes:</h1><br> 
	 * <code>BufferedImage {@link #renderLayer}</code> with arguments: <code>width</code>, <code>height</code> and <code>BufferedImage.TYPE_IN_RGB</code>.</br>
	 * <code>Int array {@link #pixels}</code> with data got from data buffer of Raster of renderLayer: 
	 * <br><code>((DataBufferInt) renderLayer.getRaster().getDataBuffer()).getData()</code> <br>
	 * <p>
	 * <code>Int {@link #width}</code> with value of passed parameter width <br>
	 * <code>Int {@link #height}</code> with value of passed parameter height <br>
	 * <code>Color {@link #defaultColor}</code> with value of passed parameter defaultColor
	 * </p> 
	 * <p>
	 * @param width (width of screen)
	 * @param height (height of screen)
	 * @param defaultColor (entire screen is filled with this color in method <code>prepare()</code>)
	 * </p>
	 * 
	 * @see java.awt.image.BufferedImage
	 */
	public Screen(int width, int height, Color defaultColor) {
		renderLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) renderLayer.getRaster().getDataBuffer()).getData();

		this.width = width;
		this.height = height;
		this.defaultScreenColor = defaultColor;
	}
	
	/**
	 * Draws filled rectangle of specified position, size and color.<br>
	 * Uses {@link #renderFilledRectangle(int, int, int, int) renderFilledRectangle(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * @param color color of rectangle
	 */
	public void renderFilledRectangle(int x, int y, int width, int height,	Color color) {
		setColor(color);
		renderFilledRectangle(x, y, width, height);
	}
	
	/**
	 * Draws filled rectangle of specified position and size. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#fillRect(int, int, int, int)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * 
	 * @see {@link #setColor(Color)}
	 */
	public void renderFilledRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Draws rectangle of specified position, size and color. <br>
	 * Uses {@link #renderRectangle(int, int, int, int) renderRectangle(x,y,width,height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * @param color color of rectangle
	 */
	public void renderRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderRectangle(x, y, width, height);
	}
	
	/**
	 * Draws rectangle of specified position and size. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#drawRect(int, int, int, int) g.drawRect(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * 
	 * @see {@link #setColor(Color)}
	 */
	public void renderRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawRect(x, y, width, height);
	}

	/**
	 * Draws BufferedImage of specified position and size. <br>
	 * Uses {@link java.awt.Graphics#drawImage(java.awt.Image, int, int,int,int, java.awt.image.ImageObserver) g.drawImage(img, x, y, width, height,null)} method.
	 * 
	 * @param img image which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of image
	 * @param height height of image
	 * 
	 * @see java.awt.image.BufferedImage
	 */
	public void renderImage(BufferedImage img, int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawImage(img, x, y, width, height, null);
	}
	
	/**
	 * Draws BufferedImage on specified position and default size of image. <br>
	 * Uses {@link java.awt.Graphics#drawImage(java.awt.Image, int, int,int,int, java.awt.image.ImageObserver) g.drawImage(img,x,y,img.getWidth(),img.getHeight(),null)} method.
	 * 
	 * @param img image which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * 
	 * @see java.awt.image.BufferedImage
	 */
	public void renderImage(BufferedImage img, int x, int y) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
	
	/**
	 * Draws String of specified position, font and color.<br>
	 * Uses {@link #renderString(String, int, int, Font) renderString(text, x, y, font)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param font font of text
	 * @param color color of text
	 * 
	 *@see java.awt.Font
	 */
	public void renderString(String text, int x, int y, Font font, Color color) {
		setColor(color);
		renderString(text, x, y, font);
	}
	
	/**
	 * Draws String of specified position and font. Uses current color set in Graphics object.<br>
	 * Uses {@link #renderString(String, int, int) renderString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param font font of text
	 * 
	 * @see {@link #setColor(Color)}
	 * @see java.awt.Font
	 */
	public void renderString(String text, int x, int y, Font font) {
		setFont(font);
		renderString(text,x,y);
	}
	
	/**
	 * Draws String of specified position. Uses current color and font set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics2D#drawString(String, int, int) g.drawString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * 
	 * @see {@link #setColor(Color)}
	 * @see {@link #setFont(Font)}
	 */
	public void renderString(String text, int x, int y) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawString(text, x,y);
	}
	
	/**
	 * Draws oval of specified position, size and color.<br>
	 * Uses {@link #renderOval(int, int, int, int) renderOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * @param color color of oval
	 */
	public void renderOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderOval(x, y, width, height);
	}
	
	/**
	 * Draws oval of specified position and size. Uses current color set in Graphics object<br>
	 * Uses {@link java.awt.Graphics#drawOval(int, int, int, int) g.drawOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * 
	 * @see {@link #setColor(Color)}
	 */
	public void renderOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawOval(x,y, width, height);
	}
	
	/**
	 * Draws filled oval of specified position and size. Uses current color set in Graphics object<br>
	 * Uses {@link #renderFiledOval(int, int, int, int) renderOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * 
	 * @see {@link #setColor(Color)}
	 */
	public void renderFiledOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderFiledOval(x, y, width, height);
	}
	
	/**
	 * Draws filled oval of specified position and size. Uses current color set in Graphics object<br>
	 * Uses {@link java.awt.Graphics#fillOval(int, int, int, int) g.fillOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * 
	 * @see {@link #setColor(Color)}
	 */
	public void renderFiledOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.fillOval(x, y, width, height);
	}

	/**
	 * Draws line from specified starting point to specified ending point of specified color.<br>
	 * Uses {@link #renderLine(int, int, int, int) renderLine(x, y, xa, ya)} method.
	 * 
	 * @param x horizontal coordinate of starting point
	 * @param y vertical coordinate of starting point
	 * @param xa horizontal coordinate of ending point
	 * @param ya vertical coordinate of ending point
	 * @param color color of line
	 */
	public void renderLine(int x, int y, int xa, int ya, Color color) {
		setColor(color);
		renderLine(x, y, xa, ya);
	}
	
	/**
	 * Draws line from specified starting point to specified ending point. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#drawLine(int, int, int, int) g.drawLine(x, y, xa, ya} method.
	 * 
	 * @param x horizontal coordinate of starting point
	 * @param y vertical coordinate of starting point
	 * @param xa horizontal coordinate of ending point
	 * @param ya vertical coordinate of ending point
	 * 
	 * @see {@link #setColor(Color)}
	 */
	public void renderLine(int x, int y, int xa, int ya) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
			xa = offsetCoordinate(xa,xOffset);
			ya = offsetCoordinate(ya,yOffset);
		}
		g.drawLine(x, y, xa, ya);
	}
	
	/**
	 * Draws filled arc of specified position, size, angles and color.<br>
	 * Uses {@link #renderFilledArc(int, int, int, int, int, int) rendeFilledrArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of filled arc
	 * @param height height offilled arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * @param color color of filled  arc
	 * 
	 * @see {@link java.awt.Graphics#fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)}
	 */
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		renderFilledArc(x, y, width, height, startAngle, arcAngle, color);
	}
	
	/**
	 * Draws filled arc of specified position, size and angles. Uses current color set in Graphics object.<br>
	 * Uses {@link #renderFilledArc(int, int, int, int, int, int) rendeFilledrArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * 
	 * @see {@link #setColor(Color)}
	 * @see {@link java.awt.Graphics#fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)}
	 */
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle){
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.fillArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws arc of specified position, size, angles and color.<br>
	 * Uses {@link #renderArc(int, int, int, int, int, int) renderArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * @param color color of arc
	 * 
	 * @see {@link java.awt.Graphics#drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)}
	 */
	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		renderArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws arc of specified position, size and angles. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#drawArc(int, int, int, int, int, int) g.drawArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * 
	 * @see {@link #setColor(Color)}
	 * @see {@link java.awt.Graphics#drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)}
	 */
	public void renderArc(int x, int y, int width, int height, int startAngle,	int arcAngle) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	//TODO
	public void renderSprite(Sprite sprite, int xCoord, int yCoord){
		int[] spritePixels = sprite.getPixels();
		
		if(!ignoreOffset){
			xCoord = offsetCoordinate(xCoord,xOffset);
			yCoord = offsetCoordinate(yCoord,yOffset);
		}
		
		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();
		
		int pixelX;
		int pixelY;
		
		for(int y = 0; y < spriteHeight; y++){
			pixelY = y + yCoord;
			for(int x = 0; x < spriteWidth; x++){
				pixelX = x + xCoord;
				if(spritePixels[x + y * spriteWidth] == 0){
					continue;
				}
				if(pixelY > 0 && pixelY < this.height && pixelX > 0 && pixelX < this.width ){
					this.pixels[pixelX + pixelY * this.width] = spritePixels[x + y * spriteWidth];
				}
			}
		}
	}
	
	/**
	 * Prepares screen before rendering any content.<br>
	 * Initializes <code>Graphics</code> object which is returned by this method: <code>renderLayer.createGraphics()</code>. <br> 
	 * Also fills entire screen with  {@link #defaultScreenColor} defaultScreenColor}. This method should be called every frame.
	 * 
	 * @see {@link #g Graphics object}
	 * @see {@link #defaultScreenColor}
	 * 
	 */
	public void prepare(){
		g = renderLayer.getGraphics();
		g.setColor(defaultScreenColor);
		g.fillRect(0, 0, width, height);
	}
	
	/**
	 * Offsets specified coordinate by specified value. <br>
	 * Offseting coordinate means that coordinate is subtracted by offset.
	 * 
	 * @param coord coordinate which will be offset
	 * @param offset value which is coordinate offset
	 * @return coord - offset 
	 * 
	 * @see {@link #xOffset}
	 * @see {@link #yOffset}
	 * @see {@link #ignoreOffset}
	 */
	protected int offsetCoordinate(int coord, int offset){
		return coord - offset;
	}
	
	//TODO documentation and exception
	public void setColor(Color color) {
		if (color == null) {
			// TODO handle error
			return;
		}
		g.setColor(color);
	}
	
	//TODO documentation and exception
	public void setFont(Font font) {
		if (font == null) {
			// TODO handle error
			return;
		}
		g.setFont(font);
	}
	
	//TODO better documentation
	/**
	 * Sets horizontal and vertical offset of screen to specified values. <br>
	 * Offseting screen can be used to "move" screen. <br>
	 * 
	 * @param xOffset Horizontal offset of screen (offset on x axis)
	 * @param yOffset Vertical offset of screen (offset on y axis)
	 * 
	 * @see {@link #xOffset}
	 * @see {@link #yOffset}
	 * @see {@link #ignoreOffset}
	 */
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/**
	 * Sets {@link #ignoreOffset} value. <br>
	 * Sets if screen offset is ignored while rendering content.
	 * 
	 * @param ignore if true offset of screen is ignored else position of rendered content is recalculated with screen offset
	 * 
	 * @see {@link #xOffset}
	 * @see {@link #yOffset}
	 * @see {@link #setOffset(int,int) setOffset(xOffset, yOffset)}
	 */
	public void setIngoreOffset(boolean ignore){
		this.ignoreOffset = ignore;
	}
	
	/**
	 * @return returns {@link #g Graphics object} which is used to draw on {@link #renderLayer}
	 * 
	 * @see java.awt.Graphics
	 */
	public Graphics getGraphics() {
		return g;
	}
	
	/**
	 * Disposes {@link #g Graphics object} which is used to draw on {@link #renderLayer}.
	 * This method should be called at the end of rendering content for better performance.
	 * 
	 * @see java.awt.Graphics
	 */
	public void disposeGraphics() {
		g.dispose();
	}
	
	/**
	 * @return width of screen in pixels
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return width of screen in pixels
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return horizontal screen offset
	 */
	public int getXOffset(){
		return xOffset;
	}
	
	/**
	 * @return vertical screen offset
	 */
	public int getYOffset(){
		return yOffset;
	}
	
	/**
	 * @return <code>BufferedImage</code>{@link #renderLayer} which contains all rendered graphics.
	 * 
	 * @see java.awt.image.BufferedImage
	 */
	public BufferedImage getRenderLayer() {
		return renderLayer;
	}
}
